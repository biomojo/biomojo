package org.biomojo.alignment;

import java.util.List;

import org.biomojo.alphabet.GappableByte;
import org.biomojo.alphabet.GappedByte;
import org.biomojo.sequence.ByteSeq;

public interface ByteSeqAligner<A extends GappableByte<A, GappedByte<A>>>
        extends Aligner<Byte, A, GappedByte<A>, ByteSeq<GappedByte<A>>, ByteSeq<A>> {

    @Override
    public ByteSeqAlignment<A> align(List<ByteSeq<A>> sequences);

}
