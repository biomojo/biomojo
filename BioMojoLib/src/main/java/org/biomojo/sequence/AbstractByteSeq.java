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

import org.biomojo.alphabet.ByteAlphabet;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractByteSeq.
 *
 * @author Hugh Eaves
 * @param <A>
 *            the generic type
 */
@Entity
@DiscriminatorValue("Y")
public abstract class AbstractByteSeq<A extends ByteAlphabet> extends AbstractSeq<Byte, A> implements ByteSeq<A> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The alphabet. */

    protected ByteAlphabet alphabet;

    /**
     * Instantiates a new abstract byte seq.
     */
    public AbstractByteSeq() {
    }

    /**
     * Instantiates a new abstract byte seq.
     *
     * @param alphabet
     *            the alphabet
     */
    public AbstractByteSeq(final A alphabet) {
        initAlphabet(alphabet);
    }

    /**
     * Inits the alphabet.
     *
     * @param alphabet
     *            the alphabet
     */
    protected void initAlphabet(final A alphabet) {
        this.alphabet = alphabet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.Seq#getAlphabet()
     */
    @Override
    public A getAlphabet() {
        return (A) alphabet;
    }

    // @Override
    // public void set(final long index, final byte symbol) throws
    // InvalidSymbolException {
    // if (index > Integer.MAX_VALUE) {
    // throw new ArrayIndexOutOfBoundsException(
    // "AbstractByteSeq only supports index values <= " + Integer.MAX_VALUE);
    // }
    // set((int) index, symbol);
    // }
    //
    // @Override
    // public byte getByte(final long index) {
    // if (index > Integer.MAX_VALUE) {
    // throw new ArrayIndexOutOfBoundsException(
    // "AbstractByteSeq only supports index values <= " + Integer.MAX_VALUE);
    // }
    // return getByte((int) index);
    // }

    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer(size());
        for (int i = 0; i < size(); ++i) {
            buf.append(charAt(i));
        }
        return buf.toString();
    }

    @Override
    public ByteSeq<A> subList(final long from, final long to) {
        return new SubByteSeq<A>(this, from, to);
    }

    @Override
    public ByteSeqIterator listIterator(final long index) {
        return new DefaultByteSeqIterator(this, index);
    }

}
