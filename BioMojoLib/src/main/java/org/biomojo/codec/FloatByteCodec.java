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

/**
 * The Interface FloatByteCodec.
 *
 * @author Hugh Eaves
 */
public interface FloatByteCodec extends Codec<Float, Byte> {

    /**
     * Decode.
     *
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @return the float[]
     */
    public float[] decode(byte[] encodedData, int length);

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
     */
    public float decode(byte[] encodedData, int length, int index);

    /**
     * Encode.
     *
     * @param decodedData
     *            the decoded data
     * @return the byte[]
     */
    public byte[] encode(float[] decodedData);

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
     */
    public void encode(byte[] encodedData, int length, float value, int index);
}
