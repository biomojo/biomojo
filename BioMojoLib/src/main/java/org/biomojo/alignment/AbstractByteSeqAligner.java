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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractByteSeqAligner<A extends GappableByte<A>> implements Aligner<A, ByteSeq<A>> {
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AbstractByteSeqAligner.class);

    protected int arrayDim1 = 0;
    protected int arrayDim2 = 0;

    /** The seq1 dim. */
    protected int seq1Dim = 0;

    /** The seq2 dim. */
    protected int seq2Dim = 0;

    /** The gap extend penalty. */
    protected final int gapExtendPenalty;

    protected final ByteSubstitutionMatrix matrix;

    /** The seq1. */
    protected ByteSeq<A> seq1;

    /** The seq2. */
    protected ByteSeq<A> seq2;

    protected AbstractByteSeqAligner(final ByteSubstitutionMatrix matrix, final int gapExtendPenalty) {
        this.matrix = matrix;
        this.gapExtendPenalty = gapExtendPenalty;
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

        logger.debug("Sequence lengths: seq1 = {}, seq2 = {}", seq1.size(), seq2.size());

        checkDimensions();

        initTraceback();

        clearScores();
    }

    protected void checkDimensions() {

        seq1Dim = seq1.size() + 1;
        seq2Dim = seq2.size() + 1;

        if (seq1Dim > arrayDim1 || seq2Dim > arrayDim2) {
            arrayDim1 = Math.max(seq1Dim, arrayDim1);
            arrayDim2 = Math.max(seq2Dim, arrayDim2);
            logger.debug("Re-Dimming matrices: arrayDim1 = {}, arrayDim2 = {}", arrayDim1, arrayDim2);
            reallocateMatrices();
        }
    }

    @Override
    public Alignment<ByteSeq<A>> align(final List<ByteSeq<A>> sequences) {
        initialize(sequences);
        final PairwiseAlignment<ByteSeq<A>> alignment = new PairwiseAlignment<>();
        calcCellScores();
        calcTraceback(alignment);
        return alignment;
    }

    /**
     * Calc score.
     *
     * @param alignment
     *            the alignment
     */
    protected void calcCellScores() {
        final byte[] seq1Bytes = seq1.toByteArray();
        final byte[] seq2Bytes = seq2.toByteArray();
        for (int seq1Pos = 1; seq1Pos < seq1Dim; seq1Pos++) {
            // int leftScore = scores[seq1Pos][0];
            for (int seq2Pos = 1; seq2Pos < seq2Dim; seq2Pos++) {
                // scores[seq1Pos][seq2Pos] = leftScore =
                // calcCellScore(seq1Bytes, seq2Bytes, leftScore, seq1Pos,
                // seq2Pos);
                calcCellScore(seq1Bytes, seq2Bytes, seq1Pos, seq2Pos);
            }
        }
    }

    protected abstract void calcTraceback(final PairwiseAlignment<ByteSeq<A>> alignment);

    protected abstract void clearScores();

    protected abstract void reallocateMatrices();

    protected abstract void initTraceback();

    protected abstract int calcCellScore(final byte[] seq1Bytes, final byte[] seq2Bytes, final int seq1Pos,
            final int seq2Pos);
}
