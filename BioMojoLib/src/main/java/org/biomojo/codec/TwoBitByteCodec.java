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
public class TwoBitByteCodec extends AbstractByteCodec {

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

    /** The Constant MASK. */
    private static final int[] MASK = { 0xC0, 0x30, 0x0C, SYMBOL_MASK };

    /** The Constant SHIFT_0. */
    // number of left shifts to move bits into correct position in byte
    private static final int SHIFT_0 = 6;

    /** The Constant SHIFT_1. */
    private static final int SHIFT_1 = 4;

    /** The Constant SHIFT_2. */
    private static final int SHIFT_2 = 2;

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
        final int endPos = length - length % SYMBOLS_PER_BYTE;
        int decodedPos = 0;
        int encodedPos = 0;
        // decode full bytes
        while (decodedPos < endPos) {
            // logger.error("decoding {} - {}", decodedPos, decodedPos + 3);
            final int encodedByte = encodedData[encodedPos++];
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((encodedByte >> SHIFT_0 & SYMBOL_MASK));
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((encodedByte >> SHIFT_1 & SYMBOL_MASK));
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((encodedByte >> SHIFT_2 & SYMBOL_MASK));
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedByte & SYMBOL_MASK);
        }
        // decode partial byte at the end (if any)
        if (decodedPos < length) {
            final int encodedByte = encodedData[encodedPos];
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedByte >> SHIFT_0 & SYMBOL_MASK);
            if (decodedPos < length) {
                decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedByte >> SHIFT_1 & SYMBOL_MASK);
            }
            if (decodedPos < length) {
                decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedByte >> SHIFT_2 & SYMBOL_MASK);
            }
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
        final int bytePos = pos / SYMBOLS_PER_BYTE;
        final int symbolPos = pos % SYMBOLS_PER_BYTE;
        return alphabet
                .getByteSymbolForOrdinal(encodedData[bytePos] >> (SHIFT_0 - BITS_PER_SYMBOL * symbolPos) & SYMBOL_MASK);
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
        final byte[] encodedData = new byte[(length + SYMBOLS_PER_BYTE - 1) / SYMBOLS_PER_BYTE];
        final int finalByteNumSymbols = length % SYMBOLS_PER_BYTE;
        final int endPos = length - finalByteNumSymbols;
        int decodedPos = 0;
        int encodedPos = 0;
        int encodedByte = 0;
        // encode full bytes
        while (decodedPos < endPos) {
            // logger.error("encoding {} - {}", decodedPos, decodedPos + 3);
            encodedByte = alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            encodedByte = (encodedByte << BITS_PER_SYMBOL) | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            encodedByte = (encodedByte << BITS_PER_SYMBOL) | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            encodedByte = (encodedByte << BITS_PER_SYMBOL) | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            encodedData[encodedPos++] = (byte) encodedByte;
        }
        // encode partial byte at the end (if any)
        if (decodedPos < length) {
            encodedByte = alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            while (decodedPos < length) {
                encodedByte = (encodedByte << BITS_PER_SYMBOL)
                        | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            }
            encodedByte = encodedByte << ((NUM_SYMBOLS - finalByteNumSymbols) * BITS_PER_SYMBOL);
            encodedData[encodedPos] = (byte) encodedByte;
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
        final int bytePos = pos / SYMBOLS_PER_BYTE;
        final int symbolPos = pos % SYMBOLS_PER_BYTE;
        final int shift = SHIFT_0 - BITS_PER_SYMBOL * symbolPos;
        encodedData[bytePos] = (byte) (encodedData[bytePos] & ~(SYMBOL_MASK << shift)
                | (alphabet.getOrdinalForSymbol(symbol) << shift));
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
