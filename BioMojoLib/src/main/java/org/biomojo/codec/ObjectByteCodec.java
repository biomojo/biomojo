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

/**
 * @author Hugh Eaves
 *
 */
public interface ObjectByteCodec<T> extends Codec<T, Byte> {
    /**
     * Decode all the data in the sequence.
     *
     * @param seq
     *            the seq
     * @return the d[]
     */
    public T[] decode(Alphabet<T> alphabet, byte[] encodedData, int length);

    /**
     * Decode a single position in the sequence.
     */
    public T decode(Alphabet<T> alphabet, byte[] encodedData, int length, int pos);

    /**
     * Encode a single value, replacing the value at the given position.
     *
     */
    public void encode(Alphabet<T> alphabet, byte[] encodedData, T symbol, int pos);

    /**
     * Encode all the data into the sequence, replacing any existing data.
     *
     */
    public byte[] encode(Alphabet<T> alphabet, T[] decodedData);
}
