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
package org.biomojo.alphabet;

/**
 * Interface for alphabets supporting gap symbols.
 *
 * @author Hugh Eaves
 * @param <T>
 *            the type of values in the alphabet
 * @param <Z>
 *            the type of alphabet returned when a gapped alphabet is requested
 */
public interface Gappable<T, A extends Alphabet<T>, G extends Gapped<T, A>> extends Alphabet<T> {

    /**
     * Supports gaps.
     *
     * @return true, if successful
     */
    public boolean isGapped();

    /**
     * Gets the gapped.
     *
     * @return the gapped
     */
    public G getGapped();

    /**
     * Gap symbol.
     *
     * @return the t
     */
    public T gapSymbol();
}
