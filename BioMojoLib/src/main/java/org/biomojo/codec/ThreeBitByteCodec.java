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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ThreeBitByteCodec.
 * 
 * Encodes alphabets with eight or less symbols into a bit array in network byte
 * order. Three bytes are used for every eight symbols. (each symbol uses 3
 * bits)
 * 
 * byte--: -------0------- -------1------- -------2-------
 * 
 * bit---: 7 6 5 4 3 2 1 0 7 6 5 4 3 2 1 0 7 6 5 4 3 2 1 0
 * 
 * symbol: --0-- --1-- --2-- --3-- --4-- --5-- --6-- --7--
 */
public class ThreeBitByteCodec extends AbstractByteCodec {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ThreeBitByteCodec.class.getName());

    /** The Constant BITS_PER_SYMBOL. */
    private static final int BITS_PER_SYMBOL = 3;

    /** The Constant NUM_SYMBOLS. */
    private static final int NUM_SYMBOLS = 1 << BITS_PER_SYMBOL;

    private static final int MASK = 0x07;

    private static final int SHIFT_0 = 5;
    private static final int SHIFT_1 = 2;
    private static final int SHIFT_2A = 1; // LSHIFT
    private static final int SHIFT_2B = 7;
    private static final int SHIFT_3 = 4;
    private static final int SHIFT_4 = 1;
    private static final int SHIFT_5A = 2; // LSHIFT
    private static final int SHIFT_5B = 6;
    private static final int SHIFT_6 = 3;
    private static final int SHIFT_7 = 0;

    private static final int MASK_0 = ~(MASK << SHIFT_0);
    private static final int MASK_1 = ~(MASK << SHIFT_1);
    private static final int MASK_2A = ~(MASK >> SHIFT_2A);
    private static final int MASK_2B = ~(MASK << SHIFT_2B);
    private static final int MASK_3 = ~(MASK << SHIFT_3);
    private static final int MASK_4 = ~(MASK << SHIFT_4);
    private static final int MASK_5A = ~(MASK >> SHIFT_5A);
    private static final int MASK_5B = ~(MASK << SHIFT_5B);
    private static final int MASK_6 = ~(MASK << SHIFT_6);
    private static final int MASK_7 = ~(MASK << SHIFT_7);

    /**
     * Instantiates a new three bit byte codec.
     *
     * @param id
     *            the id
     */
    ThreeBitByteCodec(final int id) {
        super(id);
    }

    /**
     * Decode.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @return the byte[]
     * @see org.biomojo.codec.ByteCodec#decode(byte[])
     */
    @Override
    public byte[] decode(final ByteAlphabet alphabet, final byte[] encodedData, final int length) {
        final byte[] decodedData = new byte[length];
        int decodedPos = 0;
        int encodedPos = 0;
        int numDecodedBits = 0;
        int decodedBits = 0;
        while (decodedPos < length) {
            if (numDecodedBits < BITS_PER_SYMBOL) {
                decodedBits = (decodedBits << Byte.SIZE) | (encodedData[encodedPos++] & 0xff);
                numDecodedBits += Byte.SIZE;
            }
            numDecodedBits = numDecodedBits - BITS_PER_SYMBOL;
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((decodedBits >> numDecodedBits) & MASK);

        }
        return decodedData;
    }

    /**
     * Decode.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @param pos
     *            the pos
     * @return the byte
     * @see org.biomojo.codec.ByteCodec#decode(byte[], int)
     */
    @Override
    public byte decode(final ByteAlphabet alphabet, final byte[] encodedData, final int length, final int pos) {
        int ordinal = 0;

        int start = pos / NUM_SYMBOLS * BITS_PER_SYMBOL;
        switch (pos % NUM_SYMBOLS) {
        case 0:
            ordinal = encodedData[start] >>> SHIFT_0 & MASK;
            break;
        case 1:
            ordinal = encodedData[start] >>> SHIFT_1 & MASK;
            break;
        case 2:
            ordinal = encodedData[start] << SHIFT_2A & MASK | encodedData[start + 1] >>> 31;
            break;
        case 3:
            ordinal = encodedData[start + 1] >>> SHIFT_3 & MASK;
            break;
        case 4:
            ordinal = encodedData[start + 1] >>> SHIFT_4 & MASK;
            break;
        case 5:
            ordinal = (encodedData[start + 1] & 1) << SHIFT_5A | ((encodedData[start + 2] & 0xff) >> SHIFT_5B);
            break;
        case 6:
            ordinal = encodedData[start + 2] >>> SHIFT_6 & MASK;
            break;
        case 7:
            ordinal = encodedData[start + 2] >>> SHIFT_7 & MASK;
            break;

        }
        // logger.debug("ordinal = {}", ordinal);
        // logger.debug("====================================================");

        return alphabet.getByteSymbolForOrdinal(ordinal);
    }

    /**
     * Encode.
     *
     * @param alphabet
     *            the alphabet
     * @param oldEncodedData
     *            the old encoded data
     * @param length
     *            the length
     * @param decodedData
     *            the decoded data
     * @return the byte[]
     * @see org.biomojo.codec.ByteCodec#encode(byte[])
     */
    @Override
    public byte[] encode(final ByteAlphabet alphabet, final byte[] oldEncodedData, final int length,
            final byte[] decodedData) {
        // TODO Handle overflow
        final byte[] encodedData = new byte[((length * BITS_PER_SYMBOL - 1) / Byte.SIZE) + 1];
        int decodedPos = 0;
        int encodedPos = 0;
        int numEncodedBits = 0;
        int encodedBits = 0;
        while (decodedPos < length) {
            encodedBits = encodedBits << BITS_PER_SYMBOL;
            encodedBits = encodedBits | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            numEncodedBits += BITS_PER_SYMBOL;
            if (numEncodedBits >= Byte.SIZE) {
                numEncodedBits -= Byte.SIZE;
                encodedData[encodedPos++] = (byte) ((encodedBits >> numEncodedBits) & 0xff);
            }
        }
        if (numEncodedBits > 0) {
            encodedBits = encodedBits << (8 - numEncodedBits);
            encodedData[encodedPos++] = (byte) (encodedBits & 0xff);
        }
        return encodedData;

    }

    /**
     * Encode.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @param symbol
     *            the symbol
     * @param pos
     *            the pos
     * @see org.biomojo.codec.ByteCodec#encode(byte[], byte, int)
     */
    @Override
    public void encode(final ByteAlphabet alphabet, final byte[] encodedData, final int length, final byte symbol,
            final int pos) {
        int ordinal = alphabet.getOrdinalForSymbol(symbol);
        int start = pos / NUM_SYMBOLS * BITS_PER_SYMBOL;
        switch (pos % NUM_SYMBOLS) {
        case 0:
            encodedData[start] = (byte) (encodedData[start] & MASK_0 | ordinal << SHIFT_0);
            break;
        case 1:
            encodedData[start] = (byte) (encodedData[start] & MASK_1 | ordinal << SHIFT_1);
            break;
        case 2:
            encodedData[start] = (byte) (encodedData[start] & MASK_2A | ordinal >> SHIFT_2A);
            encodedData[start + 1] = (byte) (encodedData[start + 1] & MASK_2B | (ordinal << SHIFT_2B));
            break;
        case 3:
            encodedData[start + 1] = (byte) (encodedData[start + 1] & MASK_3 | ordinal << SHIFT_3);
            break;
        case 4:
            encodedData[start + 1] = (byte) (encodedData[start + 1] & MASK_4 | ordinal << SHIFT_4);
            break;
        case 5:
            encodedData[start + 1] = (byte) (encodedData[start + 1] & MASK_5A | ordinal >> SHIFT_5A);
            encodedData[start + 2] = (byte) (encodedData[start + 2] & MASK_5B | ordinal << SHIFT_5B);
            break;
        case 6:
            encodedData[start + 2] = (byte) (encodedData[start + 2] & MASK_6 | ordinal << SHIFT_6);
            break;
        case 7:
            encodedData[start + 2] = (byte) (encodedData[start + 2] & MASK_7 | ordinal);
            break;

        }
        return;

    }

    /**
     * Supports alphabet.
     *
     * @param alphabet
     *            the alphabet
     * @return true, if successful
     * @see org.biomojo.codec.Codec#supportsAlphabet(org.biomojo.alphabet.Alphabet)
     */
    @Override
    public boolean supportsAlphabet(final Alphabet<Byte> alphabet) {
        return (alphabet.numCanonicalSymbols() <= NUM_SYMBOLS);
    }
}
