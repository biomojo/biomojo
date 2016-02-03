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

public class ASCII extends AbstractByteAlphabet {

    /**
     * Create a new org.java0.string.ASCIIAlphabet.
     *
     * @param id
     *            the id
     */
    protected ASCII() {
        super(AlphabetId.ASCII);

    }

    /**
     * Num symbols.
     *
     * @return the int
     * @see org.biomojo.alphabet.Alphabet#numSymbols()
     */
    @Override
    public int numSymbols() {
        return org.java0.string.ASCII.NUM_CHARACTERS;
    }

    /**
     * Gets the ordinal for symbol.
     *
     * @param value
     *            the value
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
     * @param ordinal
     *            the ordinal
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
     * @param symbol
     *            the symbol
     * @return true, if is valid
     * @see org.biomojo.alphabet.ByteAlphabet#isValid(byte)
     */
    @Override
    public boolean isValid(final byte symbol) {
        return (symbol >= 0);
    }
}
