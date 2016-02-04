package org.biomojo.stats;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.sequence.ByteSeq;
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

    public int[] getCounts() {
        return kmerCounts;
    }
}
