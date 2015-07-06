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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.codec.ByteCodec;
import org.biomojo.codec.CodecId;
import org.biomojo.codec.Codecs;

/**
 * @author Hugh Eaves
 *
 */
@Entity
@DiscriminatorValue("E")
public class EncodedByteSeq<A extends ByteAlphabet> extends ByteSeqImpl<A> {
	private static final long serialVersionUID = 1L;

	protected ByteCodec codec;

	public EncodedByteSeq() {
		super();
		setCodec(Codecs.getCodec(CodecId.NULL_BYTE_CODEC));
	}

	public EncodedByteSeq(final byte[] data, final A alphabet) {
		super(data, alphabet);
		setCodec(Codecs.getCodec(CodecId.NULL_BYTE_CODEC));
	}

	public EncodedByteSeq(final byte[] data, final A alphabet,
			final ByteCodec codec) {
		super(data, alphabet);
		setCodec(codec);
	}

	public EncodedByteSeq(final byte[] data) {
		super(data);
		setCodec(Codecs.getCodec(CodecId.NULL_BYTE_CODEC));
	}

	public EncodedByteSeq(final A alphabet) {
		super(alphabet);
		setCodec(Codecs.getCodec(CodecId.NULL_BYTE_CODEC));
	}

	public EncodedByteSeq(final A alphabet, final ByteCodec codec) {
		super(alphabet);
		setCodec(codec);
	}

	@Override
	public byte[] getAllBytes() {
		return codec.decode(getAlphabet(), data, length);
	}

	@Override
	public void setAll(final byte[] sequence, final boolean validate) {
		if (validate) {
			alphabet.validate(sequence);
		}
		this.length = sequence.length;
		data = codec.encode(getAlphabet(), data, sequence.length, sequence);
	}

	@Override
	public byte getValue(final int index) {
		return codec.decode(getAlphabet(), data, length, index);
	}

	@Override
	public void setValue(final byte value, final int index) {
		alphabet.validate(value);
		codec.encode(getAlphabet(), data, value, index);
	}

	/**
	 * @return the codec
	 */
	public ByteCodec getCodec() {
		return codec;
	}

	/**
	 * @param codec
	 *            the codec to set
	 */
	public void setCodec(final ByteCodec codec) {
		if (codec.supportsAlphabet(getAlphabet())) {
			this.codec = codec;
		} else {
			throw new IllegalArgumentException(
					"Codec does not support alphabet");
		}
	}

	@Override
	public void replace(final byte[] srcSeq, final int srcPos,
			final int destPos, final int length) {
		throw new UnsupportedOperationException();
	}
}