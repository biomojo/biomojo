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

import org.biomojo.alphabet.GappableByte;

/**
 * The Class NeedlemanWunschAligner.
 *
 * @param <A>
 *            the generic type
 */
public class NeedlemanWunschLinearGapByteSeqAligner<A extends GappableByte<A>> extends AbstractLinearGapByteSeqAligner<A> {

    /**
     * Instantiates a new needleman wunsch aligner.
     *
     * @param matrix
     *            the matrix
     * @param gapPenalty
     *            the gap penalty
     */
    public NeedlemanWunschLinearGapByteSeqAligner(final ByteSubstitutionMatrix matrix, final int gapPenalty) {
        super(matrix, gapPenalty);
    }

    @Override
    protected void clearScores() {
        for (int i = 1; i < seq1Dim; ++i) {
            scores[i][0] = i * gapExtendPenalty;
        }

        for (int i = 1; i < seq2Dim; ++i) {
            scores[0][i] = i * gapExtendPenalty;
        }
    }

    @Override
    protected void initTraceback() {
        seq1TracebackStart = seq1Dim - 1;
        seq2TracebackStart = seq2Dim - 1;
    }
}
