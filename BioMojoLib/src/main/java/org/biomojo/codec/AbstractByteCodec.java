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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractByteCodec.
 *
 * @author Hugh Eaves
 */
public abstract class AbstractByteCodec extends AbstractCodec<Byte, Byte> implements ByteCodec {
    
    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractByteCodec.class.getName());

    /**
     * Create a new AbstractByteCodec.
     *
     * @param codecId the codec id
     */
    public AbstractByteCodec(int codecId) {
        super(codecId);
    }

    /**
     * Decode all the data in the sequence.
     *
     * @param alphabet the alphabet
     * @param encodedData the encoded data
     * @param length the length
     * @return the d[]
     */
    @Override
    public Byte[] decode(Alphabet<Byte> alphabet, byte[] encodedData, int length) {
        throw new UnsupportedOperationException();
    }

    /**
     * Decode a single position in the sequence.
     *
     * @param alphabet the alphabet
     * @param encodedData the encoded data
     * @param length the length
     * @param pos the pos
     * @return the byte
     */
    @Override
    public Byte decode(Alphabet<Byte> alphabet, byte[] encodedData, int length, int pos) {
        throw new UnsupportedOperationException();
    }

    /**
     * Encode a single value, replacing the value at the given position.
     *
     * @param alphabet the alphabet
     * @param encodedData the encoded data
     * @param symbol the symbol
     * @param pos the pos
     */
    @Override
    public void encode(Alphabet<Byte> alphabet, byte[] encodedData, Byte symbol, int pos) {
        throw new UnsupportedOperationException();
    }

    /**
     * Encode all the data into the sequence, replacing any existing data.
     *
     * @param alphabet the alphabet
     * @param decodedData the decoded data
     * @return the byte[]
     */
    @Override
    public byte[] encode(Alphabet<Byte> alphabet, Byte[] decodedData) {
        throw new UnsupportedOperationException();
    }
}
