package org.biomojo.sequence;

import java.util.ListIterator;

public interface SeqIterator<T> extends ListIterator<T> {

    public long nextIndexL();

    public long previousIndexL();

    /**
     * @see java.util.ListIterator#nextIndex()
     */
    @Override
    public default int nextIndex() {
        final long index = nextIndexL();
        return (index > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) index);
    }

    /**
     * @see java.util.ListIterator#previousIndex()
     */
    @Override
    public default int previousIndex() {
        final long index = previousIndexL();
        return (index > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) index);
    }
}
