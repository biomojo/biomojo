package org.biomojo.sequence;

import java.util.ListIterator;

public interface ByteListIterator extends ListIterator<Byte> {
    public byte nextByte();

    public byte previousByte();

    void set(byte e);

    void add(byte e);
}
