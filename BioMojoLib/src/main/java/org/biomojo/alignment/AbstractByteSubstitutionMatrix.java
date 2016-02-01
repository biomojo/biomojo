package org.biomojo.alignment;

import org.biomojo.alphabet.ByteAlphabet;

public abstract class AbstractByteSubstitutionMatrix implements ByteSubstitutionMatrix {

    /** The alphabet. */
    private final ByteAlphabet alphabet;

    protected AbstractByteSubstitutionMatrix(final ByteAlphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public ByteAlphabet getAlphabet() {
        return alphabet;
    }

    @Override
    public int getScore(final Byte from, final Byte to) {
        return getScore(from.byteValue(), to.byteValue());
    }

}
