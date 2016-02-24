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
 * The Class FiveBitByteCodec.
 * 
 * Encodes alphabets with 32 or less symbols into a bit array in network byte
 * order. Five bytes are used for every eight symbols. (each symbol uses 5 bits)
 * 
 * byte--: 00000000|11111111|22222222|33333333|55555555
 * 
 * bit---: 76543210|76543210|76543210|76543210|76543210
 * 
 * symbol: 11100000|11322222|44443333|46655555|66677777
 * 
 * mask--: AAA00000|BBA22222|AAAABBBB|BAA55555|BBB77777
 */

public class FiveBitByteCodec extends AbstractByteByteCodec {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(FiveBitByteCodec.class.getName());

    /** The Constant BITS_PER_SYMBOL. */
    private static final int BITS_PER_SYMBOL = 5;

    private static final int SYMBOLS_PER_CHUNK = 8;

    /** The Constant NUM_SYMBOLS. */
    private static final int NUM_SYMBOLS = 1 << BITS_PER_SYMBOL;

    private static final int MASK = 0x01f;

    private static final int SHIFT_0 = 0;
    private static final int SHIFT_1A = 3;
    private static final int SHIFT_1B = 6;
    private static final int SHIFT_2 = 0;
    private static final int SHIFT_3A = 1;
    private static final int SHIFT_3B = 0;
    private static final int SHIFT_4A = 3;
    private static final int SHIFT_4B = 7;
    private static final int SHIFT_5 = 0;
    private static final int SHIFT_6A = 2;
    private static final int SHIFT_6B = 5;
    private static final int SHIFT_7 = 0;

    private static final int MASK_1A = 0b11100;
    private static final int MASK_1B = 0b00011;
    private static final int MASK_3A = 0b10000;
    private static final int MASK_3B = 0b01111;
    private static final int MASK_4A = 0b11110;
    private static final int MASK_4B = 0b00001;
    private static final int MASK_6A = 0b11000;
    private static final int MASK_6B = 0b00111;

    private static final int CLEAR_MASK_0 = ~MASK;
    private static final int CLEAR_MASK_1A = ~(MASK_1A << SHIFT_1A);
    private static final int CLEAR_MASK_1B = ~(MASK_1B << SHIFT_1B);
    private static final int CLEAR_MASK_2 = ~MASK;
    private static final int CLEAR_MASK_3A = ~(MASK_3A << SHIFT_3A);
    private static final int CLEAR_MASK_3B = ~(MASK_3B << SHIFT_3B);
    private static final int CLEAR_MASK_4A = ~(MASK_4A << SHIFT_4A);
    private static final int CLEAR_MASK_4B = ~(MASK_4B << SHIFT_4B);
    private static final int CLEAR_MASK_5 = ~MASK;
    private static final int CLEAR_MASK_6A = ~(MASK_6A << SHIFT_6A);
    private static final int CLEAR_MASK_6B = ~(MASK_6B << SHIFT_6B);
    private static final int CLEAR_MASK_7 = ~MASK;

