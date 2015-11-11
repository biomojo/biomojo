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

// TODO: Auto-generated Javadoc
/**
 * The Class AllByteAlphabet.
 */
public class AllByteAlphabet extends AbstractAlphabet<Byte> implements ByteAlphabet {
    
    /** The Constant INSTANCE. */
    public static final AllByteAlphabet INSTANCE = new AllByteAlphabet();

    /** The Constant NUM_SYMBOLS. */
    private static final int NUM_SYMBOLS = 256;

    /**
     * Instantiates a new all byte alphabet.
     */
    private AllByteAlphabet() {
        super(AlphabetId.ALL_BYTE);
    }

    /**
     * Num symbols.
     *
     * @return the int
     * @see org.biomojo.alphabet.Alphabet#numSymbols()
     */
    @Override
    public int numSymbols() {
        return NUM_SYMBOLS;
    }

    /**
     * Num canonical symbols.
     *
     * @return the int
     * @see org.biomojo.alphabet.Alphabet#numCanonicalSymbols()
     */
    @Override
    public int numCanonicalSymbols() {
        return NUM_SYMBOLS;
    }

    /**
     * Gets the ordinal for symbol.
     *
     * @param value the value
     * @return the ordinal for symbol
     * @see org.biomojo.alphabet.ByteAlphabet#getOrdinalForSymbol(byte)
     */
    @Override
    public int getOrdinalForSymbol(final byte value) {
        return value & 0xff;
    }

    /**
     * Gets the byte symbol for ordinal.
     *
     * @param ordinal the ordinal
     * @return the byte symbol for ordinal
     * @see org.biomojo.alphabet.ByteAlphabet#getByteSymbolForOrdinal(int)
     */
    @Override
    public byte getByteSymbolForOrdinal(final int ordinal) {
        return (byte) (ordinal & 0xff);
    }

    /**
     * Checks if is valid.
     *
     * @param symbol the symbol
     * @return true, if is valid
     * @see org.biomojo.alphabet.ByteAlphabet#isValid(byte)
     */
    @Override
    public boolean isValid(final byte symbol) {
        return true;
    }

    /**
     * Checks if is valid.
     *
     * @param symbols the symbols
     * @param start the start
     * @param end the end
     * @return true, if is valid
     * @see org.biomojo.alphabet.ByteAlphabet#isValid(byte[], int, int)
     */
    @Override
    public boolean isValid(final byte[] symbols, final int start, final int end) {
        return true;
    }

    /**
     * Gets the canonical.
     *
     * @param symbol the symbol
     * @return the canonical
     * @see org.biomojo.alphabet.ByteAlphabet#getCanonical(byte)
     */
    @Override
    public byte getCanonical(final byte symbol) {
        return symbol;
    }

    /**
     * Gets the canonical.
     *
     * @param values the values
     * @param start the start
     * @param end the end
     * @return the canonical
     * @see org.biomojo.alphabet.ByteAlphabet#getCanonical(byte[], int, int)
     */
    @Override
    public byte[] getCanonical(final byte[] values, final int start, final int end) {
        return values;
    }

    /**
     * Checks if is canonical.
     *
     * @param symbol the symbol
     * @return true, if is canonical
     * @see org.biomojo.alphabet.ByteAlphabet#isCanonical(byte)
     */
    @Override
    public boolean isCanonical(final byte symbol) {
        return true;
    }

    /* (non-Javadoc)
     * @see org.biomojo.alphabet.Alphabet#isCanonical()
     */
    @Override
    public boolean isCanonical() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.biomojo.alphabet.Alphabet#getCanonical()
     */
    @Override
    public ByteAlphabet getCanonical() {
        return this;
    }

}