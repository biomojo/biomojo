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

public abstract class AbstractCanonicalizableByteAlphabet<A extends CanonicalizableByte<?>> extends AbstractByteAlphabet
        implements CanonicalizableByte<A> {

    protected AbstractCanonicalizableByteAlphabet(final long id) {
        super(id);
    }

    @Override
    public boolean isCanonical() {
        return ((getId() & 1) == 0);
    }

    @Override
    public Byte getCanonical(final Byte value) {
        return value.byteValue();
    }
}
