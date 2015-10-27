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
package org.biomojo.sequence;

import java.util.List;

import org.biomojo.alphabet.Alphabet;
import org.biomojo.core.Described;
import org.biomojo.core.Identified;
import org.biomojo.property.Propertied;
import org.java0.collection.DefaultList;

// TODO: Auto-generated Javadoc
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

    /**
     * Size.
     *
     * @return the int
     * @see java.util.List#size()
     */
    @Override
    int size();

    /**
     * Gets the.
     *
     * @param index
     *            the index
     * @return the t
     * @see java.util.List#get(int)
     */
    @Override
    public T get(final int index);

    /**
     * Sets the.
     *
     * @param index
     *            the index
     * @param value
     *            the value
     * @return the t
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public T set(final int index, final T value);

    /**
     * Adds the.
     *
     * @param value
     *            the value
     * @return true, if successful
     * @see java.util.List#add(java.lang.Object)
     */
    @Override
    public boolean add(final T value);

    /**
     * Sub list.
     *
     * @param from
     *            the from
     * @param to
     *            the to
     * @return the list
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<T> subList(final int from, final int to);

    /**
     * Removes the.
     *
     * @param pos
     *            the pos
     * @return the t
     * @see java.util.List#remove(int)
     */
    @Override
    public T remove(final int pos);
}
