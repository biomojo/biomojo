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
package org.biomojo.codec;

import org.biomojo.alphabet.Alphabet;
import org.biomojo.alphabet.ByteAlphabet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FourBitByteCodec extends AbstractByteCodec {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(FourBitByteCodec.class.getName());

	FourBitByteCodec(final int id) {
		super(id);
	}

	private static final int BITS_PER_SYMBOL = 4;
	private static final int SYMBOLS_PER_BYTE = 8 / BITS_PER_SYMBOL;
	private static final int NUM_SYMBOLS = 1 << BITS_PER_SYMBOL;

	private static final int MASK_0 = 0xF0;
	private static final int MASK_1 = 0x0F;
	private static final int SHIFT_0 = 4;

	/**
	 * @see org.biomojo.codec.ByteCodec#decode(byte[])
	 */
	@Override
	public byte[] decode(final ByteAlphabet alphabet, final byte[] encodedData,
			final int length) {
		final byte[] decodedData = new byte[length];
		final int endPos = length - length % SYMBOLS_PER_BYTE;
		int decodedPos = 0;
		int encodedPos = 0;
		// decode full bytes
		while (decodedPos < endPos) {
			final int encodedByte = encodedData[encodedPos++];
			decodedData[decodedPos++] = alphabet
					.getByteSymbolForOrdinal((encodedByte & MASK_0) >> SHIFT_0);
			decodedData[decodedPos++] = alphabet
					.getByteSymbolForOrdinal(encodedByte & MASK_1);
		}
		// decode partial byte at the end (if any)
		if (decodedPos < length) {
			decodedData[decodedPos] = alphabet
					.getByteSymbolForOrdinal((encodedData[encodedPos] & MASK_0) >> SHIFT_0);
		}
		return decodedData;
	}

	/**
	 * @see org.biomojo.codec.ByteCodec#decode(byte[], int)
	 */
	@Override
	public byte decode(final ByteAlphabet alphabet, final byte[] encodedData,
			final int length, final int pos) {
		final int bytePos = pos / SYMBOLS_PER_BYTE;
		if (pos % SYMBOLS_PER_BYTE == 0) {
			return alphabet
					.getByteSymbolForOrdinal((encodedData[bytePos] & MASK_0) >> SHIFT_0);
		} else {
			return alphabet.getByteSymbolForOrdinal(encodedData[bytePos]
					& MASK_1);
		}
	}

	/**
	 * @see org.biomojo.codec.ByteCodec#encode(byte[])
	 */
	@Override
	public byte[] encode(final ByteAlphabet alphabet,
			final byte[] oldEncodedData, final int length,
			final byte[] decodedData) {
		final byte[] encodedData = new byte[(length + SYMBOLS_PER_BYTE - 1)
				/ SYMBOLS_PER_BYTE];
		final int endPos = length - length % SYMBOLS_PER_BYTE;
		;
		int decodedPos = 0;
		int encodedPos = 0;
		// encode full bytes
		while (decodedPos < endPos) {
			encodedData[encodedPos++] = (byte) ((alphabet
					.getOrdinalForSymbol(decodedData[decodedPos++]) << SHIFT_0) | (alphabet
					.getOrdinalForSymbol(decodedData[decodedPos++])));
		}
		// encode partial byte at the end (if any)
		if (decodedPos < length) {
			encodedData[encodedPos] = (byte) (alphabet
					.getOrdinalForSymbol(decodedData[decodedPos]) << SHIFT_0);
		}
		return encodedData;
	}

	/**
	 * @see org.biomojo.codec.ByteCodec#encode(byte[], byte, int)
	 */
	@Override
	public void encode(final ByteAlphabet alphabet, final byte[] encodedData,
			final int length, final byte symbol, final int pos) {
		final int bytePos = pos / SYMBOLS_PER_BYTE;
		if (pos % SYMBOLS_PER_BYTE == 0) {
			encodedData[bytePos] = (byte) (encodedData[bytePos] & ~MASK_0 | (alphabet
					.getOrdinalForSymbol(symbol) << SHIFT_0));
		} else {
			encodedData[bytePos] = (byte) (encodedData[bytePos] & ~MASK_1 | (alphabet
					.getOrdinalForSymbol(symbol)));
		}
	}

	/**
	 * @see org.biomojo.codec.Codec#supportsAlphabet(org.biomojo.alphabet.Alphabet)
	 */
	@Override
	public boolean supportsAlphabet(final Alphabet<Byte> alphabet) {
		return (alphabet.numSymbols() <= NUM_SYMBOLS);
	}
}
