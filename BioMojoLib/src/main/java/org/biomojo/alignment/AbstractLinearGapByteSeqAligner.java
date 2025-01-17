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
public abstract class AbstractLinearGapByteSeqAligner<A extends GappableByte<A, GappedByte<A>>>
        extends AbstractByteSeqAligner<A> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AbstractLinearGapByteSeqAligner.class.getName());

    /** The seq1 traceback start. */
    protected int seq1TracebackStart = 0;

    /** The seq2 traceback start. */
    protected int seq2TracebackStart = 0;

    /** The scores. */
    protected int[][] scores;

    /**
     * Instantiates a new abstract byte seq aligner.
     *
     * @param matrix
     *            the matrix
     * @param gapPenalty
     *            the gap penalty
     */
    public AbstractLinearGapByteSeqAligner(final ByteSubstitutionMatrix matrix, final int gapExtendPenalty) {
        super(matrix, gapExtendPenalty);
    }

    @Override
    protected void reallocateMatrices() {
        scores = new int[arrayDim1][arrayDim2];
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
    protected int calcCellScore(final byte[] seq1Bytes, final byte[] seq2Bytes, final int seq1Pos, final int seq2Pos) {
        int substitutionScore = scores[seq1Pos - 1][seq2Pos - 1]
                + matrix.getScore(seq1Bytes[seq1Pos - 1], seq2Bytes[seq2Pos - 1]);
        final int seq1GapScore = scores[seq1Pos - 1][seq2Pos] + gapExtendPenalty;
        final int seq2GapScore = scores[seq1Pos][seq2Pos - 1] + gapExtendPenalty;

        if (seq1GapScore > substitutionScore) {
            substitutionScore = seq1GapScore;
        }
        if (seq2GapScore > substitutionScore) {
            substitutionScore = seq2GapScore;
        }

        scores[seq1Pos][seq2Pos] = substitutionScore;
        return (substitutionScore);
    }

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

        final byte[] seq1Bytes = seq1.toByteArray();
        final byte[] seq2Bytes = seq2.toByteArray();

        while (seq1Pos > 0 && seq2Pos > 0)

        {
            final int currentCellScore = scores[seq1Pos][seq2Pos];
            if (currentCellScore == scores[seq1Pos - 1][seq2Pos - 1]
                    + matrix.getScore(seq1Bytes[seq1Pos - 1], seq2Bytes[seq2Pos - 1])) {
                align1.add(seq1Bytes[seq1Pos - 1]);
                align2.add(seq2Bytes[seq2Pos - 1]);
                seq1Pos--;
                seq2Pos--;
            } else if (currentCellScore == scores[seq1Pos - 1][seq2Pos] + gapExtendPenalty) {
                align1.add(seq1Bytes[seq1Pos - 1]);
                align2.add(gap2);
                seq1Pos--;
            } else {
                align1.add(gap1);
                align2.add(seq2Bytes[seq2Pos - 1]);
                seq2Pos--;
            }
        }

        align1.reverse();
        align2.reverse();
        alignment.setScore(scores[seq1TracebackStart][seq2TracebackStart]);
        alignment.add(align1);
        alignment.add(align2);

    }

    /**
     * Prints the score table.
     */
    protected void printScoreTable() {
        StringBuffer buf = new StringBuffer();
        buf.append(" \t \t");
        for (int i = 0; i < seq1.length(); i++) {
            buf.append(seq1.charAt(i));
            buf.append("\t");
        }
        buf.append("\n");
        logger.info(buf.toString());

        for (int i = 0; i < seq2Dim; i++) {
            buf = new StringBuffer();
            if (i > 0) {
                buf.append(seq2.charAt(i - 1));
            } else {
                buf.append(" ");
            }
            buf.append("\t");
            for (int j = 0; j < seq1Dim; j++) {
                buf.append(Integer.toString(scores[j][i]));
                buf.append("\t");
            }
            logger.info(buf.toString());
        }
    }
}
