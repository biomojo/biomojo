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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;

import org.biomojo.core.CommonProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

// TODO: Auto-generated Javadoc
/**
 * The Class SeqArrayList.
 *
 * @param <T> the generic type
 */
@Entity
public class SeqArrayList<T extends Seq<?, ?>> extends AbstractSeqList<T> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5511937136125948750L;

    /** The sequences. */
    @ManyToMany(targetEntity = AbstractSeq.class)
    @OrderColumn
    @LazyCollection(LazyCollectionOption.EXTRA)
    protected List<T> sequences = new ArrayList<>();

    /**
     * Instantiates a new seq array list.
     */
    public SeqArrayList() {

    }

    /**
     * Create a new BasicMultiSequence.
     *
     * @param name the name
     */
    public SeqArrayList(final String name) {
        setProp(CommonProperties.NAME, name);
    }

    /* (non-Javadoc)
     * @see java.util.List#get(int)
     */
    @Override
    public T get(final int index) {
        return sequences.get(index);
    }

    /* (non-Javadoc)
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public T set(final int index, final T value) {
        return sequences.get(index);
    }

    /* (non-Javadoc)
     * @see java.util.List#size()
     */
    @Override
    public int size() {
        return sequences.size();
    }

    /* (non-Javadoc)
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<T> subList(final int start, final int end) {
        return new SeqSubList<T>(this, start, end);
    }

    /* (non-Javadoc)
     * @see java.util.List#add(java.lang.Object)
     */
    @Override
    public boolean add(final T e) {
        return sequences.add(e);
    }

    /* (non-Javadoc)
     * @see java.util.List#remove(int)
     */
    @Override
    public T remove(final int pos) {
        return sequences.remove(pos);
    }
}
