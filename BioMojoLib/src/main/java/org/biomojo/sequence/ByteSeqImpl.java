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

import java.util.Arrays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.InvalidSymbolException;
import org.java0.core.type.Constants;

/**
 * @author Hugh Eaves
 *
 */
@Entity
@DiscriminatorValue("B")
public class ByteSeqImpl<A extends ByteAlphabet> extends AbstractByteSeq<A> {
	private static final long serialVersionUID = 1L;

	protected byte[] data = Constants.EMPTY_BYTE_ARRAY;

	protected CharSequence description = Constants.EMPTY_STRING;

	protected int length;

	public ByteSeqImpl() {

	}

	public ByteSeqImpl(final A alphabet) {
		super(alphabet);
	}

	public ByteSeqImpl(final byte[] data) {
		super();
		setAll(data);
	}

	public ByteSeqImpl(final byte[] data, final A alphabet) {
		super(alphabet);
		setAll(data);
	}

	public ByteSeqImpl(final int initialCapacity, final A alphabet) {
		super(alphabet);
		data = new byte[initialCapacity];
	}

	@Override
	public void setAll(final byte[] sequence) throws InvalidSymbolException {
		setAll(sequence, true);
	}

	@Override
	public byte[] getAllBytes() {
		if (data.length > length) {
			data = Arrays.copyOf(data, length);
		}
		return data;
	}

	@Override
	public void setAll(final byte[] sequence, final boolean validate)
			throws InvalidSymbolException {
		if (validate) {
			alphabet.validate(sequence);
		}
		length = sequence.length;
		data = sequence;
	}

	@Override
	public byte getValue(final int index) {
		return data[index];
	}

	@Override
	public void setValue(final byte symbol, final int index)
			throws InvalidSymbolException {
		alphabet.validate(symbol);
		data[index] = symbol;
	}

	@Override
	public int size() {
		return length;
	}

	@Override
	public CharSequence getDescription() {
		return description;
	}

	@Override
	public void setDescription(final CharSequence description) {
		this.description = description;
	}

	@Override
	public void setAlphabet(final A newAlphabet) {
		newAlphabet.validate(data);
		this.alphabet = newAlphabet;
	}

	/**
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

	@Override
	public void replace(final byte[] srcSeq, final int srcPos,
			final int destPos, final int length) {
		System.arraycopy(srcSeq, srcPos, data, destPos, length);
	}

	@Override
	public void append(final byte symbol) {
		checkStorage(1);
		data[length++] = symbol;
	}

	private final void checkStorage(final int numElements) {
		if (data.length < length + numElements) {
			data = Arrays.copyOf(data, (data.length + 1) * 2);
		}
	}
}
