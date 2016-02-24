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

// TODO: Auto-generated Javadoc
/**
 * The Class AminoAcidAlphabetImpl.
 *
 * @author Hugh Eaves
 */
public class BasicAminoAcid extends AbstractIUPAC<AminoAcid> implements AminoAcid {

    /**
     * Instantiates a new amino acid alphabet impl.
     *
     * @param id
     *            the id
     */
    protected BasicAminoAcid(final long id) {
        super(id, AminoAcids.CORE_SYMBOLS);
    }

    /**
     * Adds the any symbols.
     *
     * @see org.biomojo.alphabet.AbstractBioPolymerAlphabet#addAnySymbols()
     */
    @Override
    protected void addAnySymbols() {
        addSymbol(AminoAcids.ANY);
    }

    /**
     * Adds the ambiguity symbols.
     *
     * @see org.biomojo.alphabet.AbstractBioPolymerAlphabet#addAmbiguitySymbols()
     */
    @Override
    protected void addAmbiguitySymbols() {
        addSymbols(AminoAcids.AMBIGUITY_SYMBOLS);
    }

}
