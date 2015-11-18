/*
 * Copyright (C) 2015  Hugh Eaves
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.biomojo.codec;

import org.biomojo.alphabet.ByteAlphabet;

// TODO: Auto-generated Javadoc
/**
 * The Interface ByteCodec.
 */
public interface ByteLongCodec extends ObjectLongCodec<Byte> {

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
    public byte[] decode(ByteAlphabet alphabet, long[] encodedData, int length);

    /**
     * Decode a single position in the sequence.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @param index
     *            the index
     * @return the byte
     */
    public byte decode(ByteAlphabet alphabet, long[] encodedData, int length, int index);

    /**
     * Encode all the data into the sequence, replacing any existing data.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @param decodedData
     *            the decoded data
     * @return the byte[]
     */
    public long[] encode(ByteAlphabet alphabet, long[] encodedData, int length, byte[] decodedData);

    /**
     * Encode a single value, replacing the value at the given position.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @param symbol
     *            the symbol
     * @param index
     *            the index
     */
    public void encode(ByteAlphabet alphabet, long[] encodedData, int length, byte symbol, int index);

}