    /**
     * Instantiates a new three bit byte codec.
     *
     * @param id
     *            the id
     */
    FiveBitByteCodec() {
        super(CodecId.FIVE_BIT_BYTE_CODEC);
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
    public byte[] decodeAll(final ByteAlphabet alphabet, final byte[] encodedData, final int length) {
        final byte[] decodedData = new byte[length];
        final int finalNumSymbols = length % SYMBOLS_PER_CHUNK;
        final int endPos = length - finalNumSymbols;
        int decodedPos = 0;
        int encodedPos = 0;
        while (decodedPos < endPos) {
            decodeBlock(alphabet, decodedData, encodedData, decodedPos, encodedPos);
            decodedPos += 8;
            encodedPos += 5;
        }
        while (decodedPos < length) {
            decodedData[decodedPos] = decode(alphabet, encodedData, length, decodedPos);
            decodedPos++;
        }

        return decodedData;
    }

    protected void decodeBlock(final ByteAlphabet alphabet, final byte[] decodedData, final byte[] encodedData,
            int decodedPos, int encodedPos) {
        decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] & MASK);
        decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(
                encodedData[encodedPos++] >>> SHIFT_1A & MASK_1A | encodedData[encodedPos] >>> SHIFT_1B & MASK_1B);
        decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] & MASK);
        decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(
                encodedData[encodedPos++] >>> SHIFT_3A & MASK_3A | encodedData[encodedPos] >>> SHIFT_3B & MASK_3B);
        decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(
                encodedData[encodedPos++] >>> SHIFT_4A & MASK_4A | encodedData[encodedPos] >>> SHIFT_4B & MASK_4B);
        decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] & MASK);
        decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(
                encodedData[encodedPos++] >>> SHIFT_6A & MASK_6A | encodedData[encodedPos] >>> SHIFT_6B & MASK_6B);
        decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos++] & MASK);
    }

    /**
     * Decode.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param pos
     *            the pos
     * @return the byte
     * @see org.biomojo.codec.ByteCodec#decode(byte[], int)
     */
    @Override
    public byte decode(final ByteAlphabet alphabet, final byte[] encodedData, final int decodedLength, final int pos) {
        int ordinal = 0;

        final int start = pos / SYMBOLS_PER_CHUNK * BITS_PER_SYMBOL;
        switch (pos % SYMBOLS_PER_CHUNK) {
        case 0:
            ordinal = encodedData[start] & MASK;
            break;
        case 1:
            ordinal = encodedData[start] >>> SHIFT_1A & MASK_1A | encodedData[start + 1] >>> SHIFT_1B & MASK_1B;
            break;
        case 2:
            ordinal = encodedData[start + 1] & MASK;
            break;
        case 3:
            ordinal = encodedData[start + 1] >>> SHIFT_3A & MASK_3A | encodedData[start + 2] & MASK_3B;
            break;
        case 4:
            ordinal = encodedData[start + 2] >>> SHIFT_4A & MASK_4A | encodedData[start + 3] >>> SHIFT_4B & MASK_4B;
            break;
        case 5:
            ordinal = encodedData[start + 3] & MASK;
            break;
        case 6:
            ordinal = encodedData[start + 3] >>> SHIFT_6A & MASK_6A | encodedData[start + 4] >>> SHIFT_6B & MASK_6B;
            break;
        case 7:
            ordinal = encodedData[start + 4] & MASK;
            break;

        }

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
        final int finalNumSymbols = length % SYMBOLS_PER_CHUNK;
        final int endPos = length - finalNumSymbols;
        int decodedPos = 0;
        int encodedPos = 0;
        int encodedByte = 0;

        // encode three byte chunks
        while (decodedPos < endPos) {
            encodedByte = alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            encodedByte = encodedByte | (alphabet.getOrdinalForSymbol(decodedData[decodedPos]) & MASK_1A) << SHIFT_1A;
            encodedData[encodedPos++] = (byte) encodedByte;

            encodedByte = (alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << SHIFT_1B;
            encodedByte = encodedByte | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            encodedByte = encodedByte | (alphabet.getOrdinalForSymbol(decodedData[decodedPos]) & MASK_3A) << SHIFT_3A;
            encodedData[encodedPos++] = (byte) encodedByte;

            encodedByte = alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) & MASK_3B;
            encodedByte = encodedByte | (alphabet.getOrdinalForSymbol(decodedData[decodedPos]) & MASK_4A) << SHIFT_4A;
            encodedData[encodedPos++] = (byte) encodedByte;

            encodedByte = alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_4B;
            encodedByte = encodedByte | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            encodedByte = encodedByte | (alphabet.getOrdinalForSymbol(decodedData[decodedPos]) & MASK_6A) << SHIFT_6A;
            encodedData[encodedPos++] = (byte) encodedByte;

            encodedByte = alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_6B;
            encodedByte = encodedByte | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            encodedData[encodedPos++] = (byte) encodedByte;

        }

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
        final int ordinal = alphabet.getOrdinalForSymbol(symbol);
        final int start = pos / SYMBOLS_PER_CHUNK * BITS_PER_SYMBOL;
        switch (pos % SYMBOLS_PER_CHUNK) {
        case 0:
            encodedData[start] = (byte) (encodedData[start] & CLEAR_MASK_0 | ordinal);
            break;
        case 1:
            encodedData[start] = (byte) (encodedData[start] & CLEAR_MASK_1A | (ordinal & MASK_1A) << SHIFT_1A);
            encodedData[start + 1] = (byte) (encodedData[start + 1] & CLEAR_MASK_1B | ordinal << SHIFT_1B);
            break;
        case 2:
            encodedData[start + 1] = (byte) (encodedData[start + 1] & CLEAR_MASK_2 | ordinal);
            break;
        case 3:
            encodedData[start + 1] = (byte) (encodedData[start + 1] & CLEAR_MASK_3A | (ordinal & MASK_3A) << SHIFT_3A);
            encodedData[start + 2] = (byte) (encodedData[start + 2] & CLEAR_MASK_3B | (ordinal & MASK_3B));
            break;
        case 4:
            encodedData[start + 2] = (byte) (encodedData[start + 2] & CLEAR_MASK_4A | (ordinal & MASK_4A) << SHIFT_4A);
            encodedData[start + 3] = (byte) (encodedData[start + 3] & CLEAR_MASK_4B | ordinal << SHIFT_4B);
            break;
        case 5:
            encodedData[start + 3] = (byte) (encodedData[start + 3] & CLEAR_MASK_5 | ordinal);
            break;
        case 6:
            encodedData[start + 3] = (byte) (encodedData[start + 3] & CLEAR_MASK_6A | (ordinal & MASK_6A) << SHIFT_6A);
            encodedData[start + 4] = (byte) (encodedData[start + 4] & CLEAR_MASK_6B | ordinal << SHIFT_6B);
            break;
        case 7:
            encodedData[start + 4] = (byte) (encodedData[start + 4] & CLEAR_MASK_7 | ordinal);
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
        return (alphabet.numSymbols() <= NUM_SYMBOLS);
    }

    @Override
    public byte[] decodeBlock(final ByteAlphabet alphabet, final byte[] encodedData, final byte[] decodedBlock,
            final int blockNum) {
        final int encodedPos = blockNum * 5;
        decodeBlock(alphabet, decodedBlock, encodedData, 0, encodedPos);
        return decodedBlock;
    }

    @Override
    public int blockSize(final int blockNum) {
        return SYMBOLS_PER_CHUNK;
    }
}
