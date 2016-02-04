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
package org.biomojo.examples.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.biomojo.alphabet.ASCII;
import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.IUPACVariant;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.io.fastx.FastaOutputStream;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.io.fastx.FastqOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.Seq;
import org.biomojo.sequence.factory.ByteSeqSupplier;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.java0.cli.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "fastxformat", commandDescription = "Reformat Fastx file")
public class FastxFormatCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(FastxFormatCommand.class.getName());

    @Parameter(names = { "-i", "--input" }, required = true, description = "Input File Name")
    private InputStream inputStream;

    @Parameter(names = { "-o", "--output" }, required = true, description = "Output File Name")
    private OutputStream outputStream;

    @Parameter(names = { "-n", "--num" }, required = false, description = "Number of records")
    private Integer numRecs;

    @Parameter(names = { "-q", "--fastq" }, required = false, description = "FASTQ file?")
    private final boolean fastq = false;

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream
     *            the inputStream to set
     */
    public void setInputStream(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * @return the outputStream
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * @param outputStream
     *            the outputStream to set
     */
    public void setOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        logger.info("Formatting fastx file");
        if (fastq) {
            final FastqSeqSupplier<Nucleotide<?>, ByteQuality<?>> supplier = new FastqSeqSupplier<>(
                    AlphabetId.NUCLEOTIDE | IUPACVariant.WITH_ANY, AlphabetId.QUALITY_SANGER);
            try (SequenceInputStream<FastqSeq<Nucleotide<?>, ByteQuality<?>>> input = new FastqInputStream<>(
                    inputStream, supplier);
                    SequenceOutputStream<FastqSeq<Nucleotide<?>, ByteQuality<?>>> output = new FastqOutputStream<>(
                            outputStream)) {
                process(input, output);
            } catch (final IOException e) {
                logger.error("Caught exception in auto-generated catch block", e);
            }
        } else {
            final ByteSeqSupplier<ASCII> supplier = new ByteSeqSupplier<>(AlphabetId.ASCII);
            try (SequenceInputStream<ByteSeq<ASCII>> input = new FastaInputStream<>(inputStream, supplier);
                    SequenceOutputStream<ByteSeq<ASCII>> output = new FastaOutputStream<>(outputStream)) {
                process(input, output);
            } catch (final IOException e) {
                logger.error("Caught exception in auto-generated catch block", e);
            }
        }

    }

    public <T extends Seq<?, ?>> void process(final SequenceInputStream<T> input, final SequenceOutputStream<T> output)
            throws IOException {
        T record = null;
        int recCount = 0;
        while ((record = input.read()) != null) {
            ++recCount;
            if (numRecs == null || (numRecs != null && recCount <= numRecs)) {
                output.write(record);
                if (recCount % 10000 == 0) {
                    logger.info("Records written: {}", recCount);
                }
            } else {
                logger.info("Record limit of {} records reached", numRecs);
                break;
            }
        }
    }
}
