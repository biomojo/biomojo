package org.biomojo.alphabet;

public interface Gapped<T, A extends Alphabet<T>> extends Alphabet<T> {
    /**
     * Gap symbol.
     *
     * @return the t
     */
    public T gapSymbol();
}
