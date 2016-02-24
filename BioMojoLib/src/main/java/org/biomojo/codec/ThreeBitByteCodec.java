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
 * byte--: 00000000|11111111|22222222
 * 
 * bit---: 76543210|76543210|76543210
 * 
 * symbol: 22111000|25444333|55777666
 * 
 * mask--: AA111000|BA444333|BB777666
 */
public class ThreeBitByteCodec extends AbstractByteByteCodec {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ThreeBitByteCodec.class.getName());

    /** The Constant BITS_PER_SYMBOL. */
    private static final int BITS_PER_SYMBOL = 3;

    private static final int SYMBOLS_PER_CHUNK = 8;

    /** The Constant NUM_SYMBOLS. */
    private static final int NUM_SYMBOLS = 1 << BITS_PER_SYMBOL;

    private static final int MASK = 0b111;

    private static final int SHIFT_0 = 0;
    private static final int SHIFT_1 = 3;
    private static final int SHIFT_2A = 5;
    private static final int SHIFT_2B = 7;
    private static final int SHIFT_3 = 0;
    private static final int SHIFT_4 = 3;
    private static final int SHIFT_5A = 4;
    private static final int SHIFT_5B = 6;
    private static final int SHIFT_6 = 0;
    private static final int SHIFT_7 = 3;

    private static final int MASK_2A = 0b110;
    private static final int MASK_2B = 0b001;
    private static final int MASK_5A = 0b100;
    private static final int MASK_5B = 0b011;

    private static final int CLEAR_MASK_0 = ~MASK;
    private static final int CLEAR_MASK_1 = ~(MASK << SHIFT_1);
    private static final int CLEAR_MASK_2A = ~(MASK_2A << SHIFT_2A);
    private static final int CLEAR_MASK_2B = ~(MASK_2B << SHIFT_2B);
    private static final int CLEAR_MASK_3 = ~MASK;
    private static final int CLEAR_MASK_4 = ~(MASK << SHIFT_4);
    private static final int CLEAR_MASK_5A = ~(MASK_5A << SHIFT_5A);
    private static final int CLEAR_MASK_5B = ~(MASK_5B << SHIFT_5B);
    private static final int CLEAR_MASK_6 = ~MASK;
    private static final int CLEAR_MASK_7 = ~(MASK << SHIFT_7);

    /**
     * Instantiates a new three bit byte codec.
     *
     * @param id
     *            the id
     */
    ThreeBitByteCodec() {
        super(CodecId.THREE_BIT_BYTE_CODEC);
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
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] & MASK);
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] >>> SHIFT_1 & MASK);
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(
                    encodedData[encodedPos++] >>> SHIFT_2A & MASK_2A | encodedData[encodedPos] >>> 31);
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] & MASK);
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] >>> SHIFT_4 & MASK);
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(
                    encodedData[encodedPos++] >>> SHIFT_5A & MASK_5A | encodedData[encodedPos] >>> SHIFT_5B & MASK_5B);
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] & MASK);
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos++] >>> SHIFT_7 & MASK);
        }
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
     * @param pos
     *            the pos
     * @return the byte
     * @see org.biomojo.codec.ByteCodec#decode(byte[], int)
     */
    @Override
    public byte decode(final ByteAlphabet alphabet, final byte[] encodedData, final int decodedLength, final int pos) {
        int ordinal = 0;

        // TODO Use bit shift ops instead of div / mod operator
        final int start = pos / SYMBOLS_PER_CHUNK * BITS_PER_SYMBOL;
        switch (pos % SYMBOLS_PER_CHUNK) {
        case 0:
            ordinal = encodedData[start] & MASK;
            break;
        case 1:
            ordinal = encodedData[start] >>> SHIFT_1 & MASK;
            break;
        case 2:
            ordinal = encodedData[start] >>> SHIFT_2A & MASK_2A | encodedData[start + 1] >>> 31;
            break;
        case 3:
            ordinal = encodedData[start + 1] & MASK;
            break;
        case 4:
            ordinal = encodedData[start + 1] >>> SHIFT_4 & MASK;
            break;
        case 5:
            ordinal = encodedData[start + 1] >>> SHIFT_5A & MASK_5A | encodedData[start + 2] >>> SHIFT_5B & MASK_5B;
            break;
        case 6:
            ordinal = encodedData[start + 2] & MASK;
            break;
        case 7:
            ordinal = encodedData[start + 2] >>> SHIFT_7 & MASK;
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
            encodedByte = encodedByte | (alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_1);
            encodedByte = encodedByte | ((alphabet.getOrdinalForSymbol(decodedData[decodedPos]) & MASK_2A) << SHIFT_2A);
            encodedData[encodedPos++] = (byte) encodedByte;

            encodedByte = alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_2B;
            encodedByte = encodedByte | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            encodedByte = encodedByte | (alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_4);
            encodedByte = encodedByte | (alphabet.getOrdinalForSymbol(decodedData[decodedPos]) & MASK_5A) << SHIFT_5A;
            encodedData[encodedPos++] = (byte) encodedByte;

            encodedByte = alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_5B;
            encodedByte = encodedByte | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            encodedByte = encodedByte | (alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_7);
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

        // TODO Use bit shift ops instead of div / mod operator

        final int start = pos / SYMBOLS_PER_CHUNK * BITS_PER_SYMBOL;
        switch (pos % SYMBOLS_PER_CHUNK) {
        case 0:
            encodedData[start] = (byte) (encodedData[start] & CLEAR_MASK_0 | ordinal);
            break;
        case 1:
            encodedData[start] = (byte) (encodedData[start] & CLEAR_MASK_1 | ordinal << SHIFT_1);
            break;
        case 2:
            encodedData[start] = (byte) (encodedData[start] & CLEAR_MASK_2A | (ordinal & MASK_2A) << SHIFT_2A);
            encodedData[start + 1] = (byte) (encodedData[start + 1] & CLEAR_MASK_2B | ordinal << SHIFT_2B);
            break;
        case 3:
            encodedData[start + 1] = (byte) (encodedData[start + 1] & CLEAR_MASK_3 | ordinal);
            break;
        case 4:
            encodedData[start + 1] = (byte) (encodedData[start + 1] & CLEAR_MASK_4 | ordinal << SHIFT_4);
            break;
        case 5:
            encodedData[start + 1] = (byte) (encodedData[start + 1] & CLEAR_MASK_5A | (ordinal & MASK_5A) << SHIFT_5A);
            encodedData[start + 2] = (byte) (encodedData[start + 2] & CLEAR_MASK_5B | ordinal << SHIFT_5B);
            break;
        case 6:
            encodedData[start + 2] = (byte) (encodedData[start + 2] & CLEAR_MASK_6 | ordinal);
            break;
        case 7:
            encodedData[start + 2] = (byte) (encodedData[start + 2] & CLEAR_MASK_7 | ordinal << SHIFT_7);
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
        int encodedPos = blockNum * 3;
        decodedBlock[0] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] & MASK);
        decodedBlock[1] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] >>> SHIFT_1 & MASK);
        decodedBlock[2] = alphabet.getByteSymbolForOrdinal(
                encodedData[encodedPos++] >>> SHIFT_2A & MASK_2A | encodedData[encodedPos] >>> 31);
        decodedBlock[3] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] & MASK);
        decodedBlock[4] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] >>> SHIFT_4 & MASK);
        decodedBlock[5] = alphabet.getByteSymbolForOrdinal(
                encodedData[encodedPos++] >>> SHIFT_5A & MASK_5A | encodedData[encodedPos] >>> SHIFT_5B & MASK_5B);
        decodedBlock[6] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos] & MASK);
        decodedBlock[7] = alphabet.getByteSymbolForOrdinal(encodedData[encodedPos++] >>> SHIFT_7 & MASK);
        return decodedBlock;
    }

    @Override
    public int blockSize(final int blockNum) {
        return SYMBOLS_PER_CHUNK;
    }
}