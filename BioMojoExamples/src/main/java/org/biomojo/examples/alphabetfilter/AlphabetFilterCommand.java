/*
 * Copyright (C) 2015  Hugh Eaves
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.biomojo.examples.alphabetfilter;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.io.FileType;
import org.biomojo.io.FileUtil;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.io.fastx.FastaOutputStream;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.io.fastx.FastqOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.ByteSeqSupplier;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.java0.cli.InputOutputCommand;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "alphabetfilter", commandDescription = "Filter Input File Alphabet")
public class AlphabetFilterCommand extends InputOutputCommand {
    private static final Logger logger = LoggerFactory.getLogger(AlphabetFilterCommand.class.getName());

    @Parameter(names = { "-a", "--alphabetId" }, required = true, description = "Numeric alphabet id")
    private int alphabetId;

    @Override
    public void run() {
        try {
            logger.info("Filtering input file");

            final FileType fileType = FileUtil.guessFileType(inputFile);
            if (fileType != FileUtil.guessFileType(outputFile)) {
                logger.error("Input and output file must be the same type");
                return;
            }

            switch (fileType) {
            case FASTA:
                processFasta();
                break;
            case FASTQ:
                processFastq();
                break;
            default:
                logger.error("Unrecognized or unsupported file type");
                break;

            }

        } catch (final IOException e) {
            logger.error("Error", e);
        }
    }

    private void processFasta() throws FileNotFoundException, IOException {
        final ByteAlphabet targetAlphabet = Alphabets.getAlphabet(alphabetId);

        try (final SequenceInputStream<ByteSeq<ByteAlphabet>> inputStream = new FastaInputStream<>(
                new FileInputStream(inputFile), new ByteSeqSupplier<>(AlphabetId.LETTERS));

                final SequenceOutputStream<ByteSeq<ByteAlphabet>> outputStream = new FastaOutputStream<>(
                        new BufferedOutputStream(new FileOutputStream(outputFile)));) {

            filter(inputStream, outputStream, targetAlphabet);
        }
    }

    private void processFastq() throws FileNotFoundException, IOException {
        final ByteAlphabet targetAlphabet = Alphabets.getAlphabet(alphabetId);

        try (final SequenceInputStream<FastqSeq<Nucleotide<?>, ByteQuality<?>>> inputStream = new FastqInputStream<>(
                new FileInputStream(inputFile), new FastqSeqSupplier<>(AlphabetId.NUCLEOTIDE, 0));

                final SequenceOutputStream<FastqSeq<Nucleotide<?>, ByteQuality<?>>> outputStream = new FastqOutputStream<>(
                        new BufferedOutputStream(new FileOutputStream(outputFile)));) {

            filter(inputStream, outputStream, targetAlphabet);
        }
    }

    public <T extends ByteSeq<? extends ByteAlphabet>> void filter(final SequenceInputStream<T> inputStream,
            final SequenceOutputStream<T> outputStream, final ByteAlphabet targetAlphabet) {
        try {

            int recordCount = 0;
            for (T sequence = inputStream.read(); sequence != null; sequence = inputStream.read()) {
                ++recordCount;
                if (recordCount % 10000 == 0) {
                    logger.info("Record #{}", recordCount);
                    logger.info("Length = {}", sequence.size());
                }
                sequence.canonicalize();
                if (targetAlphabet.isValid(sequence.toByteArray())) {
                    outputStream.write(sequence);
                } else {
                    logger.info("Skipping sequence {}", sequence.getDescription());
                }
            }

        } catch (final IOException e) {
            logger.error("Error", e);
        }
    }

}
