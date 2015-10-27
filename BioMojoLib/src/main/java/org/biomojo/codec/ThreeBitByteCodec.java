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

public class ThreeBitByteCodec extends AbstractByteCodec {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ThreeBitByteCodec.class.getName());

    ThreeBitByteCodec(final int id) {
        super(id);
    }

    private static final int BITS_PER_SYMBOL = 3;
    private static final int NUM_SYMBOLS = 1 << BITS_PER_SYMBOL;

    /**
     * @see org.biomojo.codec.ByteCodec#decode(byte[])
     */
    @Override
    public byte[] decode(final ByteAlphabet alphabet, final byte[] encodedData, final int length) {
        final byte[] decodedData = new byte[length];
        int decodedPos = 0;
        int encodedPos = 0;
        int numBits = 0;
        int bits = 0;
        while (decodedPos < length) {
            if (numBits < BITS_PER_SYMBOL) {
                bits = (bits << 8) | (encodedData[encodedPos++] & 0xff);
                numBits += 8;
            }
            numBits = numBits - BITS_PER_SYMBOL;
            decodedData[decodedPos++] = alphabet.getByteSymbolForOrdinal((bits >> numBits) & 0x07);

        }
        return decodedData;
    }

    /**
     * @see org.biomojo.codec.ByteCodec#decode(byte[], int)
     */
    @Override
    public byte decode(final ByteAlphabet alphabet, final byte[] encodedData, final int length, final int pos) {
        return 0;
    }

    /**
     * @see org.biomojo.codec.ByteCodec#encode(byte[])
     */
    @Override
    public byte[] encode(final ByteAlphabet alphabet, final byte[] oldEncodedData, final int length,
            final byte[] decodedData) {
        // TODO Handle overflow
        final byte[] encodedData = new byte[((length * BITS_PER_SYMBOL - 1) / 8) + 1];
        int decodedPos = 0;
        int encodedPos = 0;
        int numBits = 0;
        int bits = 0;
        while (decodedPos < length) {
            bits = bits << BITS_PER_SYMBOL;
            bits = bits | alphabet.getOrdinalForSymbol(decodedData[decodedPos++]);
            numBits += BITS_PER_SYMBOL;
            if (numBits >= 8) {
                numBits -= 8;
                encodedData[encodedPos++] = (byte) ((bits >> numBits) & 0xff);
            }
        }
        if (numBits > 0) {
            bits = bits << (8 - numBits);
            encodedData[encodedPos++] = (byte) (bits & 0xff);
        }
        return encodedData;

    }

    /**
     * @see org.biomojo.codec.ByteCodec#encode(byte[], byte, int)
     */
    @Override
    public void encode(final ByteAlphabet alphabet, final byte[] encodedData, final int length, final byte symbol,
            final int pos) {

    }

    /**
     * @see org.biomojo.codec.Codec#supportsAlphabet(org.biomojo.alphabet.Alphabet)
     */
    @Override
    public boolean supportsAlphabet(final Alphabet<Byte> alphabet) {
        return (alphabet.numCanonicalSymbols() <= NUM_SYMBOLS);
    }
}
