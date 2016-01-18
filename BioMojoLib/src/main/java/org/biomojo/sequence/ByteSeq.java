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
 * The Interface ByteSeq.
 *
 * @param <A>
 *            the generic type
 */
public interface ByteSeq<A extends ByteAlphabet> extends Seq<Byte, A>, CharSequence {

    /**
     * Gets the all bytes.
     *
     * @return the all bytes
     */
    public byte[] toByteArray();

    /**
     * Sets the all.
     *
     * @param sequence
     *            the new all
     * @throws InvalidSymbolException
     *             the invalid symbol exception
     */
    public void setAll(byte[] sequence) throws InvalidSymbolException;

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
    public void setAll(byte[] sequence, boolean validate) throws InvalidSymbolException;

    /**
     * Gets the value.
     *
     * @param index
     *            the index
     * @return the value
     */
    public byte getByte(int index);

    /**
     * Sets the value at the given position
     * 
     * @param index
     *            the index
     * @param symbol
     *            the symbol
     *
     * @throws InvalidSymbolException
     *             the invalid symbol exception
     */
    public void set(int index, byte symbol) throws InvalidSymbolException;

    @Override
    public default Byte get(final int index) {
        return getByte(index);
    }

    @Override
    public default Byte set(final int index, final Byte symbol) throws InvalidSymbolException {
        final byte oldVal = getByte(index);
        set(index, symbol);
        return oldVal;
    }

    /**
     * Append.
     *
     * @param symbol
     *            the symbol
     */
    public void append(final byte symbol);

    // Methods from CharSequence

    @Override
    public default char charAt(final int index) {
        return (char) getByte(index);
    }

    @Override
    public default CharSequence subSequence(final int start, final int end) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default int length() {
        return size();
    }

    // public ByteListIterator byteListIterator();
}
