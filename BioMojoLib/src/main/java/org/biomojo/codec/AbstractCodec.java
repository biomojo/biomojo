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

package org.biomojo.codec;

import org.biomojo.alphabet.Alphabet;

/**
 * An AbstractCodec handles the encoding/decoding of Alphabet entities
 * (characters), into a codec specific byte array representation of those
 * entities. Generally speaking, the purpose of a codec is to allow more
 * efficient and/or more manageable storage of a collection of an Alphabets
 * entities.
 *
 */
public abstract class AbstractCodec<D, E> implements Codec<D, E> {

    protected int id;

    protected AbstractCodec(int id) {
        this.id = id;
    }

    /**
     * Return the unique identifier for this codec.
     *
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean supportsAlphabet(Alphabet<D> alphabet) {
        return true;
    }

}