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

import org.biomojo.alphabet.ByteAlphabet;

public class TwoBitByteLongCodec extends AbstractByteLongCodec {

    public TwoBitByteLongCodec(final int codecId) {
        super(codecId);
    }

    @Override
    public byte[] decode(final ByteAlphabet alphabet, final long[] encodedData, final int length) {
        final byte[] decodedData = new byte[length];

        return decodedData;
    }

    @Override
    public byte decode(final ByteAlphabet alphabet, final long[] encodedData, final int length, final int index) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long[] encode(final ByteAlphabet alphabet, final long[] encodedData, final int length,
            final byte[] decodedData) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void encode(final ByteAlphabet alphabet, final long[] encodedData, final int length, final byte symbol,
            final int index) {
        // TODO Auto-generated method stub
    }

    @Override
    public int blockSize(final int blockNum) {
        return 1;
    }

}
