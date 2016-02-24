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

/**
 * Represents an sequence, where the underlying type is a java "byte" primitive.
 * This interface avoids the boxing/unboxing that would be required with Byte
 * objects.
 * 
 * @author Hugh Eaves
 *
 * @param <A>
 *            the Alphabet for this sequence
 */
public interface ByteSeq<A extends ByteAlphabet> extends Seq<Byte, A>, CharSequence {

    /**
     * Returns this sequence as a byte array. Note that the data is defensively
     * copied, so modifications to the array will not alter the data in the
     * original sequence.
     *
     * @return the byte[]
     */
    byte[] toByteArray();

    /**
     * Returns a portion of this sequence as a byte array. The data returned
     * will start at "startPos" inclusive, and end as "endPos" exclusive. Note
     * that the data is defensively copied, so modifications to the array will
     * not alter the data in the original sequence.
     * 
     * @param start
     * @param end
     * @return
     */
    byte[] toByteArray(long startPos, long endEnd);

    /**
     * Replaces the contents of this sequence with the symbols contained in the
     * given byte array. The array contents in validated against the current
     * alphabet for this sequence, resulting in an "InvalidSymbolException"
     * being thrown if the array contains invalid symbols.
     *
     * @param sequence
     *            the new all
     * @throws InvalidSymbolException
     *             the invalid symbol exception
     */
    void setAll(byte[] sequence) throws InvalidSymbolException;

    /**
     * Replaces the contents of this sequence with the symbols contained in the
     * given byte array. If valid is true, the array contents in validated
     * against the current alphabet for this sequence, resulting in an
     * "InvalidSymbolException" being thrown if the array contains invalid
     * symbols. Setting validate to false overrides the validation check, and
     * assumes that the byte array data being presented has already been
     * validated. As validation is so fast, this typically results in only a 20%
     * savings in the execution time of this method, so this method should not
     * normally be used.
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
     * Gets the byte at the given index.
     *
     * @param index
     *            the index
     * @return the byte
     */
    default byte getByte(final int index) {
        return getByte((long) index);
    }

    /**
     * Gets the byte at the given index.
     *
     * @param index
     *            the index
     * @return the byte
     */
    byte getByte(long index);

    /**
     * Sets the byte at the given index.
     * 
     * @param index
     * @param symbol
     * @throws InvalidSymbolException
     */
    default void set(final int index, final byte symbol) throws InvalidSymbolException {
        set((long) index, symbol);
    }

    /**
     * Sets the byte at the given index.
     * 
     * @param index
     * @param symbol
     * @throws InvalidSymbolException
     */
    void set(long index, byte symbol) throws InvalidSymbolException;

    /**
     * Appends a new symbol to the end of the sequence.
     * 
     * @param symbol
     * @throws InvalidSymbolException
     */
    void add(final byte symbol) throws InvalidSymbolException;

    /**
     * @see java.lang.CharSequence#charAt(int)
     */
    @Override
    default char charAt(final int index) {
        return (char) getByte(index);
    }

    /**
     * @see java.lang.CharSequence#subSequence(int, int)
     */
    @Override
    default CharSequence subSequence(final int fromIndex, final int toIndex) {
        return subList(fromIndex, toIndex);
    }

    /**
     * @see java.lang.CharSequence#length()
     */
    @Override
    default int length() {
        return size();
    }

    /**
     * @see org.java0.collection.LongList#get(int)
     */
    @Override
    default Byte get(final int index) {
        return getByte(index);
    }

    /**
     * @see org.java0.collection.LongList#get(long)
     */
    @Override
    default Byte get(final long index) {
        return getByte(index);
    }

    /**
     * @see org.java0.collection.LongList#set(int, java.lang.Object)
     */
    @Override
    default Byte set(final int index, final Byte symbol) throws InvalidSymbolException {
        final Byte oldVal = get(index);
        set(index, symbol.byteValue());
        return oldVal;
    }

    /**
     * @see org.java0.collection.LongList#set(long, java.lang.Object)
     */
    @Override
    default Byte set(final long index, final Byte symbol) {
        final Byte oldVal = get(index);
        set(index, symbol.byteValue());
        return oldVal;
    }

    /**
     * @see org.java0.collection.LongList#add(long, java.lang.Object)
     */
    @Override
    default void add(final long index, final Byte value) {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.java0.collection.LongList#subList(long, long)
     */
    @Override
    ByteSeq<A> subList(long fromIndex, long toIndex);

    /**
     * @see org.java0.collection.LongList#subList(int, int)
     */
    @Override
    default ByteSeq<A> subList(final int fromIndex, final int toIndex) {
        return subList((long) fromIndex, (long) toIndex);
    }

    /**
     * @see org.java0.collection.DefaultList#iterator()
     */
    @Override
    default ByteSeqIterator iterator() {
        return listIterator(0L);
    }

    /**
     * @see org.java0.collection.DefaultList#listIterator()
     */
    @Override
    default ByteSeqIterator listIterator() {
        return listIterator(0L);
    }

    /**
     * @see org.java0.collection.LongList#listIterator(int)
     */
    @Override
    default ByteSeqIterator listIterator(final int index) {
        return listIterator((long) index);
    }

    /**
     * @see org.java0.collection.LongList#listIterator(long)
     */
    @Override
    ByteSeqIterator listIterator(long index);

}
