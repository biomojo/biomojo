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

import org.biomojo.alphabet.GappableByteAlphabet;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.ByteSeqImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractByteSeqAligner.
 *
 * @param <A>
 *            the generic type
 */
public abstract class AbstractByteSeqAligner<A extends GappableByteAlphabet> implements Aligner<ByteSeq<A>> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AbstractByteSeqAligner.class.getName());

    /** The matrix. */
    private final ByteSubstitutionMatrix matrix;

    /** The gap penalty. */
    private final int gapPenalty;

    /** The seq1 dim. */
    private int seq1Dim = 0;

    /** The seq2 dim. */
    private int seq2Dim = 0;

    /** The seq1 traceback start. */
    protected int seq1TracebackStart = 0;

    /** The seq2 traceback start. */
    protected int seq2TracebackStart = 0;

    /** The scores. */
    protected int[][] scores = new int[1][0];

    /** The seq1. */
    private ByteSeq<A> seq1;

    /** The seq2. */
    private ByteSeq<A> seq2;

    /** The seq1 bytes. */
    private byte[] seq1Bytes;

    /** The seq2 bytes. */
    private byte[] seq2Bytes;

    /**
     * Instantiates a new abstract byte seq aligner.
     *
     * @param matrix
     *            the matrix
     * @param gapPenalty
     *            the gap penalty
     */
    public AbstractByteSeqAligner(final ByteSubstitutionMatrix matrix, final int gapPenalty) {
        this.matrix = matrix;
        this.gapPenalty = gapPenalty;
    }

    /**
     * Initialize.
     *
     * @param sequences
     *            the sequences
     */
    protected void initialize(final List<ByteSeq<A>> sequences) {
        seq1 = sequences.get(0);
        seq2 = sequences.get(1);
        seq1Bytes = seq1.getAllBytes();
        seq2Bytes = seq2.getAllBytes();

        logger.debug("Sequence lengths: seq1 = {}, seq2 = {}", seq1.size(), seq2.size());

        seq1Dim = seq1.size() + 1;
        seq2Dim = seq2.size() + 1;

        seq1TracebackStart = seq1Dim - 1;
        seq2TracebackStart = seq2Dim - 1;

        allocateScoreMatrix();
        initScoreMatrix();
    }

    /**
     * Allocate score matrix.
     */
    private void allocateScoreMatrix() {
        int seq1Length = scores.length;
        int seq2Length = scores[0].length;
        if (seq1Dim > seq1Length || seq2Dim > seq2Length) {
            if (seq1Dim > seq1Length) {
                seq1Length = seq1Dim;
            }
            if (seq2Dim > seq2Length) {
                seq2Length = seq2Dim;
            }
            logger.debug("Re-Dimming matrices: seq1Dim = {}, seq2Dim = {}", seq1Length, seq2Length);
            scores = new int[seq1Length][seq2Length];
        }
    }

    /**
     * Inits the score matrix.
     */
    protected abstract void initScoreMatrix();

    /**
     * Calc score.
     *
     * @param alignment
     *            the alignment
     */
    protected void calcScore(final PairwiseAlignment<ByteSeq<A>> alignment) {
        for (int seq1Pos = 1; seq1Pos < seq1Dim; seq1Pos++) {
            int leftScore = scores[seq1Pos][0];
            for (int seq2Pos = 1; seq2Pos < seq2Dim; seq2Pos++) {
                scores[seq1Pos][seq2Pos] = leftScore = calcCellScore(leftScore, seq1Pos, seq2Pos);
            }
        }

        alignment.setScore(scores[seq1TracebackStart][seq2TracebackStart]);
    }

    /**
     * Calc cell score.
     *
     * @param leftScore
     *            the left score
     * @param seq1Pos
     *            the seq1 pos
     * @param seq2Pos
     *            the seq2 pos
     * @return the int
     */
    protected final int calcCellScore(final int leftScore, final int seq1Pos, final int seq2Pos) {
        int cellScore = scores[seq1Pos - 1][seq2Pos - 1]
                + matrix.getScore(seq1Bytes[seq1Pos - 1], seq2Bytes[seq2Pos - 1]);
        final int seq1GapScore = scores[seq1Pos - 1][seq2Pos] + gapPenalty;
        final int seq2GapScore = leftScore + gapPenalty;

        if (seq1GapScore > cellScore) {
            cellScore = seq1GapScore;
        }
        if (seq2GapScore > cellScore) {
            cellScore = seq2GapScore;
        }

        return cellScore;
    }

    /**
     * Calc traceback.
     *
     * @param alignment
     *            the alignment
     */
    protected void calcTraceback(final PairwiseAlignment<ByteSeq<A>> alignment) {

        final A alphabet1 = (A) seq1.getAlphabet().getGapped();
        final A alphabet2 = (A) seq2.getAlphabet().getGapped();

        final ByteSeqImpl<A> align1 = new ByteSeqImpl<A>(seq1Dim + seq2Dim, alphabet1);
        final ByteSeqImpl<A> align2 = new ByteSeqImpl<A>(seq1Dim + seq2Dim, alphabet2);

        final byte gap1 = alphabet1.gapSymbol();
        final byte gap2 = alphabet2.gapSymbol();

        int seq1Pos = seq1TracebackStart;
        int seq2Pos = seq2TracebackStart;

        while (seq1Pos > 0 && seq2Pos > 0) {
            final int currentCellScore = scores[seq1Pos][seq2Pos];
            if (currentCellScore == scores[seq1Pos - 1][seq2Pos - 1]
                    + matrix.getScore(seq1Bytes[seq1Pos - 1], seq2Bytes[seq2Pos - 1])) {
                align1.append(seq1Bytes[seq1Pos - 1]);
                align2.append(seq2Bytes[seq2Pos - 1]);
                seq1Pos--;
                seq2Pos--;
            } else if (currentCellScore == scores[seq1Pos - 1][seq2Pos] + gapPenalty) {
                align1.append(seq1Bytes[seq1Pos - 1]);
                align2.append(gap2);
                seq1Pos--;
            } else {
                align1.append(gap1);
                align2.append(seq2Bytes[seq2Pos - 1]);
                seq2Pos--;
            }
        }

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

    /**
     * Align.
     *
     * @param sequences
     *            the sequences
     * @return the alignment
     * @see org.biomojo.alignment.Aligner#align(org.biomojo.sequence.SequenceList)
     */
    @Override
    public Alignment<ByteSeq<A>> align(final List<ByteSeq<A>> sequences) {
        initialize(sequences);
        final PairwiseAlignment<ByteSeq<A>> alignment = new PairwiseAlignment<>();
        calcScore(alignment);
        calcTraceback(alignment);
        return alignment;
    }

    /**
     * Gets the seq1 dim.
     *
     * @return the seq1Dim
     */
    public int getSeq1Dim() {
        return seq1Dim;
    }

    /**
     * Gets the seq2 dim.
     *
     * @return the seq2Dim
     */
    public int getSeq2Dim() {
        return seq2Dim;
    }

    /**
     * Gets the gap penalty.
     *
     * @return the gapPenalty
     */
    public final int getGapPenalty() {
        return gapPenalty;
    }

}
