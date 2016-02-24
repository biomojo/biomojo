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
package org.biomojo.benchmark.framework.benchmark;

import java.util.Random;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.sequence.ByteSeq;

/**
 * @author Hugh Eaves
 *
 */
public class RandomSeqGenerator {
    private static final Random random = new Random(0);

    public static void fillWithRandomSymbols(final ByteSeq<? extends ByteAlphabet> seq, final int length) {
        final byte[] seqData = new byte[length];
        final ByteAlphabet alphabet = seq.getAlphabet();
        for (int i = 0; i < length; ++i) {
            seqData[i] = alphabet.getByteSymbolForOrdinal(random.nextInt(alphabet.numSymbols()));
        }
        seq.setAll(seqData);
    }
}
