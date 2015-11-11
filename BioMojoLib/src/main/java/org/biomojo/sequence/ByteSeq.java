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
 * @param <A> the generic type
 */
public interface ByteSeq<A extends ByteAlphabet> extends Seq<Byte, A> {

    /**
     * Gets the all bytes.
     *
     * @return the all bytes
     */
    public byte[] getAllBytes();

    /**
     * Sets the all.
     *
     * @param sequence the new all
     * @throws InvalidSymbolException the invalid symbol exception
     */
    public void setAll(byte[] sequence) throws InvalidSymbolException;

    /**
     * Sets the all.
     *
     * @param sequence the sequence
     * @param validate the validate
     * @throws InvalidSymbolException the invalid symbol exception
     */
    public void setAll(byte[] sequence, boolean validate) throws InvalidSymbolException;

    /**
     * Gets the value.
     *
     * @param index the index
     * @return the value
     */
    public byte getValue(int index);

    /**
     * Sets the value.
     *
     * @param symbol the symbol
     * @param index the index
     * @throws InvalidSymbolException the invalid symbol exception
     */
    public void setValue(byte symbol, int index) throws InvalidSymbolException;

    /* (non-Javadoc)
     * @see org.biomojo.sequence.Seq#get(int)
     */
    public default Byte get(final int index) {
        return getValue(index);
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.Seq#set(int, java.lang.Object)
     */
    public default Byte set(final int index, final Byte symbol) throws InvalidSymbolException {
        final byte oldVal = getValue(index);
        set(index, symbol);
        return oldVal;
    }

    /**
     * Replace.
     *
     * @param srcSeq the src seq
     * @param srcPos the src pos
     * @param destPos the dest pos
     * @param length the length
     */
    public void replace(final byte[] srcSeq, final int srcPos, final int destPos, final int length);

    /**
     * Append.
     *
     * @param symbol the symbol
     */
    public void append(final byte symbol);
}
