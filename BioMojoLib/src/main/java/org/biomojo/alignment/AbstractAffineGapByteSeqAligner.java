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
import org.biomojo.alphabet.GappedByte;
import org.biomojo.sequence.BasicByteSeq;
import org.biomojo.sequence.ByteSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractByteSeqAligner.
 *
 * @param <A>
 *            the generic type
 */
public abstract class AbstractAffineGapByteSeqAligner<A extends GappableByte<A, GappedByte<A>>>
        extends AbstractByteSeqAligner<A> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AbstractAffineGapByteSeqAligner.class.getName());

    /** The gap start penalty. */
    private final int gapOpenPenalty;

    /** The seq1 traceback start. */
    protected int seq1TracebackStart = 0;

    /** The seq2 traceback start. */
    protected int seq2TracebackStart = 0;

    /** The scores. */
    protected int[][] scores;
    protected int[][] scores1;
    protected int[][] scores2;

    /**
     * Instantiates a new abstract byte seq aligner.
     *
     * @param matrix
     *            the matrix
     * @param gapPenalty
     *            the gap penalty
     */
    public AbstractAffineGapByteSeqAligner(final ByteSubstitutionMatrix matrix, final int gapStartPenalty,
            final int gapExtendPenalty) {
        super(matrix, gapExtendPenalty);

        this.gapOpenPenalty = gapStartPenalty;

    }

    @Override
    protected void reallocateMatrices() {
        scores = new int[arrayDim1][arrayDim2];
        scores1 = new int[arrayDim1][arrayDim2];
        scores2 = new int[arrayDim1][arrayDim2];
    }

    /**
     * Calc cell score.
     * 
     * @param seq2Bytes
     * @param seq1Bytes
     *
     * @param leftScore
     *            the left score
     * @param seq1Pos
     *            the seq1 pos
     * @param seq2Pos
     *            the seq2 pos
     * @return the int
     */

    @Override
    protected final int calcCellScore(final byte[] seq1Bytes, final byte[] seq2Bytes, final int seq1Pos,
            final int seq2Pos) {
        final int substitutionScore = matrix.getScore(seq1Bytes[seq1Pos - 1], seq2Bytes[seq2Pos - 1]);

        int cellScore = scores[seq1Pos - 1][seq2Pos - 1] + substitutionScore;
        final int cellScore1 = scores1[seq1Pos - 1][seq2Pos - 1] + substitutionScore;
        final int cellScore2 = scores2[seq1Pos - 1][seq2Pos - 1] + substitutionScore;

        if (cellScore1 > cellScore) {
            cellScore = cellScore1;
        }
        if (cellScore2 > cellScore) {
            scores[seq1Pos][seq2Pos] = cellScore2;
        } else {
            scores[seq1Pos][seq2Pos] = cellScore;
        }

        final int seq1GapScoreOpen = scores[seq1Pos - 1][seq2Pos] + gapOpenPenalty;
        final int seq1GapScoreExtend = scores1[seq1Pos - 1][seq2Pos] + gapExtendPenalty;
        if (seq1GapScoreOpen > seq1GapScoreExtend) {
            scores1[seq1Pos][seq2Pos] = seq1GapScoreOpen;
        } else {
            scores1[seq1Pos][seq2Pos] = seq1GapScoreExtend;
        }

        final int seq2GapScoreOpen = scores[seq1Pos][seq2Pos - 1] + gapOpenPenalty;
        final int seq2GapScoreExtend = scores2[seq1Pos][seq2Pos - 1] + gapExtendPenalty;
        if (seq1GapScoreOpen > seq1GapScoreExtend) {
            scores2[seq1Pos][seq2Pos] = seq2GapScoreOpen;
        } else {
            scores2[seq1Pos][seq2Pos] = seq2GapScoreExtend;
        }

        return cellScore;

    }

    /**
     * Calc traceback.
     *
     * @param alignment
     *            the alignment
     */
    @Override
    protected void calcTraceback(final PairwiseByteSeqAlignment<A> alignment) {
        final GappedByte<A> alphabet1 = seq1.getAlphabet().getGapped();
        final GappedByte<A> alphabet2 = seq2.getAlphabet().getGapped();

        final ByteSeq<GappedByte<A>> align1 = new BasicByteSeq<>(seq1Dim + seq2Dim, alphabet1);
        final ByteSeq<GappedByte<A>> align2 = new BasicByteSeq<>(seq1Dim + seq2Dim, alphabet2);

        final byte gap1 = alphabet1.gapSymbol();
        final byte gap2 = alphabet2.gapSymbol();

        int seq1Pos = seq1TracebackStart;
        int seq2Pos = seq2TracebackStart;
        int matrixPos = 0;

        final byte[] seq1Bytes = seq1.toByteArray();
        final byte[] seq2Bytes = seq2.toByteArray();

        final int matrices[][][] = new int[3][][];
        matrices[0] = scores;
        matrices[1] = scores1;
        matrices[2] = scores2;

        matrixPos = 1;

        while (seq1Pos > 0 && seq2Pos > 0)

        {
            final int currentCellScore = scores[seq1Pos][seq2Pos];
            if (currentCellScore == scores[seq1Pos - 1][seq2Pos - 1]
                    + matrix.getScore(seq1Bytes[seq1Pos - 1], seq2Bytes[seq2Pos - 1])) {
                align1.add(seq1Bytes[seq1Pos - 1]);
                align2.add(seq2Bytes[seq2Pos - 1]);
                seq1Pos--;
                seq2Pos--;
            } else if (currentCellScore == scores[seq1Pos - 1][seq2Pos] + gapOpenPenalty) {
                align1.add(seq1Bytes[seq1Pos - 1]);
                align2.add(gap2);
                seq1Pos--;
            } else {
                align1.add(gap1);
                align2.add(seq2Bytes[seq2Pos - 1]);
                seq2Pos--;
            }
        }

        alignment.setScore(scores[seq1TracebackStart][seq2TracebackStart]);
        alignment.add(align1);
        alignment.add(align2);

    }

    /**
     * Prints the score table.
     */
    protected void printScoreTable() {
        for (int i = 0; i < seq1Dim; i++) {
            final StringBuffer buf = new StringBuffer();
            for (int j = 0; j < seq2Dim; j++) {
                buf.append(Integer.toString(scores[i][j]));
                buf.append("\t");
            }
            logger.info(buf.toString());
        }
    }

}
