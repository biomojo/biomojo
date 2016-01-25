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

package org.biomojo.alignment;

import static org.junit.Assert.assertEquals;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.symbols.AminoAcids;
import org.java0.test.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FileBasedMatrixTest.
 *
 * @author Hugh Eaves
 */
public class FileBasedMatrixTest extends BaseTest {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(FileBasedMatrixTest.class.getName());

    /**
     * Instantiates a new file based matrix test.
     */
    public FileBasedMatrixTest() {
    }

    /**
     * Test blosu m62.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testBLOSUM62() throws Exception {
        final ByteSubstitutionMatrix matrix = new PrecomputedAminoAcidSubstitutionMatrix("BLOSUM", 62);

        assertEquals(11, matrix.getScore(AminoAcids.TRYPTOPHAN, AminoAcids.TRYPTOPHAN), 0);
        assertEquals(-3, matrix.getScore(AminoAcids.ASPARTIC_ACID_OR_ASPARAGINE, AminoAcids.CYSTEINE), 0);

        checkSumMatrix(matrix, -726);
    }

    /**
     * Test pa m250.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testPAM250() throws Exception {
        final ByteSubstitutionMatrix matrix = new PrecomputedAminoAcidSubstitutionMatrix("PAM", 250);

        assertEquals(17, matrix.getScore(AminoAcids.TRYPTOPHAN, AminoAcids.TRYPTOPHAN), 0);
        assertEquals(2, matrix.getScore(AminoAcids.ALANINE, AminoAcids.ALANINE), 0);
        assertEquals(-8, matrix.getScore(AminoAcids.ALANINE, AminoAcids.STOP), 0);

        checkSumMatrix(matrix, -932);
    }

    /**
     * Check sum matrix.
     *
     * @param matrix
     *            the matrix
     * @param expectedCheckSum
     *            the expected check sum
     */
    private void checkSumMatrix(final ByteSubstitutionMatrix matrix, final float expectedCheckSum) {
        final ByteAlphabet alphabet = matrix.getAlphabet();

        float checkSum = 0;
        int cells = 0;
        for (int i = 0; i < alphabet.numCanonicalSymbols(); ++i) {
            for (int j = 0; j < alphabet.numCanonicalSymbols(); ++j) {

                final byte from = alphabet.getByteSymbolForOrdinal(i);
                final byte to = alphabet.getByteSymbolForOrdinal(j);

                final float score = matrix.getScore(from, to);
                checkSum += score;
                cells++;
                logger.debug("from: {} to: {} score: {} cells: {}", (char) from, (char) to, score, cells);

                assertEquals(matrix.getScore(from, to), matrix.getScore(to, from), 0);

            }
        }

        assertEquals(expectedCheckSum, checkSum, 0);
        assertEquals(576, cells, 0);
    }
}
