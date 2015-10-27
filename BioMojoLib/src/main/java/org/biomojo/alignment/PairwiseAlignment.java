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

import org.biomojo.alphabet.GappableAlphabet;
import org.biomojo.sequence.SeqArrayList;
import org.biomojo.sequence.Seq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PairwiseAlignment.
 *
 * @author Hugh Eaves
 * @param <T> the generic type
 */
public class PairwiseAlignment<T extends Seq<?, ? extends GappableAlphabet<?, ?>>> extends SeqArrayList<T>
        implements Alignment<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant SCORE. */
    private static final String SCORE = "score";

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(PairwiseAlignment.class.getName());

    /* (non-Javadoc)
     * @see org.biomojo.alignment.Alignment#getScore()
     */
    @Override
    public int getScore() {
        return getProp(SCORE);
    }

    /**
     * Sets the score.
     *
     * @param score the new score
     */
    public void setScore(final int score) {
        setProp(SCORE, score);
    }
}
