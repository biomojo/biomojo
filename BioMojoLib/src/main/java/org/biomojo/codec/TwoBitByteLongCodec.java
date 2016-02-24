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
 * The Class TwoBitByteCodec.
 */
public class TwoBitByteLongCodec extends AbstractByteLongCodec {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(TwoBitByteLongCodec.class.getName());

    /** The Constant BITS_PER_SYMBOL. */
    private static final int BITS_PER_SYMBOL = 2;

    private static final int BITS_PER_LONG = 64;

    /** The Constant SYMBOLS_PER_LONG. */
    private static final int SYMBOLS_PER_LONG = BITS_PER_LONG / BITS_PER_SYMBOL;

    private static final int LENGTH_MASK = SYMBOLS_PER_LONG - 1;

    /** The Constant NUM_SYMBOLS. */
    private static final int NUM_SYMBOLS = 1 << BITS_PER_SYMBOL;

    /** The Constant SYMBOL_MASK. */
    private static final long SYMBOL_MASK = 0x03;

    /**
     * Instantiates a new two bit byte codec.
     *
     * @param id
     *            the id
     */
    TwoBitByteLongCodec() {
        super(CodecId.TWO_BIT_LONG_CODEC);
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
    public byte[] decodeAll(final ByteAlphabet alphabet, final long[] encodedData, final int length) {
        final byte[] decodedData = new byte[length];
        final int blockEndPos = length - (length & LENGTH_MASK);
        int decodedPos = 0;
        int encodedPos = 0;
        // decode full blocks
        while (decodedPos < blockEndPos) {
            long encoded = encodedData[encodedPos++];
            for (int i = 0; i < SYMBOLS_PER_LONG; ++i) {
                decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((int) (encoded & SYMBOL_MASK));
                encoded = encoded >> BITS_PER_SYMBOL;
            }
        }
        // decode partial block at the end (if any)
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
    public byte decode(final ByteAlphabet alphabet, final long[] encodedData, final int decodedLength, final int pos) {
        final int encodedPos = pos >>> 5; // fast divide by 32
        final int shift = (pos & LENGTH_MASK) << 1;
        return alphabet.getByteSymbolForOrdinal((int) (encodedData[encodedPos] >>> shift & SYMBOL_MASK));

    }

    @Override
    public long[] encode(final ByteAlphabet alphabet, final long[] oldEncodedData, final int length,
            final byte[] decodedData) {
        final int arrayLength = (length + SYMBOLS_PER_LONG - 1) >>> 5;
        final long[] encodedData = new long[arrayLength];
        final int endPos = length - (length & LENGTH_MASK);
        int decodedPos = 0;
        int encodedPos = 0;
        // encode full blocks
        while (decodedPos < endPos) {
            long encoded = alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            for (int shift = BITS_PER_SYMBOL; shift <= BITS_PER_LONG - BITS_PER_SYMBOL; shift += BITS_PER_SYMBOL) {
                encoded = encoded | ((long) alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << shift;
            }

            // final long encoded =
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 2
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 4
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 6
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 8
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 10
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 12
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 14
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 16
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 18
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 20
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 22
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 24
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 26
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 28
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 30
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 32
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 34
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 36
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 38
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 40
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 42
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 44
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 46
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 48
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 50
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 52
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 54
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 56
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 58
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 60
            // | ((long)
            // alphabet.getOrdinalForSymbol(decodedData[decodedPos++])) << 62;

            encodedData[encodedPos++] = encoded;
        }
        // encode partial block at the end (if any)
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
    public void encode(final ByteAlphabet alphabet, final long[] encodedData, final int length, final byte symbol,
            final int pos) {
        final int encodedPos = pos >>> 5; // fast divide by 32
        final int shift = (pos & LENGTH_MASK) << 1;
        final long clearMask = ~(SYMBOL_MASK << shift);

        encodedData[encodedPos] = encodedData[encodedPos] & clearMask
                | ((long) alphabet.getOrdinalForSymbol(symbol) << shift);

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
    public byte[] decodeBlock(final ByteAlphabet alphabet, final long[] encodedData, final byte[] decodedBlock,
            final int blockNum) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int blockSize(final int blockNum) {
        return SYMBOLS_PER_LONG;
    }
}
