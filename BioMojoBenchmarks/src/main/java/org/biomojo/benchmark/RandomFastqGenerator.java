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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.io.FixedWidthSequenceIdHeaderBuilder;
import org.biomojo.io.fastx.FastqOutputStream;
import org.biomojo.sequence.FastqSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class RandomFastqGenerator extends RandomSeqGenerator {
    private static final Logger logger = LoggerFactory.getLogger(RandomFastqGenerator.class.getName());

    private final FastqSeqProvider provider = new FastqSeqProvider();

    @Override
    public void createFile(final File file, final int numSeqs, final int seqLength) {
        try {
            final FastqOutputStream output = new FastqOutputStream(new BufferedOutputStream(new FileOutputStream(file)),
                    new FixedWidthSequenceIdHeaderBuilder(8), Integer.MAX_VALUE);
            // Start seqNum at 1 because BioPerl blows up with a sequence id of
            // zero!
            for (int seqNum = 1; seqNum <= numSeqs; ++seqNum) {
                output.write(genByteSeq(seqNum, seqLength));
            }

            output.close();
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }
    }

    protected FastqSeq<NucleotideAlphabet> genByteSeq(final int seqNum, final int length) {
        final FastqSeq<NucleotideAlphabet> record = provider.get();
        record.setId(seqNum);
        createRandomSeqData(record, length);
        createRandomSeqData(record.getQualityScores(), length);
        return record;
    }
}
