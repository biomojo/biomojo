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
 * The Class SmithWatermanAligner.
 *
 * @param <A>
 *            the generic type
 */
public class SmithWatermanLinearGapByteSeqAligner<A extends GappableByte<A>> extends AbstractLinearGapByteSeqAligner<A> {

    /** The max score. */
    private int maxScore;

    /**
     * Instantiates a new smith waterman aligner.
     *
     * @param matrix
     *            the matrix
     * @param gapPenalty
     *            the gap penalty
     */
    public SmithWatermanLinearGapByteSeqAligner(final ByteSubstitutionMatrix matrix, final int gapPenalty) {
        super(matrix, gapPenalty);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alignment.AbstractByteSeqAligner#initScoreMatrix()
     */
    @Override
    protected void clearScores() {
        maxScore = 0;
        for (int i = 1; i < seq1Dim; ++i) {
            scores[i][0] = 0;
        }

        for (int i = 1; i < seq2Dim; ++i) {
            scores[0][i] = 0;
        }
    }

    /**
     * Calc cell score2.
     *
     * @param seq1Pos
     *            the seq1 pos
     * @param seq2Pos
     *            the seq2 pos
     * @return the int
     */
    @Override
    protected final int calcCellScore(final byte[] seq1Bytes, final byte[] seq2Bytes, final int seq1Pos,
            final int seq2Pos) {
        int score = super.calcCellScore(seq1Bytes, seq2Bytes, seq1Pos, seq2Pos);
        if (score < 0) {
            score = 0;
            scores[seq1Pos][seq2Pos] = 0;
        }
        if (score > maxScore) {
            seq1TracebackStart = seq1Pos;
            seq2TracebackStart = seq2Pos;
        }

        return score;
    }

    @Override
    protected void initTraceback() {
        maxScore = -1;
        seq1TracebackStart = -1;
        seq2TracebackStart = -1;
    }
}
