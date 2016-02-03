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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractByteAlphabet.
 *
 * @author Hugh Eaves
 */
public abstract class TableBasedByteAlphabet<A extends CanonicalizableByte<?>>
        extends AbstractCanonicalizableByteAlphabet<A> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(TableBasedByteAlphabet.class.getName());

    /** The valid. */
    protected final boolean valid[] = new boolean[Byte.MAX_VALUE + 1];

    /** The ordinal to symbol. */
    protected final byte ordinalToSymbol[] = new byte[Byte.MAX_VALUE + 1];

    /** The symbol to ordinal. */
    protected final byte symbolToOrdinal[] = new byte[Byte.MAX_VALUE + 1];

    /** The canonical symbols. */
    protected final byte canonicalSymbols[] = new byte[Byte.MAX_VALUE + 1];

    /** The num symbols. */
    protected int numSymbols = 0;

    /** The num canonical symbols. */
    protected int numCanonicalSymbols = 0;

    /** The canonical. */
    protected boolean canonical;

    /**
     * Create a new AbstractByteTableAlphabet.
     *
     * @param id
     *            the id
     */
    protected TableBasedByteAlphabet(final int id) {
        super(id);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.ByteAlphabet#isValid(byte)
     */
    @Override
    public boolean isValid(final byte symbol) {
        return valid[symbol];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.ByteAlphabet#getOrdinalForSymbol(byte)
     */
    @Override
    public int getOrdinalForSymbol(final byte symbol) {
        return (symbolToOrdinal[symbol]);
    }

    /**
     * Adds the symbol.
     *
     * @param symbol
     *            the symbol
     */
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

    /**
     * Adds the symbols.
     *
     * @param symbols
     *            the symbols
     */
    protected void addSymbols(final byte[] symbols) {
        for (final byte symbol : symbols) {
            addSymbol(symbol);
        }
    }

    /**
     * Adds the symbol.
     *
     * @param symbol
     *            the symbol
     * @param canonicalSymbol
     *            the canonical symbol
     */
    protected void addSymbol(final byte symbol, final byte canonicalSymbol) {
        logger.debug("addSymbol: symbol = {}, canonicalSymbol = {}", (char) symbol, (char) canonicalSymbol);

        valid[symbol] = true;
        canonicalSymbols[symbol] = canonicalSymbol;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.Alphabet#numSymbols()
     */
    @Override
    public int numSymbols() {
        return numSymbols;
    }

    /**
     * Gets the byte symbol for ordinal.
     *
     * @param ordinal
     *            the ordinal
     * @return the byte symbol for ordinal
     * @see org.biomojo.sequence.alphabet.ByteAlphabet#getByteSymbolForOrdinal(int)
     */
    @Override
    public byte getByteSymbolForOrdinal(final int ordinal) {
        return ordinalToSymbol[ordinal];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.ByteAlphabet#isCanonical(byte)
     */
    @Override
    public boolean isCanonical(final byte symbol) {
        return (getCanonical(symbol) == symbol);
    }

    /**
     * Checks if is valid.
     *
     * @param symbols
     *            the symbols
     * @param start
     *            the start
     * @param end
     *            the end
     * @return true, if is valid
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
     * Num canonical symbols.
     *
     * @return the int
     * @see org.biomojo.alphabet.Alphabet#numCanonicalSymbols()
     */
    @Override
    public int numCanonicalSymbols() {
        return numCanonicalSymbols;
    }

    /**
     * Inits the ordinals.
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

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.ByteAlphabet#validate(byte[], int, int)
     */
    @Override
    public void validate(final byte[] symbols, final int start, final int end) throws InvalidSymbolException {
        for (int i = start; i < end; ++i) {
            if (!valid[symbols[i]])
                throw new InvalidSymbolException(symbols[i], i);
        }
        return;
    }

    /**
     * Checks if is canonical.
     *
     * @return the canonical
     */
    @Override
    public boolean isCanonical() {
        return canonical;
    }
}
