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

import java.nio.ByteBuffer;

/**
 * @author Hugh Eaves
 *
 */
public class DefaultFloatCodec extends AbstractCodec<Float, Byte> implements FloatCodec {

    DefaultFloatCodec(int id) {
        super(id);
    }

    /**
     * @see org.biomojo.codec.FloatCodec#decode(byte[], int)
     */
    @Override
    public float[] decode(byte[] encodedData, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(encodedData);
        float[] decodedData = new float[length];
        int pos = 0;
        while (buffer.position() < buffer.limit()) {
            decodedData[pos++] = buffer.getFloat();
        }
        return decodedData;
    }

    /**
     * @see org.biomojo.codec.FloatCodec#decode(byte[], int, int)
     */
    @Override
    public float decode(byte[] encodedData, int length, int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.biomojo.codec.FloatCodec#encode(float[])
     */
    @Override
    public byte[] encode(float[] decodedData) {
        byte[] byteBuf = new byte[decodedData.length * 4];
        ByteBuffer buffer = ByteBuffer.wrap(byteBuf);
        for (float f : decodedData) {
            buffer.putFloat(f);
        }
        return byteBuf;
    }

    /**
     * @see org.biomojo.codec.FloatCodec#encode(byte[], int, float, int)
     */
    @Override
    public void encode(byte[] encodedData, int length, float value, int index) {
        throw new UnsupportedOperationException();
    }
}
