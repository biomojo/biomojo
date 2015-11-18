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

// TODO: Auto-generated Javadoc
/**
 * The Class TwoBitByteCodec.
 */
public class TwoBitByteCodec extends AbstractByteByteCodec {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(TwoBitByteCodec.class.getName());

    /**
     * Instantiates a new two bit byte codec.
     *
     * @param id
     *            the id
     */
    TwoBitByteCodec(final int id) {
        super(id);
    }

    /** The Constant BITS_PER_SYMBOL. */
    private static final int BITS_PER_SYMBOL = 2;

    /** The Constant SYMBOLS_PER_BYTE. */
    private static final int SYMBOLS_PER_BYTE = 8 / BITS_PER_SYMBOL;

    /** The Constant NUM_SYMBOLS. */
    private static final int NUM_SYMBOLS = 1 << BITS_PER_SYMBOL;

    /** The Constant MASK_3. */
    private static final int SYMBOL_MASK = 0x03;

    private static final int MASK_0 = 0xC0;
    private static final int MASK_1 = 0x30;
    private static final int MASK_2 = 0x0C;
    private static final int MASK_3 = 0x03;

    private static final int[] MASK = { MASK_0, MASK_1, MASK_2, MASK_3 };

    private static final int SHIFT_0 = 6;

    private static final int SHIFT_1 = 4;

    private static final int SHIFT_2 = 2;

    private static final int SHIFT_3 = 0;

    /** The Constant SHIFT. */
    private static final int[] SHIFT = { SHIFT_0, SHIFT_1, SHIFT_2, SHIFT_3 };

    private static final int CLEAR_MASK_0 = ~(SYMBOL_MASK << SHIFT_0);
    private static final int CLEAR_MASK_1 = ~(SYMBOL_MASK << SHIFT_1);
    private static final int CLEAR_MASK_2 = ~(SYMBOL_MASK << SHIFT_2);
    private static final int CLEAR_MASK_3 = ~SYMBOL_MASK;

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
        final int endPos = length - (length & 0x03);
        int decodedPos = 0;
        int encodedPos = 0;
        // decode full bytes
        while (decodedPos < endPos) {
            final int encodedByte = encodedData[encodedPos++];
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((encodedByte >> SHIFT_0 & SYMBOL_MASK));
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((encodedByte >> SHIFT_1 & SYMBOL_MASK));
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((encodedByte >> SHIFT_2 & SYMBOL_MASK));
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedByte & SYMBOL_MASK);
        }
        // decode partial byte at the end (if any)
        while (decodedPos < length) {
            decodedData[decodedPos] = decode(alphabet, encodedData, length, decodedPos);
            decodedPos++;
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
        final int bytePos = pos >>> 2;
        final int symbolPos = pos & 0x03;
        switch (symbolPos) {
        case 0:
            return alphabet.getByteSymbolForOrdinal(encodedData[bytePos] >> SHIFT_0 & SYMBOL_MASK);
        case 1:
            return alphabet.getByteSymbolForOrdinal(encodedData[bytePos] >> SHIFT_1 & SYMBOL_MASK);
        case 2:
            return alphabet.getByteSymbolForOrdinal(encodedData[bytePos] >> SHIFT_2 & SYMBOL_MASK);
        case 3:
            return alphabet.getByteSymbolForOrdinal(encodedData[bytePos] & SYMBOL_MASK);
        }
        return 0;
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
        final int arrayLength = (length + SYMBOLS_PER_BYTE - 1) >>> 2;
        // byte[] encodedData;
        // if (oldEncodedData == null || oldEncodedData.length <= arrayLength) {
        // encodedData = new byte[arrayLength];
        // } else {
        // logger.info("reuse");
        // encodedData = oldEncodedData;
        // }
        final byte[] encodedData = new byte[arrayLength];
        final int endPos = length - (length & 0x03);
        int decodedPos = 0;
        int encodedPos = 0;
        // encode full bytes
        while (decodedPos < endPos) {
            encodedData[encodedPos++] = (byte) (alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_0
                    | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_1
                    | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_2
                    | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]));
            // int encodedByte =
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            // encodedByte = (encodedByte << BITS_PER_SYMBOL) |
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            // encodedByte = (encodedByte << BITS_PER_SYMBOL) |
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            // encodedByte = (encodedByte << BITS_PER_SYMBOL) |
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            // encodedData[encodedPos++] = (byte) encodedByte;
        }
        // encode partial byte at the end (if any)
        while (decodedPos < length) {
            encode(alphabet, encodedData, length, decodedData[decodedPos], decodedPos);
            ++decodedPos;
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
        final int bytePos = pos >>> 2;
        final int symbolPos = pos & 0x03;
        switch (symbolPos) {
        case 0:
            encodedData[bytePos] = (byte) (encodedData[bytePos] & CLEAR_MASK_0
                    | alphabet.getOrdinalForSymbol(symbol) << SHIFT_0);
            break;
        case 1:
            encodedData[bytePos] = (byte) (encodedData[bytePos] & CLEAR_MASK_1
                    | alphabet.getOrdinalForSymbol(symbol) << SHIFT_1);
            break;
        case 2:
            encodedData[bytePos] = (byte) (encodedData[bytePos] & CLEAR_MASK_2
                    | alphabet.getOrdinalForSymbol(symbol) << SHIFT_2);
            break;
        case 3:
            encodedData[bytePos] = (byte) (encodedData[bytePos] & CLEAR_MASK_3 | alphabet.getOrdinalForSymbol(symbol));
            break;
        }

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
        return (alphabet.numSymbols() <= NUM_SYMBOLS);
    }
}
