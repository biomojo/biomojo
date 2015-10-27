/*
 * Copyright (C) 2014  Hugh Eaves
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

public class TwoBitByteCodec extends AbstractByteCodec {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(TwoBitByteCodec.class.getName());

    TwoBitByteCodec(final int id) {
        super(id);
    }

    private static final int BITS_PER_SYMBOL = 2;
    private static final int SYMBOLS_PER_BYTE = 8 / BITS_PER_SYMBOL;
    private static final int NUM_SYMBOLS = 1 << BITS_PER_SYMBOL;

    // bit mask for character at position N in byte
    private static final int MASK_0 = 0xC0;
    private static final int MASK_1 = 0x30;
    private static final int MASK_2 = 0x0C;
    private static final int MASK_3 = 0x03;
    private static final int[] MASK = { MASK_0, MASK_1, MASK_2, MASK_3 };

    // number of left shifts to move bits into correct position in byte
    private static final int SHIFT_0 = 6;
    private static final int SHIFT_1 = 4;
    private static final int SHIFT_2 = 2;
    private static final int SHIFT_3 = 0;
    private static final int[] SHIFT = { SHIFT_0, SHIFT_1, SHIFT_2, SHIFT_3 };

    /**
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
            final int encodedByte = encodedData[encodedPos++];
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((encodedByte & MASK_0) >> SHIFT_0);
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((encodedByte & MASK_1) >> SHIFT_1);
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((encodedByte & MASK_2) >> SHIFT_2);
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal(encodedByte & MASK_3);
        }
        // decode partial byte at the end (if any)
        while (decodedPos < length) {
            final int encodedByte = encodedData[encodedPos];
            final int charNum = decodedPos % SYMBOLS_PER_BYTE;
            decodedData[decodedPos++] = alphabet
                    .getByteSymbolForOrdinal((encodedByte & MASK[charNum]) >> SHIFT[charNum]);
        }
        return decodedData;
    }

    /**
     * @see org.biomojo.codec.ByteCodec#decode(byte[], int)
     */
    @Override
    public byte decode(final ByteAlphabet alphabet, final byte[] encodedData, final int length, final int pos) {
        final int bytePos = pos / SYMBOLS_PER_BYTE;
        final int charPos = pos % SYMBOLS_PER_BYTE;
        return alphabet.getByteSymbolForOrdinal((encodedData[bytePos] & MASK[charPos]) >> SHIFT[charPos]);
    }

    /**
     * @see org.biomojo.codec.ByteCodec#encode(byte[])
     */
    @Override
    public byte[] encode(final ByteAlphabet alphabet, final byte[] oldEncodedData, final int length,
            final byte[] decodedData) {
        final byte[] encodedData = new byte[(length + SYMBOLS_PER_BYTE - 1) / SYMBOLS_PER_BYTE];
        final int finalByteNumChars = length % SYMBOLS_PER_BYTE;
        final int endPos = length - finalByteNumChars;
        int decodedPos = 0;
        int encodedPos = 0;
        int encodedByte = 0;
        // encode full bytes
        while (decodedPos < endPos) {
            encodedData[encodedPos++] = (byte) ((alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_0)
                    | (alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_1)
                    | (alphabet.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_2)
                    | (alphabet.getOrdinalForSymbol(decodedData[decodedPos++])));
        }
        // encode partial byte at the end (if any)
        if (decodedPos < length) {
            encodedByte = 0;
            while (decodedPos < length) {
                encodedByte = (encodedByte << BITS_PER_SYMBOL)
                        | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            }
            encodedByte = encodedByte << SHIFT[finalByteNumChars - 1];
            encodedData[encodedPos] = (byte) encodedByte;
        }
        return encodedData;
    }

    /**
     * @see org.biomojo.codec.ByteCodec#encode(byte[], byte, int)
     */
    @Override
    public void encode(final ByteAlphabet alphabet, final byte[] encodedData, final int length, final byte symbol,
            final int pos) {
        final int bytePos = pos / SYMBOLS_PER_BYTE;
        final int charPos = pos % SYMBOLS_PER_BYTE;
        encodedData[bytePos] = (byte) (encodedData[bytePos] & ~MASK[charPos]
                | (alphabet.getOrdinalForSymbol(symbol) << SHIFT[charPos]));
    }

    /**
     * @see org.biomojo.codec.Codec#supportsAlphabet(org.biomojo.alphabet.Alphabet)
     */
    @Override
    public boolean supportsAlphabet(final Alphabet<Byte> alphabet) {
        return (alphabet.numSymbols() <= NUM_SYMBOLS);
    }
}
