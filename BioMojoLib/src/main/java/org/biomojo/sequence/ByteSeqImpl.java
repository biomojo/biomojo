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

import java.util.Arrays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.InvalidSymbolException;
import org.java0.core.type.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class ByteSeqImpl.
 *
 * @author Hugh Eaves
 * @param <A> the generic type
 */
@Entity
@DiscriminatorValue("B")
public class ByteSeqImpl<A extends ByteAlphabet> extends AbstractByteSeq<A> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The data. */
    protected byte[] data = Constants.EMPTY_BYTE_ARRAY;

    /** The description. */
    protected CharSequence description = Constants.EMPTY_STRING;

    /** The length. */
    protected int length;

    /**
     * Instantiates a new byte seq impl.
     */
    public ByteSeqImpl() {

    }

    /**
     * Instantiates a new byte seq impl.
     *
     * @param alphabet the alphabet
     */
    public ByteSeqImpl(final A alphabet) {
        super(alphabet);
    }

    /**
     * Instantiates a new byte seq impl.
     *
     * @param data the data
     */
    public ByteSeqImpl(final byte[] data) {
        super();
        setAll(data);
    }

    /**
     * Instantiates a new byte seq impl.
     *
     * @param data the data
     * @param alphabet the alphabet
     */
    public ByteSeqImpl(final byte[] data, final A alphabet) {
        super(alphabet);
        setAll(data);
    }

    /**
     * Instantiates a new byte seq impl.
     *
     * @param initialCapacity the initial capacity
     * @param alphabet the alphabet
     */
    public ByteSeqImpl(final int initialCapacity, final A alphabet) {
        super(alphabet);
        data = new byte[initialCapacity];
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#setAll(byte[])
     */
    @Override
    public void setAll(final byte[] sequence) throws InvalidSymbolException {
        setAll(sequence, true);
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#getAllBytes()
     */
    @Override
    public byte[] getAllBytes() {
        if (data.length > length) {
            data = Arrays.copyOf(data, length);
        }
        return data;
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#setAll(byte[], boolean)
     */
    @Override
    public void setAll(final byte[] sequence, final boolean validate) throws InvalidSymbolException {
        if (validate) {
            alphabet.validate(sequence);
        }
        length = sequence.length;
        data = sequence;
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#getValue(int)
     */
    @Override
    public byte getValue(final int index) {
        return data[index];
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#setValue(byte, int)
     */
    @Override
    public void setValue(final byte symbol, final int index) throws InvalidSymbolException {
        alphabet.validate(symbol);
        data[index] = symbol;
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.Seq#size()
     */
    @Override
    public int size() {
        return length;
    }

    /* (non-Javadoc)
     * @see org.biomojo.core.Described#getDescription()
     */
    @Override
    public CharSequence getDescription() {
        return description;
    }

    /* (non-Javadoc)
     * @see org.biomojo.core.Described#setDescription(java.lang.CharSequence)
     */
    @Override
    public void setDescription(final CharSequence description) {
        this.description = description;
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.Seq#setAlphabet(org.biomojo.alphabet.Alphabet)
     */
    @Override
    public void setAlphabet(final A newAlphabet) {
        newAlphabet.validate(data);
        this.alphabet = newAlphabet;
    }

    /**
     * Canonicalize.
     *
     * @see org.biomojo.sequence.SeqI#canonicalize()
     */
    @Override
    public void canonicalize() {
        if (alphabet.isCanonical()) {
            return;
        } else {
            alphabet.makeCanonical(data, 0, length);
            alphabet = (A) alphabet.getCanonical();
        }
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#replace(byte[], int, int, int)
     */
    @Override
    public void replace(final byte[] srcSeq, final int srcPos, final int destPos, final int length) {
        System.arraycopy(srcSeq, srcPos, data, destPos, length);
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#append(byte)
     */
    @Override
    public void append(final byte symbol) {
        checkStorage(1);
        data[length++] = symbol;
    }

    /**
     * Check storage.
     *
     * @param numElements the num elements
     */
    private final void checkStorage(final int numElements) {
        if (data.length < length + numElements) {
            data = Arrays.copyOf(data, (data.length + 1) * 2);
        }
    }
}
