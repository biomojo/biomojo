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

import java.util.Arrays;

import org.biomojo.alphabet.ByteAlphabet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class AbstractByteSubstitutionMatrix implements ByteSubstitutionMatrix {

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractByteSubstitutionMatrix.class.getName());

	ByteAlphabet alphabet;
	protected int scores[][] = new int[Byte.MAX_VALUE + 1][Byte.MAX_VALUE + 1];

	protected AbstractByteSubstitutionMatrix(ByteAlphabet alphabet) {
		this.alphabet = alphabet;
	}

	protected void initMatrix(int score) {
		for (int[] scoresRow : scores) {
			Arrays.fill(scoresRow, score);
		}
	}

	/**
	 * Sets a score in the matrix for both upper and lowercase versions of the
	 * character.
	 *
	 * @param fromChar
	 * @param toChar
	 * @param score
	 */
	protected void setScore(char fromChar, char toChar, int score) {
		logger.debug("setScore: fromChar = {}, toChar = {}, score = {}",
				fromChar, toChar, score);
		scores[Character.toLowerCase(fromChar)][Character.toLowerCase(toChar)] = score;
		scores[Character.toLowerCase(fromChar)][Character.toUpperCase(toChar)] = score;
		scores[Character.toUpperCase(fromChar)][Character.toLowerCase(toChar)] = score;
		scores[Character.toUpperCase(fromChar)][Character.toUpperCase(toChar)] = score;
	}

	/**
	 * @see org.biomojo.alignment.matrix.ByteSubstitutionMatrix#getAlphabet()
	 */
	@Override
	public ByteAlphabet getAlphabet() {
		return alphabet;
	}

	@Override
	public int getScore(byte from, byte to) {
		return scores[from][to];
	}

	/**
	 * @see org.biomojo.alignment.SubstitutionMatrix#getScore(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public int getScore(Byte from, Byte to) {
		return scores[from][to];
	}
}
