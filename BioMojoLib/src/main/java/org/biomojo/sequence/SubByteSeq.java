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

public class SubByteSeq<A extends ByteAlphabet> extends SubSeq<Byte, A, ByteSeq<A>> implements ByteSeq<A> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SubByteSeq(final ByteSeq<A> target, final long start, final long end) {
        super(target, start, end);
    }

    /**
     * To byte array.
     *
     * @return the byte[]
     */
    @Override
    public byte[] toByteArray() {
        return target.toByteArray(start, end);

    }

    @Override
    public byte[] toByteArray(final long from, final long to) {
        return target.toByteArray(start + from, start + from + to);
    }

    /**
     * Sets the all.
     *
     * @param sequence
     *            the new all
     * @throws InvalidSymbolException
     *             the invalid symbol exception
     */
    @Override
    public void setAll(final byte[] sequence) throws InvalidSymbolException {
        throw new UnsupportedOperationException();
    }

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
    @Override
    public void setAll(final byte[] sequence, final boolean validate) throws InvalidSymbolException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void set(final long index, final byte element) throws InvalidSymbolException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void add(final byte symbol) throws InvalidSymbolException {
        throw new UnsupportedOperationException();

    }

    /**
     * Gets the byte.
     *
     * @param index
     *            the index
     * @return the byte
     */
    @Override
    public byte getByte(final long index) {
        return target.getByte(start + index);
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
