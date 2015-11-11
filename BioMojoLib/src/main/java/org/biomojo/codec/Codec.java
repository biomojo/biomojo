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

import org.biomojo.alphabet.Alphabet;
import org.biomojo.core.IntegerIdentified;

// TODO: Auto-generated Javadoc
/**
 * A Codec provides a method to encode / decode data from one format to another.
 * For example, a codec could convert sequence data between a single byte
 * representation and a two-bit representation, or just compress / decompress
 * data using a compression algorithm.
 *
 * @author Hugh Eaves
 * @param <D>
 *            the decoded type
 * @param <E>
 *            the encoded type
 */
public interface Codec<D, E> extends IntegerIdentified {

    /**
     * Supports alphabet.
     *
     * @param alphabet
     *            the alphabet
     * @return true, if the given alphabet is supported by this codec
     */
    boolean supportsAlphabet(Alphabet<D> alphabet);

}
