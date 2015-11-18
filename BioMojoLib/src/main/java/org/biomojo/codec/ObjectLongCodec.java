package org.biomojo.codec;

import org.biomojo.alphabet.Alphabet;

public interface ObjectLongCodec<T> extends Codec<T, Long> {

    /**
     * Decode all the data in the sequence.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @return the d[]
     */
    public T[] decode(Alphabet<T> alphabet, long[] encodedData, int length);

    /**
     * Decode a single position in the sequence.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @param pos
     *            the pos
     * @return the t
     */
    public T decode(Alphabet<T> alphabet, long[] encodedData, int length, int pos);

    /**
     * Encode a single value, replacing the value at the given position.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param symbol
     *            the symbol
     * @param pos
     *            the pos
     */
    public void encode(Alphabet<T> alphabet, long[] encodedData, T symbol, int pos);

    /**
     * Encode all the data into the sequence, replacing any existing data.
     *
     * @param alphabet
     *            the alphabet
     * @param decodedData
     *            the decoded data
     * @return the long[]
     */
    public long[] encode(Alphabet<T> alphabet, T[] decodedData);
}
