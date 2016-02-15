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

public interface CanonicalizableByte<A extends CanonicalizableByte<?>> extends Canonicalizable<Byte, A>, ByteAlphabet {

    /**
     * Checks if is canonical.
     *
     * @param symbol
     *            the symbol
     * @return true, if is canonical
     */
    boolean isCanonical(byte symbol);

    /**
     * Returns a new array containing the canonical representation of all the
     * symbols in the given array.
     *
     * @param symbols
     *            the symbols
     * @return the canonical
     */
    default byte[] getCanonical(final byte[] symbols) {
        return getCanonical(symbols, 0, symbols.length);
    }

    /**
     * Returns a new array containing the canonical representation of all the
     * symbols in specified portion of the given array.
     *
     * @param values
     *            the values
     * @param start
     *            the start
     * @param end
     *            the end
     * @return the canonical
     */
    default byte[] getCanonical(final byte[] values, final int start, final int end) {
        final byte newValues[] = new byte[end - start];
        int pos = 0;
        for (int i = start; i < end; ++i) {
            newValues[pos++] = getCanonical(values[i]);
        }
        return newValues;
    }

    /**
     * Make canonical.
     *
     * @param symbols
     *            the symbols
     */
    default void makeCanonical(final byte[] symbols) {
        makeCanonical(symbols, 0, symbols.length);
    }

    /**
     * Make canonical.
     *
     * @param symbols
     *            the symbols
     * @param start
     *            the start
     * @param end
     *            the end
     */
    default void makeCanonical(final byte[] symbols, final int start, final int end) {
        for (int i = start; i < end; ++i) {
            symbols[i] = getCanonical(symbols[i]);
        }
    }

    /**
     * Returns true if getCanonical(a) == getCanonical(b).
     *
     * @param a
     *            the a
     * @param b
     *            the b
     * @return true, if is equivalent
     */
    default boolean isEquivalent(final byte a, final byte b) {
        return (getCanonical(a) == getCanonical(b));
    }

    byte getCanonical(byte symbol);

}
