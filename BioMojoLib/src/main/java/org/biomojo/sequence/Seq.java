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
import org.biomojo.core.Described;
import org.biomojo.core.Identified;
import org.biomojo.property.Propertied;
import org.java0.collection.DefaultList;

/**
 * The {@code Seq} interface represents a sequence of values of the specified
 * Java type from the specified {@link org.biomojo.alphabet.Alphabet}.
 *
 * @author Hugh Eaves
 * @param <T>
 *            the type of values in the sequence
 * @param <A>
 *            the alphabet from which values are taken
 */
public interface Seq<T, A extends Alphabet<T>> extends DefaultList<T>, Propertied, Described, Identified {

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    void setId(long id);

    /**
     * Gets the alphabet.
     *
     * @return the alphabet
     */
    public A getAlphabet();

    /**
     * Sets the alphabet.
     *
     * @param alphabet
     *            the new alphabet
     */
    public void setAlphabet(A alphabet);

    /**
     * Canonicalize.
     */
    public void canonicalize();

    void add(long index, T element);

    T set(long index, T element);

    T get(long index);

    long lsize();

    /**
     * Reverse the order of elements in the s
     */
    default void reverse() {
        final long lastPos = lsize() - 1;
        final long midPos = lastPos / 2;
        for (long i = 0; i < midPos; ++i) {
            final T val = get(i);
            // swap values
            set(i, get(lastPos - i));
            set(lastPos - i, val);
        }
    }

}
