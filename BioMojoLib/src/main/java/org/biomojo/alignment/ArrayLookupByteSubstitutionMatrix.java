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

import java.util.Arrays;

import org.biomojo.alphabet.ByteAlphabet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractByteSubstitutionMatrix.
 *
 * @author Hugh Eaves
 */
public class ArrayLookupByteSubstitutionMatrix extends AbstractByteSubstitutionMatrix
        implements ByteSubstitutionMatrix {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(ArrayLookupByteSubstitutionMatrix.class.getName());

    /** The scores. */
    protected int scores[][] = new int[Byte.MAX_VALUE + 1][Byte.MAX_VALUE + 1];

    protected ArrayLookupByteSubstitutionMatrix(final ByteAlphabet alphabet) {
        super(alphabet);
    }

    /**
     * Inits the matrix.
     *
     * @param score
     *            the score
     */
    protected void initMatrix(final int score) {
        for (final int[] scoresRow : scores) {
            Arrays.fill(scoresRow, score);
        }
    }

    /**
     * Sets a score in the matrix for both upper and lowercase versions of the
     * character.
     *
     * @param fromChar
     *            the from char
     * @param toChar
     *            the to char
     * @param score
     *            the score
     */
    protected void setScore(final char fromChar, final char toChar, final int score) {
        logger.debug("setScore: fromChar = {}, toChar = {}, score = {}", fromChar, toChar, score);
        scores[Character.toLowerCase(fromChar)][Character.toLowerCase(toChar)] = score;
        scores[Character.toLowerCase(fromChar)][Character.toUpperCase(toChar)] = score;
        scores[Character.toUpperCase(fromChar)][Character.toLowerCase(toChar)] = score;
        scores[Character.toUpperCase(fromChar)][Character.toUpperCase(toChar)] = score;
    }

    @Override
    public int getScore(final byte from, final byte to) {
        return scores[from][to];
    }

}
