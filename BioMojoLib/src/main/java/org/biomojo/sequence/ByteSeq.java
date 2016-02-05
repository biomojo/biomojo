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

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.InvalidSymbolException;

// TODO: Auto-generated Javadoc
/**
 * The Interface ByteSeq.
 *
 * @param <A>
 *            the generic type
 */
public interface ByteSeq<A extends ByteAlphabet> extends Seq<Byte, A>, CharSequence {

    /**
     * To byte array.
     *
     * @return the byte[]
     */
    byte[] toByteArray();

    byte[] toByteArray(long start, long end);

    /**
     * Sets the all.
     *
     * @param sequence
     *            the new all
     * @throws InvalidSymbolException
     *             the invalid symbol exception
     */
    void setAll(byte[] sequence) throws InvalidSymbolException;

    /**
     * Sets the all.
     *
     * @param sequence
     *            the sequence
     * @param validate
     *            the validate
     * @throws InvalidSymbolException
     *             the invalid symbol exception
     */
    void setAll(byte[] sequence, boolean validate) throws InvalidSymbolException;

    /**
     * Gets the byte.
     *
     * @param index
     *            the index
     * @return the byte
     */
    default byte getByte(final int index) {
        return getByte((long) index);
    }

    /**
     * Gets the byte.
     *
     * @param index
     *            the index
     * @return the byte
     */
    byte getByte(long index);

    /**
     * Sets the.
     *
     * @param index
     *            the index
     * @param symbol
     *            the symbol
     * @throws InvalidSymbolException
     *             the invalid symbol exception
     */
    default void set(final int index, final byte symbol) throws InvalidSymbolException {
        set((long) index, symbol);
    }

    void set(long index, byte symbol) throws InvalidSymbolException;

    void add(final byte symbol) throws InvalidSymbolException;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.CharSequence#charAt(int)
     */
    @Override
    default char charAt(final int index) {
        return (char) getByte(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.CharSequence#subSequence(int, int)
     */
    @Override
    default CharSequence subSequence(final int start, final int end) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.CharSequence#length()
     */
    @Override
    default int length() {
        return size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#get(int)
     */
    @Override
    default Byte get(final int index) {
        return getByte(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.Seq#get(long)
     */
    @Override
    default Byte get(final long index) {
        return getByte(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    default Byte set(final int index, final Byte symbol) throws InvalidSymbolException {
        final Byte oldVal = get(index);
        set(index, symbol.byteValue());
        return oldVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.Seq#set(long, java.lang.Object)
     */
    @Override
    default Byte set(final long index, final Byte symbol) {
        final Byte oldVal = get(index);
        set(index, symbol.byteValue());
        return oldVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.Seq#add(long, java.lang.Object)
     */
    @Override
    default void add(final long index, final Byte value) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#subList(int, int)
     */
    @Override
    ByteSeq<A> subList(long fromIndex, long toIndex);

    @Override
    default ByteSeq<A> subList(final int fromIndex, final int toIndex) {
        return subList((long) fromIndex, (long) toIndex);
    }

    @Override
    default ByteSeqIterator iterator() {
        return listIterator(0L);
    }

    @Override
    default ByteSeqIterator listIterator() {
        return listIterator(0L);
    }

    @Override
    default ByteSeqIterator listIterator(final int index) {
        return listIterator((long) index);
    }

    @Override
    ByteSeqIterator listIterator(long index);

}
