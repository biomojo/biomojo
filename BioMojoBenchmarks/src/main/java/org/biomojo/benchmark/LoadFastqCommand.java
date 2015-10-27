/*
 * Copyright (C) 2014  Hugh Eaves
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
package org.biomojo.benchmark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.io.SequenceIdHeaderParser;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "load_fastq")
public class LoadFastqCommand extends BaseCommand {
    private static final Logger logger = LoggerFactory.getLogger(LoadFastqCommand.class.getName());

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        try {
            logger.info("BioMojo Fastq Load Benchmark");

            final FastqInputStream inputStream = new FastqInputStream(new FileInputStream(inputFile),
                    new SequenceIdHeaderParser());
            final List<ByteSeq<NucleotideAlphabet>> sequences = new ArrayList<ByteSeq<NucleotideAlphabet>>();

            int recordCount = 0;
            long totalLength = 0;
            long qualityLength = 0;
            int lastLog = 0;

            Supplier<FastqSeq<NucleotideAlphabet>> provider = new FastqSeqProvider();
            if (encode) {
                provider = new EncodedFastqSeqProvider();
            }
            FastqSeq<NucleotideAlphabet> sequence = provider.get();

            while (inputStream.read(sequence)) {
                sequences.add(sequence);
                ++recordCount;
                totalLength += sequence.size();
                qualityLength += sequence.getQualityScores().size();
                final int log = (int) (Math.log(recordCount) / Math.log(1.015));
                if (log != lastLog) {
                    lastLog = log;
                    logger.info("Loaded " + recordCount + " sequences so far");
                    System.gc();
                }
                sequence = provider.get();
            }
            inputStream.close();
            System.gc();
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
