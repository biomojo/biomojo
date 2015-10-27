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

import org.biomojo.symbols.AminoAcids;
import org.biomojo.symbols.CommonSymbols;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractIUPACAlphabet extends AbstractByteAlphabet implements IUPACAlphabet {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractIUPACAlphabet.class.getName());

    protected boolean gap;
    protected boolean any;
    protected boolean ambiguity;

    protected AbstractIUPACAlphabet(final int id, final byte[] coreSymbols) {
        super(id);
        final int flags = id % IUPACAlphabetVariant.NUM_VARIANTS;
        if ((flags & IUPACAlphabetVariant.WITH_NON_CANONICAL) != 0) {
            canonical = false;
        } else {
            canonical = true;
        }
        addSymbols(coreSymbols);
        if ((flags & IUPACAlphabetVariant.WITH_GAP) != 0) {
            addGapSymbols();
            gap = true;
        }
        if ((flags & IUPACAlphabetVariant.WITH_ANY) != 0) {
            addAnySymbols();
            any = true;
        }
        if ((flags & IUPACAlphabetVariant.WITH_AMBIGIGUITY) != 0) {
            addAmbiguitySymbols();
            ambiguity = true;
        }
        initOrdinals();
    }

    protected void addGapSymbols() {
        addSymbol(CommonSymbols.GAP);
        if (!isCanonical()) {
            addSymbol(AminoAcids.GAP, AminoAcids.ALT_GAP);
        }
    }

    protected abstract void addAnySymbols();

    protected abstract void addAmbiguitySymbols();

    @Override
    public byte getCanonical(final byte symbol) {
        return canonicalSymbols[symbol];
    }

    @Override
    public void makeCanonical(final byte[] symbols) {
        makeCanonical(symbols, 0, symbols.length);
    }

    /**
     * @see org.biomojo.alphabet.BioPolymerAlphabet#supportsGaps()
     */
    @Override
    public boolean supportsGaps() {
        return gap;
    }

    /**
     * @see org.biomojo.alphabet.BioPolymerAlphabet#supportsAny()
     */
    @Override
    public boolean supportsAny() {
        return any;
    }

    /**
     * @see org.biomojo.alphabet.BioPolymerAlphabet#supportsAmbiguity()
     */
    @Override
    public boolean supportsAmbiguity() {
        return ambiguity;
    }

    @Override
    public ByteAlphabet getCanonical() {
        return this;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(id=" + getId() + ", gap = " + gap + ", any = " + any + ", ambiguity = "
                + ambiguity + ")";
    }
}
