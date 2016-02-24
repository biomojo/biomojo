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

package org.biomojo.stats;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.EncodedByteSeq;
import org.java0.util.bits.Masks;

public class KmerCounter<A extends ByteAlphabet> {
    private final int kmerLength;
    private final A alphabet;
    final int kmerCounts[];

    public KmerCounter(final int kmerLength, final A alphabet) {
        this.kmerLength = kmerLength;
        this.alphabet = alphabet;
        kmerCounts = new int[arraySize(alphabet)];
    }

    private int arraySize(final A alphabet) {
        final int alphabetSize = alphabet.numSymbols();
        int numKmers = alphabetSize;
        for (int i = 1; i < kmerLength; ++i) {
            numKmers = numKmers * alphabetSize;
        }
        return numKmers;
    }

    public void count(final ByteSeq<A> seq) {
        final int bitsPerSymbol = 2;
        final int kmerMask = Masks.LOW_ORDER_INT_MASK[kmerLength * bitsPerSymbol];

        final byte[] bytes = seq.toByteArray();
        int kmer = 0;
        for (int i = 0; i < kmerLength; ++i) {
            kmer = kmer << bitsPerSymbol;
            kmer = kmer | alphabet.getOrdinalForSymbol(bytes[i]);
        }
        kmerCounts[kmer]++;
        for (int i = kmerLength; i < bytes.length; ++i) {
            kmer = kmer << bitsPerSymbol;
            kmer = kmer | alphabet.getOrdinalForSymbol(bytes[i]);
            kmerCounts[kmer & kmerMask]++;
        }
    }

    public void count(final EncodedByteSeq<A> seq) {
        final int bitsPerSymbol = 2;
        final int kmerMask = Masks.LOW_ORDER_INT_MASK[kmerLength * bitsPerSymbol];

        final byte[] bytes = seq.getEncodedBytes();
        int kmer = 0;
        for (int i = 0; i < kmerLength; ++i) {
            kmer = kmer << bitsPerSymbol;
            kmer = kmer | bytes[i];
        }
        kmerCounts[kmer]++;
        for (int i = kmerLength; i < bytes.length; ++i) {
            kmer = kmer << bitsPerSymbol;
            kmer = kmer | alphabet.getOrdinalForSymbol(bytes[i]);
            kmerCounts[kmer & kmerMask]++;
        }
    }

    public int[] getCounts() {
        return kmerCounts;
    }
}
