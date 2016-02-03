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
package org.biomojo.alignment;

import org.biomojo.alphabet.ByteAlphabet;

public abstract class AbstractByteSubstitutionMatrix implements ByteSubstitutionMatrix {

    /** The alphabet. */
    private final ByteAlphabet alphabet;

    protected AbstractByteSubstitutionMatrix(final ByteAlphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public ByteAlphabet getAlphabet() {
        return alphabet;
    }

    @Override
    public int getScore(final Byte from, final Byte to) {
        return getScore(from.byteValue(), to.byteValue());
    }

}
