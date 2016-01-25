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
package org.biomojo.sequence;

import org.java0.collection.DefaultListIterator;

public class EncodedByteSeqIterator extends DefaultListIterator<Byte> implements ByteListIterator {

    public EncodedByteSeqIterator(final EncodedByteSeq<?> target, final int index) {
        super(target, index);
    }

    @Override
    public byte nextByte() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public byte previousByte() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void set(final byte e) {

    }

    @Override
    public void add(final byte e) {
        // TODO Auto-generated method stub

    }

}
