package org.biomojo.benchmark.framework.benchmark;

import java.util.function.Supplier;

import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.sequence.BasicByteSeq;
import org.biomojo.sequence.BasicFastqSeq;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.FastqSeqSupplier;

public class RandomFastqSeqSupplier<A extends Nucleotide<?>, Q extends ByteQuality> extends FastqSeqSupplier<A, Q> {
    protected Supplier<Integer> lengthGenerator;

    public RandomFastqSeqSupplier(final long alphabetId, final long qualityScoreAlphabetId,
            final Supplier<Integer> lengthGenerator) {
        super(alphabetId, qualityScoreAlphabetId);
        this.lengthGenerator = lengthGenerator;
    }

    @Override
    public FastqSeq<A, Q> get() {
        final FastqSeq<A, Q> seq = new BasicFastqSeq<>(alphabet, new BasicByteSeq<>(qualityScoreAlphabet));

        final int length = lengthGenerator.get();

        RandomSeqGenerator.fillWithRandomSymbols(seq, length);
        RandomSeqGenerator.fillWithRandomSymbols(seq.getQualityScores(), length);

        return seq;
    }
}
