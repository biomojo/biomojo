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
package org.biomojo.sequence;

import org.biomojo.alphabet.Alphabet;
import org.biomojo.alphabet.InvalidSymbolException;
import org.biomojo.core.Described;
import org.biomojo.property.Propertied;
import org.java0.collection.LongList;
import org.java0.core.type.LongIdentified;

/**
 * The {@code Seq} interface represents a sequence of values of type T from the
 * {@link org.biomojo.alphabet.Alphabet} A.
 *
 * @author Hugh Eaves
 * 
 * @param <T>
 *            the type of values in the sequence
 * @param <A>
 *            the Alphabet for this sequence
 */
public interface Seq<T, A extends Alphabet<T>> extends LongList<T>, Propertied, Described, LongIdentified {

    /**
     * Sets the id for this sequence.
     *
     * @param id
     *            the new id
     */
    void setId(long id);

    /**
     * Gets the alphabet for this sequence.
     *
     * @return the alphabet
     */
    public A getAlphabet();

    /**
     * Modifies this sequence by replacing each of the elements with the
     * equivalent symbol in the given alphabet, and then set the alphabet of
     * this sequence to the given alphabet.
     * 
     * @param alphabet
     * @throws InvalidSymbolException
     *             if the current sequence contains elements that can not be
     *             represented in the new alphabet
     */
    public void setAlphabet(A alphabet) throws InvalidSymbolException;

    /**
     * Modifies this sequence by replacing each of the elements with its
     * canonical form, and then changes the alphabet of this sequence the
     * canonical version.
     */
    public void canonicalize();

    /**
     * Reverse the order of elements in this sequence.
     */
    default void reverse() {
        final long lastPos = sizeL() - 1;
        final long midPos = lastPos / 2;
        for (long i = 0; i < midPos; ++i) {
            final T val = get(i);
            // swap values
            set(i, get(lastPos - i));
            set(lastPos - i, val);
        }
    }
}
