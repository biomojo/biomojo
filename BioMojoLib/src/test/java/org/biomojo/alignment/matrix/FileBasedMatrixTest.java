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
package org.biomojo.alignment.matrix;

import static org.junit.Assert.assertEquals;

import org.biomojo.alignment.ByteSubstitutionMatrix;
import org.biomojo.alignment.PrecomputedAminoAcidSubstitutionMatrix;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.symbols.AminoAcids;
import org.java0.test.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class FileBasedMatrixTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(FileBasedMatrixTest.class.getName());

    public FileBasedMatrixTest() {
    }

    @Test
    public void testBLOSUM62() throws Exception {
        ByteSubstitutionMatrix matrix = new PrecomputedAminoAcidSubstitutionMatrix("BLOSUM", 62);

        assertEquals(11, matrix.getScore(AminoAcids.TRYPTOPHAN, AminoAcids.TRYPTOPHAN), 0);
        assertEquals(-3, matrix.getScore(AminoAcids.ASPARTIC_ACID_OR_ASPARAGINE, AminoAcids.CYSTEINE), 0);

        checkSumMatrix(matrix, -726);
    }

    @Test
    public void testPAM250() throws Exception {
        ByteSubstitutionMatrix matrix = new PrecomputedAminoAcidSubstitutionMatrix("PAM", 250);

        assertEquals(17, matrix.getScore(AminoAcids.TRYPTOPHAN, AminoAcids.TRYPTOPHAN), 0);
        assertEquals(2, matrix.getScore(AminoAcids.ALANINE, AminoAcids.ALANINE), 0);
        assertEquals(-8, matrix.getScore(AminoAcids.ALANINE, AminoAcids.STOP), 0);

        checkSumMatrix(matrix, -932);
    }

    /**
     * @param matrix
     */
    private void checkSumMatrix(ByteSubstitutionMatrix matrix, float expectedCheckSum) {
        ByteAlphabet alphabet = matrix.getAlphabet();

        float checkSum = 0;
        int cells = 0;
        for (int i = 0; i < alphabet.numCanonicalSymbols(); ++i) {
            for (int j = 0; j < alphabet.numCanonicalSymbols(); ++j) {

                byte from = alphabet.getByteSymbolForOrdinal(i);
                byte to = alphabet.getByteSymbolForOrdinal(j);

                float score = matrix.getScore(from, to);
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
