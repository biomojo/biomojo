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
