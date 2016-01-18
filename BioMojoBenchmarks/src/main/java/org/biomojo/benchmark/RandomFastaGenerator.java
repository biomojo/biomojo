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
package org.biomojo.benchmark;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.io.FixedWidthSequenceIdHeaderBuilder;
import org.biomojo.io.fastx.FastaOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.factory.ByteSeqSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class RandomFastaGenerator extends RandomSeqGenerator {
    private static final Logger logger = LoggerFactory.getLogger(RandomFastaGenerator.class.getName());
    Supplier<ByteSeq<NucleotideAlphabet>> supplier = new ByteSeqSupplier<>(AlphabetId.DNA);

    @Override
    public void createFile(final File file, final int numSeqs, final int seqLength) {
        try {
            final FastaOutputStream output = new FastaOutputStream(new BufferedOutputStream(new FileOutputStream(file)),
                    new FixedWidthSequenceIdHeaderBuilder(8));
            // Start seqNum at 1 because BioPerl blows up with a sequence id of
            // zero!
            for (int seqNum = 1; seqNum <= numSeqs; ++seqNum) {
                output.write(genFastaSeq(seqNum, seqLength));
            }

            output.close();
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }
    }

    protected ByteSeq<NucleotideAlphabet> genFastaSeq(final int seqNum, final int length) {
        final ByteSeq<NucleotideAlphabet> record = supplier.get();
        record.setId(seqNum);
        createRandomSeqData(record, length);
        return record;
    }

}
