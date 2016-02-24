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
 * Represents an alphabet, where the underlying type is a java "byte" primitive.
 * This interface avoids the boxing/unboxing that would be required with Byte
 * objects.
 * 
 * @author Hugh Eaves
 */
public interface ByteAlphabet extends Alphabet<Byte> {

    /**
     * Gets the ordinal for symbol.
     *
     * @param value
     *            the value
     * @return the ordinal for symbol
     * @see org.biomojo.alphabet.Alphabet#getOrdinalForSymbol(java.lang.Object)
     */
    @Override
    default int getOrdinalForSymbol(final Byte value) {
        return getOrdinalForSymbol(value.byteValue());
    }

    /**
     * Gets the symbol for ordinal.
     *
     * @param ordinal
     *            the ordinal
     * @return the symbol for ordinal
     * @see org.biomojo.alphabet.Alphabet#getSymbolForOrdinal(int)
     */
    @Override
    default Byte getSymbolForOrdinal(final int ordinal) {
        return getByteSymbolForOrdinal(ordinal);
    }

    /**
     * Checks if is valid.
     *
     * @param symbol
     *            the symbol
     * @return true, if is valid
     * @see org.biomojo.alphabet.Alphabet#isValid(java.lang.Object)
     */
    @Override
    default boolean isValid(final Byte symbol) {
        return isValid(symbol.byteValue());
    }

    /**
     * Get the order of this symbol in the alphabet.
     *
     * @param value
     *            the value
     * @return the ordinal for symbol
     */
    int getOrdinalForSymbol(byte value);

    /**
     * Get the symbol for a given ordinal.
     *
     * @param ordinal
     *            the ordinal
     * @return the symbol for ordinal
     */
    byte getByteSymbolForOrdinal(int ordinal);

    /**
     * Determine if a symbol is a member of this alphabet.
     *
     * @param symbol
     *            symbol to check for validity
     * @return true if the symbol value is a member of this alphabet.
     */
    boolean isValid(byte symbol);

    /**
     * Determine if the symbol in the given array are all members of this
     * alphabet.
     *
     * @param symbols
     *            symbols to check for validity
     * @return true all the symbols is a member of this alphabet.
     */
    default boolean isValid(final byte[] symbols) {
        return isValid(symbols, 0, symbols.length);
    }

    /**
     * Determine if the symbol in the specified portion of the given array are
     * all members of this alphabet.
     *
     * @param symbols
     *            the symbols
     * @param start
     *            the start
     * @param end
     *            the end
     * @return true all the symbols is a member of this alphabet.
     */
    default boolean isValid(final byte[] symbols, final int start, final int end) {
        for (int i = start; i < end; ++i) {
            if (!isValid(symbols[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given symbol is a members of this alphabet. Returns an
     * InvalidSymbolInfo structure if the symbol is not a member of this
     * alphabet, or null, if all symbols are members of this alphabet.
     *
     * @param symbol
     *            the symbol
     * @return the invalid symbol info
     */
    default InvalidSymbolInfo checkValidity(final byte symbol) {
        if (!isValid(symbol)) {
            return new InvalidSymbolInfo(symbol);
        }
        return null;
    }

    /**
     * Checks to the symbols in the given array are members of this alphabet.
     * Returns an InvalidSymbolInfo structure for the first symbol that is not a
     * member of this alphabet, or null, if all symbols are members of this
     * alphabet.
     *
     * @param symbols
     *            the symbols
     * @return the invalid symbol info
     */
    default InvalidSymbolInfo checkValidity(final byte[] symbols) {
        return checkValidity(symbols, 0, symbols.length);
    }

    /**
     * Checks to the symbols in the given array (between start and end) are
     * members of this alphabet. Returns an InvalidSymbolInfo structure for the
     * first symbol that is not a member of this alphabet, or null, if all
     * symbols are members of this alphabet.
     *
     * @param symbols
     *            the symbols
     * @param start
     *            the start
     * @param end
     *            the end
     * @return the invalid symbol info
     */
    default InvalidSymbolInfo checkValidity(final byte[] symbols, final int start, final int end) {
        for (int i = start; i < end; ++i) {
            if (!isValid(symbols[i])) {
                return new InvalidSymbolInfo(symbols[i], i);
            }
        }
        return null;
    }

    /**
     * Validates the given symbol against this alphabet.
     *
     * @param symbol
     *            the symbol
     * @throws InvalidSymbolException
     *             thrown if the symbol is not a member of this alphabet
     */
    default void validate(final byte symbol) throws InvalidSymbolException {
        final InvalidSymbolInfo info = checkValidity(symbol);
        if (info != null) {
            throw new InvalidSymbolException(info);
        }
    }

    /**
     * Validates the given symbols. Throws an InvalidSymbolException if any of
     * the symbols in the array are invalid.
     *
     * @param symbols
     *            the symbols
     * @throws InvalidSymbolException
     *             thrown if the symbol is not a member of this alphabet
     */
    default void validate(final byte[] symbols) throws InvalidSymbolException {
        final InvalidSymbolInfo info = checkValidity(symbols);
        if (info != null) {
            throw new InvalidSymbolException(info);
        }
    }

    /**
     * Validates a portion of the given array of symbols. Throws an
     * InvalidSymbolException if any of the symbols in the specified portion
     * array are invalid.
     *
     * @param symbols
     *            the symbols
     * @param start
     *            the start
     * @param end
     *            the end
     * @throws InvalidSymbolException
     *             thrown if the symbol is not a member of this alphabet
     */
    default void validate(final byte[] symbols, final int start, final int end) throws InvalidSymbolException {
        final InvalidSymbolInfo info = checkValidity(symbols, start, end);
        if (info != null) {
            throw new InvalidSymbolException(info);
        }
    }

    /**
     * @see org.biomojo.alphabet.Alphabet#getSymbolType()
     */
    @Override
    default Class<Byte> getSymbolType() {
        return Byte.class;
    }
}
