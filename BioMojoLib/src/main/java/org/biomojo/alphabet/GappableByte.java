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

import org.biomojo.symbols.CommonSymbols;

// TODO: Auto-generated Javadoc
/**
 * Interface for alphabets supporting gap symbols.
 *
 * @author Hugh Eaves
 * @param <A>
 *            the generic type
 */
public interface GappableByte<A extends ByteAlphabet, G extends GappedByte<A>>
        extends ByteAlphabet, Gappable<Byte, A, G> {

    @Override
    public default G getGapped() {
        return (G) Alphabets.getAlphabet(getId() | AlphabetVariant.WITH_GAP);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.GappableAlphabet#supportsGaps()
     */
    @Override
    public default boolean isGapped() {
        return ((getId() & AlphabetVariant.WITH_GAP) != 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.GappableAlphabet#gapSymbol()
     */
    @Override
    public default Byte gapSymbol() {
        return CommonSymbols.GAP;
    }
}
