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

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

// TODO: Auto-generated Javadoc
/**
 * The Class SeqSubList.
 *
 * @param <T> the generic type
 */
@Entity
public class SeqSubList<T extends Seq<?, ?>> extends AbstractSeqList<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8154271883890794936L;

    /** The target list. */
    @ManyToOne(targetEntity = AbstractSeqList.class)
    protected SeqList<T> targetList;

    /** The from index. */
    private int fromIndex;

    /** The to index. */
    private int toIndex;

    /**
     * Instantiates a new seq sub list.
     */
    protected SeqSubList() {
    }

    /**
     * Instantiates a new seq sub list.
     *
     * @param sequenceList the sequence list
     * @param fromIndex the from index
     * @param toIndex the to index
     */
    public SeqSubList(final SeqList<T> sequenceList, final int fromIndex, final int toIndex) {
        this.targetList = sequenceList;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    /**
     * Gets the from index.
     *
     * @return the from index
     */
    public int getFromIndex() {
        return fromIndex;
    }

    /**
     * Gets the to index.
     *
     * @return the to index
     */
    public int getToIndex() {
        return toIndex;
    }

    /**
     * Gets the target list.
     *
     * @return the target list
     */
    public SeqList<T> getTargetList() {
        return targetList;
    }

    /* (non-Javadoc)
     * @see java.util.List#get(int)
     */
    @Override
    public T get(final int index) {
        return targetList.get(fromIndex + index);
    }

    /* (non-Javadoc)
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public T set(final int index, final T value) {
        return targetList.set(fromIndex + index, value);
    }

    /* (non-Javadoc)
     * @see java.util.List#size()
     */
    @Override
    public int size() {
        return toIndex - fromIndex;
    }

    /* (non-Javadoc)
     * @see java.util.List#add(java.lang.Object)
     */
    @Override
    public boolean add(final T value) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<T> subList(final int from, final int to) {
        return new SeqSubList<T>(targetList, fromIndex + from, toIndex + to);
    }

    /* (non-Javadoc)
     * @see java.util.List#remove(int)
     */
    @Override
    public T remove(final int pos) {
        throw new UnsupportedOperationException();
    }
}