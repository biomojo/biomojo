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
import java.util.Arrays;
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.codec.CodecId;
import org.biomojo.io.ParseException;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.io.fastx.FastqOutputStream;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.EncodedFastqSeqSupplier;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "trim")
public class TrimCommand extends BaseInputOutputCommand {
    private static final Logger logger = LoggerFactory.getLogger(TrimCommand.class.getName());

    @Parameter(names = { "-q", "--qscore" }, description = "Minimum quality score for trimming")
    private final int cutoff = 30;

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        try {
            logger.info("BioMojo Fastq trim benchmark");

            final FastqInputStream inputStream = new FastqInputStream(new FileInputStream(inputFile), false);
            final FastqOutputStream outputStream = new FastqOutputStream(
                    new BufferedOutputStream(new FileOutputStream(outputFile), 1024 * 1024));

            Supplier<FastqSeq<NucleotideAlphabet>> supplier = new FastqSeqSupplier<>(AlphabetId.DNA);
            if (encode) {
                supplier = new EncodedFastqSeqSupplier<>(AlphabetId.DNA, CodecId.TWO_BIT_BYTE_CODEC);
            }
            final FastqSeq<NucleotideAlphabet> sequence = supplier.get();

            int recordCount = 0;
            long totalLength = 0;
            long qualityLength = 0;

            while (inputStream.read(sequence)) {

                final int end = sequence.size() - 1;
                final byte[] sequenceData = sequence.toByteArray();
                final byte[] quality = sequence.getQualityScores().toByteArray();
                ++recordCount;
                totalLength += sequenceData.length;
                qualityLength += quality.length;

                int trimPos = -1;
                for (int j = end; j >= 0; --j) {
                    if ((quality[j] - 33) >= cutoff) {
                        trimPos = j;
                        break;
                    }
                }

                if (trimPos >= 0) {

                    if (trimPos < end) {
                        sequence.setAll(Arrays.copyOfRange(sequenceData, 0, trimPos + 1), false);
                        sequence.getQualityScores().setAll(Arrays.copyOfRange(quality, 0, trimPos + 1), false);
                    }

                    outputStream.write(sequence);
                }
            }

            inputStream.close();
            outputStream.close();
            logger.info("Done loading " + recordCount + " sequences");
            logger.info("Total sequence length is " + totalLength + " bases");
            logger.info("Total quality length is " + qualityLength + " values");

            try {
                Thread.sleep(0);
            } catch (final InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (final ParseException e) {
            throw new UncheckedException(e);
        } catch (final FileNotFoundException e) {
            throw new UncheckedException(e);
        } catch (final IOException e) {
            throw new UncheckedException(e);
        }
    }
}
