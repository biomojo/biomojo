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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public abstract class AbstractByteAlphabet extends AbstractAlphabet<Byte> implements ByteAlphabet {
    private static final Logger logger = LoggerFactory.getLogger(AbstractByteAlphabet.class.getName());

    protected final boolean valid[] = new boolean[Byte.MAX_VALUE + 1];
    protected final byte ordinalToSymbol[] = new byte[Byte.MAX_VALUE + 1];
    protected final byte symbolToOrdinal[] = new byte[Byte.MAX_VALUE + 1];
    protected final byte canonicalSymbols[] = new byte[Byte.MAX_VALUE + 1];
    protected int numSymbols = 0;
    protected int numCanonicalSymbols = 0;
    protected boolean canonical;

    /**
     * Create a new AbstractByteTableAlphabet.
     *
     * @param id
     */
    protected AbstractByteAlphabet(final int id) {
        super(id);

    }

    @Override
    public boolean isValid(final byte symbol) {
        return valid[symbol];
    }

    @Override
    public int getOrdinalForSymbol(final byte symbol) {
        return (symbolToOrdinal[symbol]);
    }

    protected void addSymbol(final byte symbol) {
        if (Character.isLetter(symbol)) {
            addSymbol((byte) Character.toUpperCase(symbol), (byte) Character.toUpperCase(symbol));
            if (!isCanonical()) {
                addSymbol((byte) Character.toLowerCase(symbol), (byte) Character.toUpperCase(symbol));
            }
        } else {
            addSymbol(symbol, symbol);
        }

    }

    protected void addSymbols(final byte[] symbols) {
        for (final byte symbol : symbols) {
            addSymbol(symbol);
        }
    }

    protected void addSymbol(final byte symbol, final byte canonicalSymbol) {
        logger.debug("addSymbol: symbol = {}, canonicalSymbol = {}", (char) symbol, (char) canonicalSymbol);

        valid[symbol] = true;
        canonicalSymbols[symbol] = canonicalSymbol;
    }

    @Override
    public int numSymbols() {
        return numSymbols;
    }

    /**
     * @see org.biomojo.sequence.alphabet.ByteAlphabet#getByteSymbolForOrdinal(int)
     */
    @Override
    public byte getByteSymbolForOrdinal(final int ordinal) {
        return ordinalToSymbol[ordinal];
    }

    /**
     * @see org.biomojo.alphabet.Alphabet#getOrdinalForSymbol(java.lang.Object)
     */
    @Override
    public int getOrdinalForSymbol(final Byte value) {
        return getOrdinalForSymbol(value.byteValue());
    }

    /**
     * @see org.biomojo.alphabet.Alphabet#getSymbolForOrdinal(int)
     */
    @Override
    public Byte getSymbolForOrdinal(final int ordinal) {
        return getByteSymbolForOrdinal(ordinal);
    }

    /**
     * @see org.biomojo.alphabet.Alphabet#isValid(java.lang.Object)
     */
    @Override
    public boolean isValid(final Byte symbol) {
        return isValid(symbol.byteValue());
    }

    /**
     * @see org.biomojo.alphabet.Alphabet#isCanonical(java.lang.Object)
     */
    @Override
    public boolean isCanonical(final Byte symbol) {
        return isCanonical(symbol.byteValue());
    }

    @Override
    public boolean isCanonical(final byte symbol) {
        return (getCanonical(symbol) == symbol);
    }

    /**
     * @see org.biomojo.alphabet.Alphabet#getCanonical(java.lang.Object)
     */
    @Override
    public Byte getCanonical(final Byte symbol) {
        return getCanonical(symbol.byteValue());
    }

    /**
     * @see org.biomojo.alphabet.ByteAlphabet#isValid(byte[], int, int)
     */
    @Override
    public final boolean isValid(final byte[] symbols, final int start, final int end) {
        for (int i = start; i < end; ++i) {
            if (!valid[symbols[i]]) {
                return false;
            }
        }
        return true;
    }

    /**
     * @see org.biomojo.alphabet.Alphabet#numCanonicalSymbols()
     */
    @Override
    public int numCanonicalSymbols() {
        return numCanonicalSymbols;
    }

    /**
     *
     */
    protected void initOrdinals() {
        for (int i = 0; i < valid.length; ++i) {
            if (valid[i]) {
                if (canonicalSymbols[i] == i) {
                    ordinalToSymbol[numSymbols] = (byte) i;
                    symbolToOrdinal[i] = (byte) numSymbols;
                    ++numSymbols;
                    ++numCanonicalSymbols;
                }
            }
        }
        for (int i = 0; i < valid.length; ++i) {
            if (valid[i]) {
                if (canonicalSymbols[i] != i) {
                    ordinalToSymbol[numSymbols] = (byte) i;
                    symbolToOrdinal[i] = (byte) numSymbols;
                    ++numSymbols;
                }
            }
        }
    }

    @Override
    public void validate(final byte[] symbols, final int start, final int end) throws InvalidSymbolException {
        for (int i = start; i < end; ++i) {
            if (!valid[symbols[i]])
                throw new InvalidSymbolException(symbols[i], i);
        }
        return;
    }

    /**
     * @return the canonical
     */
    @Override
    public boolean isCanonical() {
        return canonical;
    }

    @Override
    public ByteAlphabet getCanonical() {
        return Alphabets.getAlphabet(this.getId() & ~1, ByteAlphabet.class);
    }
}
