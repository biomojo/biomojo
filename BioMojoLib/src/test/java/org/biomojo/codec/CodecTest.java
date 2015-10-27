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

import static org.junit.Assert.assertArrayEquals;

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
        testCodec(CodecId.THREE_BIT_BYTE_CODEC,
                AlphabetId.DNA | IUPACAlphabetVariant.WITH_GAP | IUPACAlphabetVariant.WITH_ANY);
    }

    /**
     * Test four bit.
     */
    @Test
    public void testFourBit() {
        testCodec(CodecId.FOUR_BIT_BYTE_CODEC, AlphabetId.DNA);
    }

    /**
     * Test codec.
     *
     * @param codecId the codec id
     * @param alphabetId the alphabet id
     */
    public void testCodec(final int codecId, final int alphabetId) {
        for (int i = 0; i < 10000; ++i) {
            logger.debug("run {}", i);
            runTest(codecId, alphabetId);
        }
    }

    /**
     * Run test.
     *
     * @param codecId the codec id
     * @param alphabetId the alphabet id
     */
    public void runTest(final int codecId, final int alphabetId) {
        final ByteAlphabet alphabet = Alphabets.getAlphabet(alphabetId, ByteAlphabet.class);
        final ByteCodec codec = Codecs.getCodec(codecId, ByteCodec.class);
        if (!codec.supportsAlphabet(alphabet)) {

        }
        final int length = random.nextInt(1000);
        final byte[] seq = new byte[length];
        for (int i = 0; i < length; ++i) {
            seq[i] = alphabet.getByteSymbolForOrdinal(random.nextInt(alphabet.numCanonicalSymbols()));
        }
        logger.debug("seq     {}", new String(seq));
        final byte[] encoded = codec.encode(alphabet, null, seq.length, seq);
        final byte[] decoded = codec.decode(alphabet, encoded, seq.length);
        logger.debug("decoded {}", new String(decoded));
        assertArrayEquals(seq, decoded);
    }
}
