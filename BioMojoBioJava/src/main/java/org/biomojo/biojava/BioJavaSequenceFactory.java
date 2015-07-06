package org.biomojo.biojava;

import org.biojava.nbio.core.sequence.template.Sequence;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.sequence.ByteSeq;

public interface BioJavaSequenceFactory<T extends Sequence<?>, A extends ByteAlphabet> {
	public T createSequence(ByteSeq<A> sequence);
}
