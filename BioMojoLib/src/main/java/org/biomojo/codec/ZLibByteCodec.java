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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.biomojo.alphabet.Alphabet;
import org.biomojo.alphabet.ByteAlphabet;

import com.jcraft.jzlib.DeflaterOutputStream;
import com.jcraft.jzlib.InflaterInputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class ZLibByteCodec.
 */
public class ZLibByteCodec extends AbstractByteByteCodec implements StatefulByteCodec {

    /** The uncompressed data cache. */
    private transient volatile byte[] uncompressedDataCache;

    /** The dirty. */
    private boolean dirty = false;

    /**
     * Instantiates a new z lib byte codec.
     *
     * @param id
     *            the id
     */
    public ZLibByteCodec(final int id) {
        super(id);
    }

    /**
     * Gets the uncompressed data.
     *
     * @param compressedData
     *            the compressed data
     * @param uncompressedLength
     *            the uncompressed length
     * @return the uncompressed data
     */
    protected byte[] getUncompressedData(final byte[] compressedData, final long uncompressedLength) {
        byte[] data = uncompressedDataCache;

        if (data == null) {
            synchronized (this) {
                data = uncompressedDataCache;
                if (data == null) {
                    data = uncompressedDataCache = uncompress(compressedData, uncompressedLength);
                }
            }
        }
        return data;
    }

    /**
     * Uncompress.
     *
     * @param compressedData
     *            the compressed data
     * @param uncompressedLength
     *            the uncompressed length
     * @return the byte[]
     */
    protected byte[] uncompress(final byte[] compressedData, final long uncompressedLength) {
        final byte[] uncompressedData = new byte[(int) uncompressedLength];
        try {
            final ByteArrayInputStream input = new ByteArrayInputStream(compressedData);
            final InflaterInputStream decompressor = new InflaterInputStream(input);
            decompressor.read(uncompressedData);
            decompressor.close();
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
        return uncompressedData;
    }

    /**
     * Compress.
     *
     * @param uncompressedData
     *            the uncompressed data
     * @return the byte[]
     */
    protected byte[] compress(final byte[] uncompressedData) {
        try {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final DeflaterOutputStream compressor = new DeflaterOutputStream(out);
            compressor.write(uncompressedData);
            compressor.close();
            return out.toByteArray();
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Decode.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param length
     *            the length
     * @return the byte[]
     * @see org.biomojo.codec.ByteCodec#decode(byte[])
     */
    @Override
    public byte[] decodeAll(final ByteAlphabet alphabet, final byte[] encodedData, final int length) {
        return getUncompressedData(encodedData, length);
    }

    /**
     * Decode.
     *
     * @param alphabet
     *            the alphabet
     * @param encodedData
     *            the encoded data
     * @param index
     *            the index
     * @return the byte
     * @see org.biomojo.codec.ByteCodec#decode(byte[], int)
     */
    @Override
    public byte decode(final ByteAlphabet alphabet, final byte[] encodedData, final int decodedLength,
            final int index) {
        return getUncompressedData(encodedData, decodedLength)[index];
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.biomojo.codec.ByteCodec#encode(org.biomojo.alphabet.ByteAlphabet,
     * byte[], int, byte, int)
     */
    @Override
    public void encode(final ByteAlphabet alphabet, final byte[] encodedData, final int length, final byte val,
            final int index) {
        getUncompressedData(encodedData, length)[index] = val;
        dirty = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.biomojo.codec.ByteCodec#encode(org.biomojo.alphabet.ByteAlphabet,
     * byte[], int, byte[])
     */
    @Override
    public byte[] encode(final ByteAlphabet alphabet, final byte[] encodedData, final int length,
            final byte[] decodedData) {
        final byte[] compressedData = compress(decodedData);
        synchronized (this) {
            uncompressedDataCache = null;
            return compressedData;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.biomojo.codec.AbstractCodec#supportsAlphabet(org.biomojo.alphabet.
     * Alphabet)
     */
    @Override
    public boolean supportsAlphabet(final Alphabet<Byte> alphabet) {
        return (true);
    }

    /**
     * Checks if is dirty data.
     *
     * @return true, if is dirty data
     * @see org.biomojo.codec.StatefulCodec#isChanged()
     */
    @Override
    public boolean isDirtyData() {
        return dirty;
    }

    /**
     * Gets the encoded data.
     *
     * @param alphabet
     *            the alphabet
     * @return the encoded data
     * @see org.biomojo.codec.StatefulCodec#getEncodedData()
     */
    @Override
    public byte[] getEncodedData(final ByteAlphabet alphabet) {
        return encode(alphabet, null, uncompressedDataCache.length, uncompressedDataCache);
    }

    @Override
    public byte[] decodeBlock(final ByteAlphabet alphabet, final byte[] encodedData, final byte[] decodedBlock,
            final int blockNum) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int blockSize(final int blockNum) {
        return 1;
    }
}
