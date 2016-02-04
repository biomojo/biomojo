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
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.DNA;
import org.biomojo.alphabet.SangerQuality;
import org.biomojo.codec.CodecId;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.EncodedFastqSeqSupplier;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.java0.util.timing.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "read_fastq")
public class ReadFastqCommand extends BaseInputCommand {
    private static final Logger logger = LoggerFactory.getLogger(ReadFastqCommand.class.getName());

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        try {
            logger.info("BioMojo Fastq Read Benchmark");

            final Stopwatch sw = new Stopwatch();
            sw.start();

            final FastqInputStream<DNA, SangerQuality> inputStream = new FastqInputStream<>(
                    new FileInputStream(inputFile));

            int recordCount = 0;
            long totalLength = 0;
            long qualityLength = 0;

            Supplier<FastqSeq<DNA, SangerQuality>> supplier = new FastqSeqSupplier<>(AlphabetId.DNA,
                    AlphabetId.QUALITY_SANGER);
            if (encode) {
                supplier = new EncodedFastqSeqSupplier<>(AlphabetId.DNA, CodecId.TWO_BIT_BYTE_CODEC,
                        AlphabetId.QUALITY_SANGER);
            }

            final FastqSeq<DNA, SangerQuality> sequence = supplier.get();

            while (inputStream.read(sequence)) {
                ++recordCount;
                totalLength += sequence.size();
                qualityLength += sequence.getQualityScores().size();
            }
            inputStream.close();

            logger.info("Done loading " + recordCount + " sequences");
            logger.info("Total sequence length is " + totalLength + " bases");
            logger.info("Total quality length is " + qualityLength + " values");

            sw.stop();

        } catch (final Exception e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }
    }
}
