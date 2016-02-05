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
package org.biomojo.benchmark.commands;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.biomojo.alignment.ByteSeqAligner;
import org.biomojo.alignment.ByteSeqAlignment;
import org.biomojo.alignment.ByteSubstitutionMatrix;
import org.biomojo.alignment.MatchMismatchByteSubstitutionMatrix;
import org.biomojo.alignment.SmithWatermanLinearGapByteSeqAligner;
import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.DNA;
import org.biomojo.codec.CodecId;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.factory.ByteSeqSupplier;
import org.biomojo.sequence.factory.EncodedByteSeqSupplier;
import org.java0.core.exception.UncheckedException;
import org.java0.util.timing.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "align")
public class AlignCommand extends BaseInputOutputCommand {
    private static final Logger logger = LoggerFactory.getLogger(AlignCommand.class.getName());

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        try {
            logger.info("BioMojo alignment benchmark");

            final FastaInputStream<DNA> inputStream = new FastaInputStream<>(new FileInputStream(inputFile));
            final PrintStream outputStream = new PrintStream(
                    new BufferedOutputStream(new FileOutputStream(outputFile)));

            final List<ByteSeq<DNA>> sequences = new ArrayList<>();

            Supplier<ByteSeq<DNA>> supplier = new ByteSeqSupplier<>(AlphabetId.DNA);
            if (encode) {
                supplier = new EncodedByteSeqSupplier<>(AlphabetId.DNA, CodecId.TWO_BIT_BYTE_CODEC);
            }
            ByteSeq<DNA> sequence = supplier.get();

            while (inputStream.read(sequence)) {
                logger.debug("Read sequence: {}", sequence.getDescription());
                sequences.add(sequence);
                sequence = supplier.get();
            }
            inputStream.close();

            logger.info("Done loading " + sequences.size() + " sequences");

            logger.info("Running alignments");

            final int numSeqs = sequences.size();

            final ByteSubstitutionMatrix matrix = new MatchMismatchByteSubstitutionMatrix(
                    Alphabets.getAlphabet(AlphabetId.DNA), 1, -1);

            final ByteSeqAligner<DNA> aligner = new SmithWatermanLinearGapByteSeqAligner<>(matrix, -2);
            final Stopwatch sw = new Stopwatch();
            sw.start();
            final List<ByteSeq<DNA>> seqList = new ArrayList<ByteSeq<DNA>>();
            for (int i = 0; i < 1; ++i) {
                for (int j = 1; j < numSeqs; ++j) {
                    seqList.clear();
                    seqList.add(sequences.get(i));
                    seqList.add(sequences.get(j));
                    logger.info("Aligning {} and {}", i, j);
                    final ByteSeqAlignment<DNA> alignment = aligner.align(seqList);
                    // logger.info("{}", alignment.get(0).toString());
                    // logger.info("{}", alignment.get(1).toString());
                    outputStream.println(alignment.getScore());
                }
            }

            outputStream.close();
            sw.stop();
            logger.info("Done");

        } catch (final FileNotFoundException e) {
            throw new UncheckedException(e);
        } catch (final IOException e) {
            throw new UncheckedException(e);
        }
    }
}
