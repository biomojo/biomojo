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

import org.biomojo.core.IntegerIdentified;

// TODO: Auto-generated Javadoc
/**
 * An {@code Alphabet} represents a specific subset of all the possible values
 * of a particular Java type.
 *
 * @param <T>
 *            the type of values in the alphabet
 */
public interface Alphabet<T> extends IntegerIdentified {

    /**
     * Gets the symbol type.
     *
     * @return the symbol type
     */
    public Class<T> getSymbolType();

    /**
     * Checks if is canonical.
     *
     * @return true, if is canonical
     */
    public boolean isCanonical();

    /**
     * Gets the canonical.
     *
     * @return the canonical
     */
    public Alphabet<T> getCanonical();

    /**
     * Get the number of symbols in this alphabet.
     *
     * @return the int
     */
    public int numSymbols();

    /**
     * Get the number of canonical symbols in this alphabet.
     *
     * @return the int
     */
    public int numCanonicalSymbols();

    /**
     * Get the order of this symbol in the alphabet.
     *
     * @param value            the value
     * @return the ordinal for symbol
     */
    public int getOrdinalForSymbol(T value);

    /**
     * Get the symbol for a given ordinal.
     *
     * @param ordinal            the ordinal
     * @return the symbol for ordinal
     */
    public T getSymbolForOrdinal(int ordinal);

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
     * Determine if a symbol is a member of this alphabet.
     *
     * @param symbol
     *            symbol to check for validity
     * @return true if the symbol value is a member of this alphabet.
     */
    public boolean isValid(T symbol);

    /**
     * Determine if a symbol is canonical.
     *
     * @param symbol
     *            symbol to check
     * @return true if the symbol is canonical.
     */
    public boolean isCanonical(T symbol);

    /**
     * Determine if the symbol in the given array are all members of this
     * alphabet.
     *
     * @param symbols
     *            symbols to check for validity
     * @return true all the symbols is a member of this alphabet.
     */
    public default boolean isValid(final T[] symbols) {
        return (isValid(symbols, 0, symbols.length));
    }

    /**
     * Determine if the symbol in the specified portion of the given array are
     * all members of this alphabet.
     *
     * @param symbols the symbols
     * @param start            the start
     * @param end            the end
     * @return true all the symbols is a member of this alphabet.
     */
    public default boolean isValid(final T[] symbols, final int start, final int end) {
        for (int i = start; i < end; ++i) {
            if (!isValid(symbols[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check validity.
     *
     * @param symbol the symbol
     * @return the invalid symbol info
     */
    public default InvalidSymbolInfo checkValidity(final T symbol) {
        if (!isValid(symbol)) {
            return new InvalidSymbolInfo(symbol);
        }
        return null;
    }

    /**
     * Check validity.
     *
     * @param symbols the symbols
     * @return the invalid symbol info
     */
    public default InvalidSymbolInfo checkValidity(final T[] symbols) {
        return checkValidity(symbols, 0, symbols.length);
    }

    /**
     * Check validity.
     *
     * @param symbols the symbols
     * @param start the start
     * @param end the end
     * @return the invalid symbol info
     */
    public default InvalidSymbolInfo checkValidity(final T[] symbols, final int start, final int end) {
        for (int i = start; i < end; ++i) {
            if (!isValid(symbols[i])) {
                return new InvalidSymbolInfo(symbols[i], i);
            }
        }
        return null;
    }

    /**
     * Validate.
     *
     * @param symbol the symbol
     * @throws InvalidSymbolException the invalid symbol exception
     */
    public default void validate(final T symbol) throws InvalidSymbolException {
        final InvalidSymbolInfo info = checkValidity(symbol);
        if (info != null) {
            throw new InvalidSymbolException(info);
        }
    }

    /**
     * Validate.
     *
     * @param symbols the symbols
     * @throws InvalidSymbolException the invalid symbol exception
     */
    public default void validate(final T[] symbols) throws InvalidSymbolException {
        final InvalidSymbolInfo info = checkValidity(symbols);
        if (info != null) {
            throw new InvalidSymbolException(info);
        }
    }

    /**
     * Validate.
     *
     * @param symbols the symbols
     * @param start the start
     * @param end the end
     * @throws InvalidSymbolException the invalid symbol exception
     */
    public default void validate(final T[] symbols, final int start, final int end) throws InvalidSymbolException {
        final InvalidSymbolInfo info = checkValidity(symbols, start, end);
        if (info != null) {
            throw new InvalidSymbolException(info);
        }
    }

    /**
     * Determine if all symbols in the list are members of this alphabet.
     *
     * @param symbols the symbols
     * @return true if all symbols in the list are members of this alphabet.
     */
    public default boolean isValid(final List<T> symbols) {
        for (final T symbol : symbols) {
            if (!isValid(symbol)) {
                return false;
            }
        }
        return true;
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
     * Make canonical.
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

}
