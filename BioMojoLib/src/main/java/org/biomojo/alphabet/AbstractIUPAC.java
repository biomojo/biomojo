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

import org.biomojo.symbols.AminoAcids;
import org.biomojo.symbols.CommonSymbols;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractIUPAC<A extends IUPAC<A>> extends TableBasedByteAlphabet<A> implements IUPAC<A> {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractIUPAC.class.getName());

    /** The gap. */
    protected boolean gap;

    /** The any. */
    protected boolean any;

    /** The ambiguity. */
    protected boolean ambiguity;

    /**
     * Instantiates a new abstract iupac alphabet.
     *
     * @param id
     *            the id
     * @param coreSymbols
     *            the core symbols
     */
    protected AbstractIUPAC(final int id, final byte[] coreSymbols) {
        super(id);
        final int flags = id % IUPACVariant.NUM_VARIANTS;
        if ((flags & AlphabetVariant.WITH_NON_CANONICAL) != 0) {
            canonical = false;
        } else {
            canonical = true;
        }
        addSymbols(coreSymbols);
        if ((flags & AlphabetVariant.WITH_GAP) != 0) {
            addGapSymbols();
            gap = true;
        }
        if ((flags & IUPACVariant.WITH_ANY) != 0) {
            addAnySymbols();
            any = true;
        }
        if ((flags & IUPACVariant.WITH_AMBIGIGUITY) != 0) {
            addAmbiguitySymbols();
            ambiguity = true;
        }
        initOrdinals();
    }

    /**
     * Adds the gap symbols.
     */
    protected void addGapSymbols() {
        addSymbol(CommonSymbols.GAP);
        if (!isCanonical()) {
            addSymbol(AminoAcids.GAP, AminoAcids.ALT_GAP);
        }
    }

    /**
     * Adds the any symbols.
     */
    protected abstract void addAnySymbols();

    /**
     * Adds the ambiguity symbols.
     */
    protected abstract void addAmbiguitySymbols();

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.ByteAlphabet#getCanonical(byte)
     */
    @Override
    public byte getCanonical(final byte symbol) {
        return canonicalSymbols[symbol];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.ByteAlphabet#makeCanonical(byte[])
     */
    @Override
    public void makeCanonical(final byte[] symbols) {
        makeCanonical(symbols, 0, symbols.length);
    }

    /**
     * Supports any.
     *
     * @return true, if successful
     * @see org.biomojo.alphabet.BioPolymerAlphabet#supportsAny()
     */
    @Override
    public boolean supportsAny() {
        return any;
    }

    /**
     * Supports ambiguity.
     *
     * @return true, if successful
     * @see org.biomojo.alphabet.BioPolymerAlphabet#supportsAmbiguity()
     */
    @Override
    public boolean supportsAmbiguity() {
        return ambiguity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.AbstractAlphabet#toString()
     */
    @Override
    public String toString() {
        return this.getClass().getName() + "(id=" + getId() + ", gap = " + gap + ", any = " + any + ", ambiguity = "
                + ambiguity + ")";
    }
}
