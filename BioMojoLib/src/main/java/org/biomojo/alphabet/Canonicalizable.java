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

import java.util.ArrayList;
import java.util.List;

import org.biomojo.BioMojo;

public interface Canonicalizable<T, A extends Canonicalizable<T, ?>> extends Alphabet<T> {

    /**
     * Get the number of canonical symbols in this alphabet. Note that some
     * Alphabets may have no canonical symbols.
     *
     * @return the int
     */
    public int numCanonicalSymbols();

    /**
     * Checks if this Alphabet is the canonical variant.
     *
     * @return true, if is canonical
     */
    public boolean isCanonical();

    /**
     * Returns true if getCanonical(a) == getCanonical(b).
     *
     * @param a
     *            the a
     * @param b
     *            the b
     * @return true, if is equivalent
     */
    public default boolean isEquivalent(final T a, final T b) {
        return (getCanonical(a) == getCanonical(b));
    }

    /**
     * Return the canonical representation of the given symbol.
     *
     * @param symbol
     *            the symbol
     * @return the canonical representation
     */
    public T getCanonical(T symbol);

    /**
     * Returns a new list containing the canonical representation of all the
     * symbols in the given list.
     *
     * @param symbols
     *            the symbols
     * @return the canonical
     */
    public default List<T> getCanonical(final List<T> symbols) {
        final ArrayList<T> newList = new ArrayList<T>(symbols.size());
        for (final T symbol : symbols) {
            newList.add(getCanonical(symbol));
        }
        return newList;
    }

    /**
     * Make canonical.
     *
     * @param symbols
     *            the symbols
     */
    public default void makeCanonical(final T[] symbols) {
        makeCanonical(symbols, 0, symbols.length);
    }

    /**
     * Converts all the symbols in the list to their canonical represntation.
     *
     * @param symbols
     *            the symbols
     * @param start
     *            the start
     * @param end
     *            the end
     */
    public default void makeCanonical(final T[] symbols, final int start, final int end) {
        for (int i = start; i < end; ++i) {
            symbols[i] = getCanonical(symbols[i]);
        }
    }

    /**
     * Converts all the symbols in the given list to their canonical
     * representation.
     *
     * @param symbols
     *            the symbols
     */
    public default void makeCanonical(final List<T> symbols) {
        for (int i = 0; i < symbols.size(); ++i) {
            symbols.set(i, getCanonical(symbols.get(i)));
        }
    }

    /**
     * Returns the canonical version of this Alphabet.
     * 
     * @return
     */
    public default A getCanonical() {
        return (A) BioMojo.getObject(Canonicalizable.class, this.getId() & ~AlphabetVariant.WITH_NON_CANONICAL);
    }
}
