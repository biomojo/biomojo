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

import org.biomojo.alphabet.Alphabet;
import org.biomojo.alphabet.ByteAlphabet;

// TODO: Auto-generated Javadoc
/**
 * The Class NullByteCodec.
 */
public class NullByteCodec extends AbstractByteCodec implements ByteCodec {
    
    /**
     * Instantiates a new null byte codec.
     *
     * @param id the id
     */
    NullByteCodec(final int id) {
        super(id);
    }

    /**
     * Decode.
     *
     * @param alphabet the alphabet
     * @param encodedData the encoded data
     * @param length the length
     * @return the byte[]
     * @see org.biomojo.codec.ByteCodec#decode(byte[])
     */
    @Override
    public byte[] decode(final ByteAlphabet alphabet, final byte[] encodedData, final int length) {
        return encodedData;
    }

    /**
     * Decode.
     *
     * @param alphabet the alphabet
     * @param encodedData the encoded data
     * @param length the length
     * @param pos the pos
     * @return the byte
     * @see org.biomojo.codec.ByteCodec#decode(byte[], long)
     */
    @Override
    public byte decode(final ByteAlphabet alphabet, final byte[] encodedData, final int length, final int pos) {
        return encodedData[pos];
    }

    /**
     * Encode.
     *
     * @param alphabet the alphabet
     * @param encodedData the encoded data
     * @param length the length
     * @param decodedData the decoded data
     * @return the byte[]
     * @see org.biomojo.codec.ByteCodec#encode(byte[], byte[])
     */
    @Override
    public byte[] encode(final ByteAlphabet alphabet, final byte[] encodedData, final int length,
            final byte[] decodedData) {
        return decodedData;
    }

    /**
     * Encode.
     *
     * @param alphabet the alphabet
     * @param encodedData the encoded data
     * @param length the length
     * @param symbol the symbol
     * @param index the index
     * @see org.biomojo.codec.ByteCodec#encode(byte[], byte, long)
     */
    @Override
    public void encode(final ByteAlphabet alphabet, final byte[] encodedData, final int length, final byte symbol,
            final int index) {
        encodedData[index] = symbol;
    }

    /**
     * Supports alphabet.
     *
     * @param alphabet the alphabet
     * @return true, if successful
     * @see org.biomojo.codec.Codec#supportsAlphabet(org.biomojo.alphabet.Alphabet)
     */
    @Override
    public boolean supportsAlphabet(final Alphabet<Byte> alphabet) {
        return true;
    }

    /**
     * Decode.
     *
     * @param alphabet the alphabet
     * @param encodedData the encoded data
     * @param length the length
     * @param index the index
     * @return the byte
     * @see org.biomojo.codec.AnyToByteCodec#decode(org.biomojo.alphabet.Alphabet,
     *      byte[], int, int)
     */
    @Override
    public Byte decode(final Alphabet<Byte> alphabet, final byte[] encodedData, final int length, final int index) {
        return encodedData[index];
    }

    /**
     * Encode.
     *
     * @param alphabet the alphabet
     * @param encodedData the encoded data
     * @param symbol the symbol
     * @param index the index
     * @see org.biomojo.codec.AnyToByteCodec#encode(org.biomojo.alphabet.Alphabet,
     *      byte[], java.lang.Object, int)
     */
    @Override
    public void encode(final Alphabet<Byte> alphabet, final byte[] encodedData, final Byte symbol, final int index) {
        encodedData[index] = symbol;
    }

}