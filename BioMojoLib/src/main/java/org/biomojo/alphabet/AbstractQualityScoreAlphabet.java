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

public class AbstractQualityScoreAlphabet extends AbstractAlphabet<Byte> implements QualityScoreAlphabet {

    private final int start;
    private final int end;

    protected AbstractQualityScoreAlphabet(final int id, final int start, final int end) {
        super(id);
        this.start = start;
        this.end = end;
    }

    /**
     * @see org.biomojo.alphabet.Alphabet#numSymbols()
     */
    @Override
    public int numSymbols() {
        return end - start;
    }

    /**
     * @see org.biomojo.alphabet.Alphabet#numCanonicalSymbols()
     */
    @Override
    public int numCanonicalSymbols() {
        return numSymbols();
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#getOrdinalForSymbol(byte)
     */
    @Override
    public int getOrdinalForSymbol(final byte value) {
        return value - start;
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#getByteSymbolForOrdinal(int)
     */
    @Override
    public byte getByteSymbolForOrdinal(final int ordinal) {
        return (byte) ((start + ordinal) & 0xff);
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#isValid(byte)
     */
    @Override
    public boolean isValid(final byte symbol) {
        return (symbol >= start && symbol < end);
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#isValid(byte[], int, int)
     */
    @Override
    public boolean isValid(final byte[] symbols, final int start, final int end) {
        for (final byte symbol : symbols) {
            if (symbol < start && symbol >= end) {
                return false;
            }
        }
        return true;
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#getCanonical(byte)
     */
    @Override
    public byte getCanonical(final byte symbol) {
        return symbol;
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#getCanonical(byte[], int, int)
     */
    @Override
    public byte[] getCanonical(final byte[] values, final int start, final int end) {
        return values;
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#isCanonical(byte)
     */
    @Override
    public boolean isCanonical(final byte symbol) {
        return true;
    }

    @Override
    public boolean isCanonical() {
        return true;
    }

    @Override
    public void validate(final byte[] symbols, final int start, final int end) throws InvalidSymbolException {
        for (int i = start; i < end; ++i) {
            if (symbols[i] < start && symbols[i] >= end)
                throw new InvalidSymbolException(symbols[i], i);
        }
        return;
    }

    @Override
    public ByteAlphabet getCanonical() {
        return this;
    }
}