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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.DNA;
import org.biomojo.benchmark.util.GCUtil;
import org.biomojo.codec.CodecId;
import org.biomojo.io.SequenceIdHeaderParser;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.EncodedFastqSeqSupplier;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "load_fastq")
public class LoadFastqCommand extends BaseInputCommand {
    private static final Logger logger = LoggerFactory.getLogger(LoadFastqCommand.class.getName());

    protected final GCUtil gcUtil = new GCUtil();

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        try {
            logger.info("BioMojo Fastq Load Benchmark");

            final List<ByteSeq<DNA>> sequences = new ArrayList<>();

            long totalLength = 0;
            long qualityLength = 0;

            Supplier<FastqSeq<DNA>> supplier = new FastqSeqSupplier<>(AlphabetId.DNA);
            if (encode) {
                supplier = new EncodedFastqSeqSupplier<>(AlphabetId.DNA, CodecId.TWO_BIT_BYTE_CODEC);
            }

            final SequenceInputStream<FastqSeq<DNA>> inputStream = new FastqInputStream<DNA>(
                    new FileInputStream(inputFile), new SequenceIdHeaderParser(), supplier);

            for (FastqSeq<DNA> sequence = inputStream.readSeq(); sequence != null; sequence = inputStream.readSeq()) {
                sequences.add(sequence);
                totalLength += sequence.size();
                qualityLength += sequence.getQualityScores().size();
                // gcUtil.recordAdded();
            }

            inputStream.close();

            logger.info("Done loading " + sequences.size() + " sequences");
            logger.info("Total sequence length is " + totalLength + " bases");
            logger.info("Total quality length is " + qualityLength + " values");

            Thread.sleep(0);

        } catch (final FileNotFoundException e) {
            throw new UncheckedException(e);
        } catch (final IOException e) {
            throw new UncheckedException(e);
        } catch (final InterruptedException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }
    }
}
