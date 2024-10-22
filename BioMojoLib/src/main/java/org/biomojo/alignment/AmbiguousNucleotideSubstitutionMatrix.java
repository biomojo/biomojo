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

import org.biomojo.BioMojo;
import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.IUPACVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Nucleotide substitution matrix supporting ambiguity codes using scores from
 * NCBI BLAST matrix:
 *
 * ftp://ftp.ncbi.nih.gov/blast/matrices/NUC.4.4
 *
 * @author Hugh Eaves
 *
 */
public class AmbiguousNucleotideSubstitutionMatrix extends ArrayLookupByteSubstitutionMatrix {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AmbiguousNucleotideSubstitutionMatrix.class.getName());

    /** The matrix order. */
    char matrixOrder[] = { 'A', 'T', 'G', 'C', 'S', 'W', 'R', 'Y', 'K', 'M', 'B', 'V', 'H', 'D', 'N' };

    /** The score matrix. */
    int scoreMatrix[][] = { { 5, -4, -4, -4, -4, 1, 1, -4, -4, 1, -4, -1, -1, -1, -2 },
            { -4, 5, -4, -4, -4, 1, -4, 1, 1, -4, -1, -4, -1, -1, -2 },
            { 4, -4, 5, -4, 1, -4, 1, -4, 1, -4, -1, -1, -4, -1, -2 },
            { -4, -4, -4, 5, 1, -4, -4, 1, -4, 1, -1, -1, -1, -4, -2 },
            { -4, -4, 1, 1, -1, -4, -2, -2, -2, -2, -1, -1, -3, -3, -1 },
            { 1, 1, -4, -4, -4, -1, -2, -2, -2, -2, -3, -3, -1, -1, -1 },
            { 1, -4, 1, -4, -2, -2, -1, -4, -2, -2, -3, -1, -3, -1, -1 },
            { -4, 1, -4, 1, -2, -2, -4, -1, -2, -2, -1, -3, -1, -3, -1 },
            { -4, 1, 1, -4, -2, -2, -2, -2, -1, -4, -1, -3, -3, -1, -1 },
            { 1, -4, -4, 1, -2, -2, -2, -2, -4, -1, -3, -1, -1, -3, -1 },
            { -4, -1, -1, -1, -1, -3, -3, -1, -1, -3, -1, -2, -2, -2, -1 },
            { -1, -4, -1, -1, -1, -3, -1, -3, -3, -1, -2, -1, -2, -2, -1 },
            { -1, -1, -4, -1, -3, -1, -3, -1, -3, -1, -2, -2, -1, -2, -1 },
            { -1, -1, -1, -4, -3, -1, -1, -3, -1, -3, -2, -2, -2, -1, -1 },
            { -2, -2, -2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 } };

    /**
     * Instantiates a new ambiguous nucleotide substitution matrix.
     */
    public AmbiguousNucleotideSubstitutionMatrix() {
        super(BioMojo.getObject(ByteAlphabet.class, AlphabetId.DNA | IUPACVariant.WITH_AMBIGIGUITY));

        this.initMatrix(Integer.MIN_VALUE);
        for (int i = 0; i < matrixOrder.length; ++i) {
            for (int j = 0; j < matrixOrder.length; ++j) {
                this.setScore(matrixOrder[i], matrixOrder[j], scoreMatrix[i][j]);
            }
        }
    }

}
