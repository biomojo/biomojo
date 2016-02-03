package org.biomojo.alignment;

import org.biomojo.alphabet.GappableByte;
import org.biomojo.alphabet.GappedByte;
import org.biomojo.sequence.ByteSeq;

public interface ByteSeqAlignment<A extends GappableByte<A, GappedByte<A>>>
        extends Alignment<Byte, A, GappedByte<A>, ByteSeq<GappedByte<A>>> {
}
