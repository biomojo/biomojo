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
package org.biomojo.alignment;

import org.biomojo.alphabet.GappableByteAlphabet;

public class NeedlemanWunschAligner<A extends GappableByteAlphabet<A>> extends AbstractByteSeqAligner<A> {

    public NeedlemanWunschAligner(final ByteSubstitutionMatrix matrix, final int gapPenalty) {
        super(matrix, gapPenalty);
    }

    @Override
    protected void initScoreMatrix() {
        for (int i = 1; i < getSeq1Dim(); ++i) {
            scores[i][0] = i * getGapPenalty();
        }

        for (int i = 1; i < getSeq2Dim(); ++i) {
            scores[0][i] = i * getGapPenalty();
        }
    }
}
