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
package org.biomojo.biojava;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.AccessionID;
import org.biojava.nbio.core.sequence.template.Compound;
import org.biojava.nbio.core.sequence.template.CompoundSet;
import org.biojava.nbio.core.sequence.template.ProxySequenceReader;
import org.biojava.nbio.core.sequence.template.SequenceMixin;
import org.biojava.nbio.core.sequence.template.SequenceProxyView;
import org.biojava.nbio.core.sequence.template.SequenceView;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.sequence.ByteSeq;

/**
 * The Class BioJavaSequence.
 *
 * @param <T>
 *            the generic type
 */
public class BioJavaSequenceAdapter<A extends ByteAlphabet, T extends Compound> implements ProxySequenceReader<T> {

    /** The sequence. */
    public ByteSeq<A> sequence;

    /** The compound factory. */
    private BioJavaCompoundFactory<T> compoundFactory;

    /** The accession id. */
    private AccessionID accessionId;

    /**
     * Instantiates a new bio java sequence.
     *
     * @param compoundSet
     *            the compound set
     * @param sequence
     *            the sequence
     */
    public BioJavaSequenceAdapter(final ByteSeq<A> sequence) {
        this.sequence = sequence;
        this.compoundFactory = BioJavaFactoryFactory.getCompoundFactory(sequence.getAlphabet());
    }

    /**
     * The Class BioJavaSequenceIterator.
     */
    public class BioJavaSequenceIterator implements Iterator<T> {

        /** The pos. */
        private final int pos = 0;

        /**
         * Instantiates a new bio java sequence iterator.
         *
         * @param sequence
         *            the sequence
         */
        protected BioJavaSequenceIterator() {
        }

        /**
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            if (pos == sequence.size()) {
                return false;
            }
            return true;
        }

        /**
         * @see java.util.Iterator#next()
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return compoundFactory.getCompoundFor(sequence.getByte(pos));
        }

        /**
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<T> iterator() {
        return new BioJavaSequenceIterator();
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Accessioned#getAccession()
     */
    @Override
    public AccessionID getAccession() {
        if (accessionId == null) {
            accessionId = new AccessionID();
        }
        return accessionId;
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Sequence#getLength()
     */
    @Override
    public int getLength() {
        return sequence.size();
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Sequence#getCompoundAt(int)
     */
    @Override
    public T getCompoundAt(final int position) {
        return compoundFactory.getCompoundFor(sequence.getByte(position));
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Sequence#getIndexOf(org.biojava.nbio.core.sequence.template.Compound)
     */
    @Override
    public int getIndexOf(final T compound) {
        return SequenceMixin.indexOf(this, compound);
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Sequence#getLastIndexOf(org.biojava.nbio.core.sequence.template.Compound)
     */
    @Override
    public int getLastIndexOf(final T compound) {
        return SequenceMixin.lastIndexOf(this, compound);
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Sequence#getSequenceAsString()
     */
    @Override
    public String getSequenceAsString() {
        return SequenceMixin.toString(this);
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Sequence#getAsList()
     */
    @Override
    public List<T> getAsList() {
        return SequenceMixin.toList(this);
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Sequence#getSubSequence(java.lang.Integer,
     *      java.lang.Integer)
     */
    @Override
    public SequenceView<T> getSubSequence(final Integer start, final Integer end) {
        return new SequenceProxyView<T>(this, start, end);
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Sequence#getCompoundSet()
     */
    @Override
    public CompoundSet<T> getCompoundSet() {
        return compoundFactory.getCompoundSet();
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Sequence#countCompounds(org.biojava.nbio.core.sequence.template.Compound[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public int countCompounds(final T... compounds) {
        return SequenceMixin.countCompounds(this, compounds);
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.Sequence#getInverse()
     */
    @Override
    public SequenceView<T> getInverse() {
        return SequenceMixin.inverse(this);
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.SequenceReader#setCompoundSet(org.biojava.nbio.core.sequence.template.CompoundSet)
     */
    @Override
    public void setCompoundSet(final CompoundSet<T> compoundSet) {
        sequence.setAlphabet(BioJavaFactoryFactory.getByteAlphabet(compoundSet));
        compoundFactory = BioJavaFactoryFactory.getCompoundFactory(sequence.getAlphabet());
    }

    /**
     * @see org.biojava.nbio.core.sequence.template.SequenceReader#setContents(java.lang.String)
     */
    @Override
    public void setContents(final String contents) throws CompoundNotFoundException {
        this.sequence.setAll(contents.getBytes());
    }

}
