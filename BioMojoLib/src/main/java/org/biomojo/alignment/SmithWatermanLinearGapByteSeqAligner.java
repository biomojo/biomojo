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

import java.util.List;

import org.biomojo.alphabet.GappableByte;
import org.biomojo.sequence.ByteSeq;

/**
 * The Class SmithWatermanAligner.
 *
 * @param <A>
 *            the generic type
 */
public class SmithWatermanLinearGapByteSeqAligner<A extends GappableByte<A>>
        extends AbstractLinearGapByteSeqAligner<A> {

    /** The max score. */
    private int maxScore = Integer.MIN_VALUE;

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
        // int score = 0;
        // final int substitutionScore = scores[seq1Pos - 1][seq2Pos - 1]
        // + matrix.getScore(seq1Bytes[seq1Pos - 1], seq2Bytes[seq2Pos - 1]);
        // final int seq1GapScore = scores[seq1Pos - 1][seq2Pos] +
        // gapExtendPenalty;
        // final int seq2GapScore = scores[seq1Pos][seq2Pos - 1] +
        // gapExtendPenalty;
        //
        // if (substitutionScore > score) {
        // score = substitutionScore;
        // }
        // if (seq1GapScore > score) {
        // score = seq1GapScore;
        // }
        // if (seq2GapScore > score) {
        // score = seq2GapScore;
        // }
        if (score > maxScore) {
            maxScore = score;
            seq1TracebackStart = seq1Pos;
            seq2TracebackStart = seq2Pos;
        }
        // scores[seq1Pos][seq2Pos] = score;

        return score;
    }

    @Override
    protected void initTraceback() {
        maxScore = Integer.MIN_VALUE;
        seq1TracebackStart = Integer.MIN_VALUE;
        seq2TracebackStart = Integer.MIN_VALUE;
    }

    @Override
    public Alignment<ByteSeq<A>> align(final List<ByteSeq<A>> sequences) {
        final Alignment<ByteSeq<A>> alignment = super.align(sequences);
        // this.printScoreTable();
        return alignment;
    }
    //
    // @Override
    // protected void calcCellScores() {
    // final byte[] seq1Bytes = seq1.toByteArray();
    // final byte[] seq2Bytes = seq2.toByteArray();
    // for (int seq1Pos = 1; seq1Pos < seq1Dim; seq1Pos++) {
    // // int prevScore = scores[seq1Pos][0];
    // // final int up = seq1Pos - 1;
    // // final int[] prevRow = scores[up];
    // for (int seq2Pos = 1; seq2Pos < seq2Dim; seq2Pos++) {
    // // final int subScore = scores[seq1Pos - 1][seq2Pos - 1]
    // // + matrix.getScore(seq1Bytes[seq1Pos - 1], seq2Bytes[seq2Pos -
    // // 1]);
    // // final int seq1GapScore = scores[seq1Pos - 1][seq2Pos] +
    // // gapExtendPenalty;
    // // final int seq2GapScore = scores[seq1Pos][seq2Pos - 1] +
    // // gapExtendPenalty;
    // // final int left = seq2Pos - 1;
    //
    // // final int subScore = prevRow[left] +
    // // matrix.getScore(seq1Bytes[up], seq2Bytes[left]);
    // // final int seq1GapScore = prevRow[seq2Pos] + gapExtendPenalty;
    // // final int seq2GapScore = prevScore + gapExtendPenalty;
    // // final int left = seq2Pos - 1;
    // final int subScore = scores[seq1Pos - 1][seq2Pos - 1]
    // + matrix.getScore(seq1Bytes[seq1Pos - 1], seq2Bytes[seq2Pos - 1]);
    // final int seq1GapScore = scores[seq1Pos - 1][seq2Pos] + gapExtendPenalty;
    // final int seq2GapScore = scores[seq1Pos][seq2Pos - 1] + gapExtendPenalty;
    // int score = 0;
    // if (subScore > score) {
    // score = subScore;
    // }
    // if (seq1GapScore > score) {
    // score = seq1GapScore;
    // }
    // if (seq2GapScore > score) {
    // score = seq2GapScore;
    // }
    //
    // if (score > maxScore) {
    // maxScore = score;
    // seq1TracebackStart = seq1Pos;
    // seq2TracebackStart = seq2Pos;
    // }
    // scores[seq1Pos][seq2Pos] = score;
    // }
    // }
    // }

}
