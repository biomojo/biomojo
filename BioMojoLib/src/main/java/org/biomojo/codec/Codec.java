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
package org.biomojo.codec;

import java.util.List;

import org.biomojo.alphabet.Alphabet;
import org.java0.core.type.LongIdentified;

/**
 * A Codec provides a method to encode / decode data from one format to another.
 * For example, a codec could convert sequence data between a single byte
 * representation and a two-bit representation, or just compress / decompress
 * data using a variable length compression algorithm.
 *
 * @author Hugh Eaves
 * @param <D>
 *            the type of decoded data
 * @param <E>
 *            the type of encoded data
 */
public interface Codec<D, E> extends LongIdentified {

    /**
     * Checks to see if this Codec supports the given Alphabet.
     *
     * @param alphabet
     *            the alphabet
     * @return true, if the given alphabet is supported by this codec
     */
    boolean supportsAlphabet(Alphabet<D> alphabet);

    /**
     * Decodes all of the encoded data.
     * 
     * @param alphabet
     *            the Alphabet
     * @param encodedData
     *            the encoded data
     * @param decodedLength
     *            the expected length of the decoded data
     * @return
     */
    public List<D> decodeAll(Alphabet<D> alphabet, List<E> encodedData, int decodedLength);

    /**
     * Decodes the symbol at the specified position in the unencoded data..
     * 
     * @param Alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param decodedLength
     *            the expected length of the decoded data
     * @param pos
     *            the position of the element that should be decoded. (i.e. the
     *            position in the decoded data, not the encoded data)
     * @return
     */
    public D decode(Alphabet<D> alphabet, List<E> encodedData, int decodedLength, int pos);

    /**
     * @param alphabet
     * @param decodedData
     * @return
     */
    public List<E> encodeAll(Alphabet<D> alphabet, List<D> decodedData);

    /**
     * Replaces the symbol at the specified position with the new symbol.
     * 
     * @param alphabet
     * @param encodedData
     * @param symbol
     * @param pos
     */
    public void encode(Alphabet<D> alphabet, List<E> encodedData, D symbol, int pos);

    /**
     * Decodes the specified block number in the encode data
     * 
     * @param alphabet
     * @param encodedData
     * @param decodedBlock
     * @param blockNum
     * @return
     */
    public List<D> decodeBlock(Alphabet<D> alphabet, List<E> encodedData, List<D> decodedBlock, int blockNum);

    /**
     * Returns the size of the given block number
     * 
     * @param blockNum
     * @return
     */
    public int blockSize(int blockNum);

}
