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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.biomojo.alphabet.Alphabet;
import org.biomojo.core.AbstractPropertiedEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractSeq.
 *
 * @author Hugh Eaves
 * @param <T>
 *            the generic type
 * @param <A>
 *            the generic type
 */
@Entity
@DiscriminatorValue("X")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractSeq<T, A extends Alphabet<T>> extends AbstractPropertiedEntity implements Seq<T, A> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new abstract seq.
     */
    public AbstractSeq() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.core.AbstractEntity#setId(long)
     */
    @Override
    public void setId(final long id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.Seq#add(java.lang.Object)
     */
    @Override
    public boolean add(final T value) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.Seq#subList(int, int)
     */
    @Override
    public Seq<T, A> subList(final long fromIndex, final long toIndex) {
        return new SubSeq<T, A, Seq<T, A>>(this, fromIndex, toIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.Seq#remove(int)
     */
    @Override
    public T remove(final int pos) {
        throw new UnsupportedOperationException();
    }

}
