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
package org.biomojo.alphabet;

import org.java0.string.ASCII;

/**
 * @author Hugh Eaves
 *
 */
public class CanonicalASCIIAlphabet extends AbstractAlphabet<Byte> implements ByteAlphabet {
    /**
     * Create a new ASCIIAlphabet.
     *
     * @param id
     */
    protected CanonicalASCIIAlphabet(final int id) {
        super(id);

    }

    /**
     * @see org.biomojo.alphabet.Alphabet#numSymbols()
     */
    @Override
    public int numSymbols() {
        return ASCII.NUM_CHARACTERS;
    }

    /**
     * @see org.biomojo.alphabet.Alphabet#numCanonicalSymbols()
     */
    @Override
    public int numCanonicalSymbols() {
        return ASCII.NUM_CHARACTERS - ASCII.NUM_LETTERS;
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#getOrdinalForSymbol(byte)
     */
    @Override
    public int getOrdinalForSymbol(final byte value) {
        return value & 0xff;
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#getByteSymbolForOrdinal(int)
     */
    @Override
    public byte getByteSymbolForOrdinal(final int ordinal) {
        return (byte) (ordinal & 0xff);
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#isValid(byte)
     */
    @Override
    public boolean isValid(final byte symbol) {
        return (symbol >= 0);
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#getCanonical(byte)
     */
    @Override
    public byte getCanonical(final byte symbol) {
        if (symbol >= ASCII.LOWER_CASE_START && symbol <= ASCII.LOWER_CASE_END) {
            return (byte) (symbol - ASCII.LOWER_CASE_START + ASCII.UPPER_CASE_START);
        }
        return symbol;
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#isCanonical(byte)
     */
    @Override
    public boolean isCanonical(final byte symbol) {
        if (symbol >= ASCII.LOWER_CASE_START && symbol <= ASCII.LOWER_CASE_END) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isCanonical() {
        return false;
    }

    @Override
    public CanonicalASCIIAlphabet getCanonical() {
        return this;
    }

}
