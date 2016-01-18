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

import java.nio.ByteBuffer;

/**
 * The Class DefaultFloatCodec.
 *
 * @author Hugh Eaves
 */
public class DefaultFloatByteCodec extends AbstractCodec<Float, Byte> implements FloatByteCodec {

    /**
     * Instantiates a new default float codec.
     *
     * @param id
     *            the id
     */
    DefaultFloatByteCodec(final int id) {
        super(id);
    }

    /**
     * Decode.
     *
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @return the float[]
     * @see org.biomojo.codec.FloatCodec#decode(byte[], int)
     */
    @Override
    public float[] decode(final byte[] encodedData, final int length) {
        final ByteBuffer buffer = ByteBuffer.wrap(encodedData);
        final float[] decodedData = new float[length];
        int pos = 0;
        while (buffer.position() < buffer.limit()) {
            decodedData[pos++] = buffer.getFloat();
        }
        return decodedData;
    }

    /**
     * Decode.
     *
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @param index
     *            the index
     * @return the float
     * @see org.biomojo.codec.FloatCodec#decode(byte[], int, int)
     */
    @Override
    public float decode(final byte[] encodedData, final int length, final int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * Encode.
     *
     * @param decodedData
     *            the decoded data
     * @return the byte[]
     * @see org.biomojo.codec.FloatCodec#encode(float[])
     */
    @Override
    public byte[] encode(final float[] decodedData) {
        final byte[] byteBuf = new byte[decodedData.length * 4];
        final ByteBuffer buffer = ByteBuffer.wrap(byteBuf);
        for (final float f : decodedData) {
            buffer.putFloat(f);
        }
        return byteBuf;
    }

    /**
     * Encode.
     *
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @param value
     *            the value
     * @param index
     *            the index
     * @see org.biomojo.codec.FloatCodec#encode(byte[], int, float, int)
     */
    @Override
    public void encode(final byte[] encodedData, final int length, final float value, final int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int blockSize(final int blockNum) {
        return 1;
    }
}
