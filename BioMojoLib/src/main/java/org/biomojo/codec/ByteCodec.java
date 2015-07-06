package org.biomojo.codec;

import org.biomojo.alphabet.ByteAlphabet;

public interface ByteCodec extends ObjectByteCodec<Byte> {

	/**
	 * Decode all the data in the sequence.
	 *
	 * @param seq
	 *            the seq
	 * @return the d[]
	 */
	public byte[] decode(ByteAlphabet alphabet, byte[] encodedData, int length);

	/**
	 * Decode a single position in the sequence.
	 */
	public byte decode(ByteAlphabet alphabet, byte[] encodedData, int length,
			int index);

	/**
	 * Encode all the data into the sequence, replacing any existing data.
	 *
	 */
	public byte[] encode(ByteAlphabet alphabet, byte[] encodedData, int length,
			byte[] decodedData);

	/**
	 * Encode a single value, replacing the value at the given position.
	 *
	 */
	public void encode(ByteAlphabet alphabet, byte[] encodedData, int length,
			byte symbol, int index);

}
