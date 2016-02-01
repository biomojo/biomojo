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

import org.biomojo.alphabet.ByteAlphabet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class MatchMismatchByteSubstitutionMatrix.
 *
 * @author Hugh Eaves
 */
public class MatchMismatchByteSubstitutionMatrix extends AbstractByteSubstitutionMatrix {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(MatchMismatchByteSubstitutionMatrix.class.getName());

    final private int matchScore;
    final private int mismatchScore;

    /**
     * Instantiates a new match mismatch byte substitution matrix.
     *
     * @param alphabet
     *            the alphabet
     * @param matchScore
     *            the match score
     * @param mismatchScore
     *            the mismatch score
     */
    public MatchMismatchByteSubstitutionMatrix(final ByteAlphabet alphabet, final int matchScore,
            final int mismatchScore) {
        super(alphabet);
        this.matchScore = matchScore;
        this.mismatchScore = mismatchScore;
    }

    @Override
    public int getScore(final byte from, final byte to) {
        if (from == to) {
            return matchScore;
        } else {
            return mismatchScore;
        }
    }

}
