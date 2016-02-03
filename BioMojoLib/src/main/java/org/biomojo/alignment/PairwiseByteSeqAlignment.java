package org.biomojo.alignment;

import org.biomojo.alphabet.GappableByte;
import org.biomojo.alphabet.GappedByte;
import org.biomojo.sequence.ByteSeq;

public class PairwiseByteSeqAlignment<A extends GappableByte<A, GappedByte<A>>>
        extends PairwiseAlignment<Byte, A, GappedByte<A>, ByteSeq<GappedByte<A>>> implements ByteSeqAlignment<A> {

    private static final long serialVersionUID = 1L;

}
