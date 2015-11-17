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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Random;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.IUPACAlphabetVariant;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class CodecTest.
 *
 * @author Hugh Eaves
 */
public class CodecTest {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(CodecTest.class.getName());

    /** The random. */
    public Random random = new Random(0);

    /**
     * Test null.
     */
    @Test
    public void testNull() {
        testCodec(CodecId.NULL_BYTE_CODEC, AlphabetId.DNA);
    }

    /**
     * Test two bit.
     */
    @Test
    public void testTwoBit() {
        testCodec(CodecId.TWO_BIT_BYTE_CODEC, AlphabetId.DNA);
    }

    /**
     * Test two bit fail.
     */
    @Test
    public void testTwoBitFail() {
        // testCodec(CodecId.TWO_BIT_BYTE_CODEC, AlphabetId.DNA
        // | IUPACAlphabetVariant.WITH_GAP);
    }

    /**
     * Test three bit.
     */
    @Test
    public void testThreeBit() {
        testCodec(CodecId.THREE_BIT_BYTE_CODEC, AlphabetId.DNA | IUPACAlphabetVariant.WITH_NON_CANONICAL);
    }

    /**
     * Test four bit.
     */
    @Test
    public void testFourBit() {
        testCodec(CodecId.FOUR_BIT_BYTE_CODEC, AlphabetId.DNA | IUPACAlphabetVariant.WITH_NON_CANONICAL
                | IUPACAlphabetVariant.WITH_GAP | IUPACAlphabetVariant.WITH_ANY);
    }

    @Test
    public void testFiveBit() {
        testCodec(CodecId.FIVE_BIT_BYTE_CODEC,
                AlphabetId.AMINO_ACID | IUPACAlphabetVariant.WITH_GAP | IUPACAlphabetVariant.WITH_ANY);
    }

    /**
     * Test codec.
     *
     * @param codecId
     *            the codec id
     * @param alphabetId
     *            the alphabet id
     */
    public void testCodec(final int codecId, final int alphabetId) {
        for (int i = 0; i < 10000; ++i) {
            // logger.debug("run {}", i);
            runTest(codecId, alphabetId);
        }
    }

    /**
     * Run test.
     *
     * @param codecId
     *            the codec id
     * @param alphabetId
     *            the alphabet id
     */
    public void runTest(final int codecId, final int alphabetId) {
        final ByteAlphabet alphabet = Alphabets.getAlphabet(alphabetId, ByteAlphabet.class);
        final ByteCodec codec = Codecs.getCodec(codecId, ByteCodec.class);
        if (!codec.supportsAlphabet(alphabet)) {

        }
        final int length = random.nextInt(1000);
        final byte[] seq = new byte[length];
        for (int i = 0; i < length; ++i) {
            seq[i] = alphabet.getByteSymbolForOrdinal(random.nextInt(alphabet.numSymbols()));
        }
        final byte[] encoded = codec.encode(alphabet, null, seq.length, seq);

        // logger.debug("seq {}", new String(seq));
        // StringBuffer message = new StringBuffer();
        // for (int i = 0; i < length; ++i) {
        //
        // message.append(BitStringUtil.toString(alphabet.getOrdinalForSymbol(seq[i]),
        // 5));
        // message.append(" ");
        // }
        // logger.debug("ordinals = {}", message.toString());
        // logger.debug(" {}",
        // "11100000|11322222|44443333|46655555|66677777
        // 11100000|11322222|44443333|46655555|66677777");
        // logger.debug("encoded = {}", split(BitStringUtil.toString(encoded),
        // 8));

        byte[] decoded = codec.decode(alphabet, encoded, seq.length);
        assertArrayEquals(seq, decoded);
        for (int i = 0; i < seq.length; ++i) {
            // logger.debug("decoding position {}:", i);
            byte decodedByte = codec.decode(alphabet, encoded, seq.length, i);
            // logger.debug("ord = {}, seq[i] = {}, decoded = {}",
            // alphabet.getOrdinalForSymbol(seq[i]), seq[i],
            // decodedByte);
            // assertEquals(seq[i], decodedByte);
        }
        // logger.debug("encoded = {} (A)", BitStringUtil.toString(encoded));

        Arrays.fill(encoded, (byte) 0);
        for (int i = 0; i < seq.length; ++i) {
            codec.encode(alphabet, encoded, length, seq[i], i);
        }

        decoded = codec.decode(alphabet, encoded, seq.length);
        assertArrayEquals(seq, decoded);
        // logger.debug("encoded = {} (B)", BitStringUtil.toString(encoded));

        if (length != 0) {
            for (int i = 0; i < 1000; ++i) {
                int pos = random.nextInt(length);
                codec.encode(alphabet, encoded, length, seq[pos], pos);
                // logger.debug("encoded = {} {}",
                // split(BitStringUtil.toString(encoded), 8), pos);
                // decoded = codec.decode(alphabet, encoded, seq.length);
                // assertArrayEquals(seq, decoded);
            }
        }
        // logger.debug("encoded = {}", BitStringUtil.toString(encoded));
        decoded = codec.decode(alphabet, encoded, seq.length);
        assertArrayEquals(seq, decoded);
        for (int i = 0; i < seq.length; ++i) {
            assertEquals(seq[i], codec.decode(alphabet, encoded, seq.length, i));
        }

        // logger.debug("decoded {}", new String(decoded));
        assertArrayEquals(seq, decoded);
    }

    String split(String str, int interval) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < str.length(); ++i) {
            result.append(str.charAt(i));
            if (i % interval == interval - 1) {
                result.append(' ');
            }
        }
        return result.toString();
    }
}
