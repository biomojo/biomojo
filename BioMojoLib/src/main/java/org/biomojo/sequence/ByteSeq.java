/*
 * Copyright (C) 2014  Hugh Eaves
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

public interface ByteSeq<A extends ByteAlphabet> extends Seq<Byte, A> {

	public byte[] getAllBytes();

	public void setAll(byte[] sequence) throws InvalidSymbolException;

	public void setAll(byte[] sequence, boolean validate)
			throws InvalidSymbolException;

	public byte getValue(int index);

	public void setValue(byte symbol, int index) throws InvalidSymbolException;

	public default Byte get(final int index) {
		return getValue(index);
	}

	public default Byte set(final int index, final Byte symbol)
			throws InvalidSymbolException {
		final byte oldVal = getValue(index);
		set(index, symbol);
		return oldVal;
	}

	public void replace(final byte[] srcSeq, final int srcPos,
			final int destPos, final int length);

	public void append(final byte symbol);
}
