package org.biomojo.io;

import java.util.Iterator;

import org.biomojo.sequence.Seq;

public class SequenceInputStreamIterator<T extends Seq<?, ?>> implements Iterator<T> {
    private final SequenceInputStream<T> inputStream;
    private T seq;

    public SequenceInputStreamIterator(final SequenceInputStream<T> inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public boolean hasNext() {
        seq = inputStream.read();
        if (seq != null) {
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        final T rVal = seq;
        seq = null;
        return rVal;
    }

}
