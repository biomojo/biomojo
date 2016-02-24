package org.biomojo.benchmark.framework.benchmark;

import java.util.function.Supplier;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.sequence.BasicByteSeq;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.factory.ByteSeqSupplier;

public class RandomFastaSeqSupplier<A extends ByteAlphabet> extends ByteSeqSupplier<A> {
    final Supplier<Integer> lengthGenerator;

    public RandomFastaSeqSupplier(final long alphabetId, final Supplier<Integer> lengthGenerator) {
        super(alphabetId);
        this.lengthGenerator = lengthGenerator;
    }

    @Override
    public ByteSeq<A> get() {
        final ByteSeq<A> seq = new BasicByteSeq<>(alphabet);

        RandomSeqGenerator.fillWithRandomSymbols(seq, lengthGenerator.get());

        return seq;
    }
}
