/*
 * Copyright (C) 2015  Hugh Eaves
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General  License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General  License for more details.
 *
 * You should have received a copy of the GNU General  License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.biomojo.alphabet;

// TODO: Auto-generated Javadoc
/**
 * The Interface ByteAlphabet.
 */
public interface ByteAlphabet extends Alphabet<Byte> {

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.Alphabet#getCanonical()
     */
    @Override
    public ByteAlphabet getCanonical();

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
     * Check validity.
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
     * Check validity.
     *
     * @param symbols
     *            the symbols
     * @return the invalid symbol info
     */
    default InvalidSymbolInfo checkValidity(final byte[] symbols) {
        return checkValidity(symbols, 0, symbols.length);
    }

    /**
     * Check validity.
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
     * Validate.
     *
     * @param symbol
     *            the symbol
     * @throws InvalidSymbolException
     *             the invalid symbol exception
     */
    default void validate(final byte symbol) throws InvalidSymbolException {
        final InvalidSymbolInfo info = checkValidity(symbol);
        if (info != null) {
            throw new InvalidSymbolException(info);
        }
    }

    /**
     * Validate.
     *
     * @param symbols
     *            the symbols
     * @throws InvalidSymbolException
     *             the invalid symbol exception
     */
    default void validate(final byte[] symbols) throws InvalidSymbolException {
        final InvalidSymbolInfo info = checkValidity(symbols);
        if (info != null) {
            throw new InvalidSymbolException(info);
        }
    }

    /**
     * Validate.
     *
     * @param symbols
     *            the symbols
     * @param start
     *            the start
     * @param end
     *            the end
     * @throws InvalidSymbolException
     *             the invalid symbol exception
     */
    default void validate(final byte[] symbols, final int start, final int end) throws InvalidSymbolException {
        final InvalidSymbolInfo info = checkValidity(symbols, start, end);
        if (info != null) {
            throw new InvalidSymbolException(info);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.Alphabet#getCanonical(java.lang.Object)
     */
    @Override
    default Byte getCanonical(final Byte symbol) {
        return getCanonical(symbol.byteValue());
    }

    /**
     * Gets the canonical.
     *
     * @param symbol
     *            the symbol
     * @return the canonical
     */
    byte getCanonical(byte symbol);

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

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.Alphabet#getSymbolType()
     */
    @Override
    default Class<Byte> getSymbolType() {
        return Byte.class;
    }

    /**
     * Checks if is canonical.
     *
     * @param symbol
     *            the symbol
     * @return true, if is canonical
     */
    boolean isCanonical(byte symbol);

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.Alphabet#isCanonical(java.lang.Object)
     */
    @Override
    default boolean isCanonical(final Byte symbol) {
        return isCanonical(symbol.byteValue());
    }

}
