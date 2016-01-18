package org.biomojo.sequence;

public class AbstractByteListIterator implements ByteListIterator {

    /** The target List. */
    protected ByteSeq<?> target;

    /** The current position. */
    protected int currentPosition = 0;

    /** The active element. */
    protected int activeElement = -1;

    public AbstractByteListIterator(final ByteSeq<?> target, final int index) {
        this.target = target;
        currentPosition = index;
    }

    /**
     * @see java.util.ListIterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return currentPosition != target.size();
    }

    /**
     * @see java.util.ListIterator#next()
     */
    @Override
    public byte nextByte() {
        final byte next = target.get(currentPosition);
        activeElement = currentPosition;
        currentPosition = currentPosition + 1;
        return next;
    }

    /**
     * @see java.util.ListIterator#set(java.lang.Object)
     */
    @Override
    public void set(final byte e) {
        if (activeElement < 0) {
            throw new IllegalStateException();
        }
        target.set(activeElement, e);
    }

    /**
     * @see java.util.ListIterator#add(java.lang.Object)
     */
    @Override
    public void add(final byte e) {
        target.add(currentPosition, e);
        activeElement = -1;
        currentPosition = currentPosition + 1;
    }

    /**
     * @see java.util.ListIterator#remove()
     */
    @Override
    public void remove() {
        if (activeElement < 0) {
            throw new IllegalStateException();
        }
        target.remove(activeElement);
        if (activeElement < currentPosition)
            currentPosition--;
        activeElement = -1;
    }

    /**
     * @see java.util.ListIterator#hasPrevious()
     */
    @Override
    public boolean hasPrevious() {
        return currentPosition != 0;
    }

    /**
     * @see java.util.ListIterator#previous()
     */
    @Override
    public byte previousByte() {
        currentPosition = currentPosition - 1;
        final byte previous = target.get(currentPosition);
        activeElement = currentPosition;
        return previous;

    }

    /**
     * @see java.util.ListIterator#nextIndex()
     */
    @Override
    public int nextIndex() {
        return currentPosition;
    }

    /**
     * @see java.util.ListIterator#previousIndex()
     */
    @Override
    public int previousIndex() {
        return currentPosition - 1;
    }

    @Override
    public Byte next() {
        return nextByte();
    }

    @Override
    public Byte previous() {
        return previousByte();
    }

    @Override
    public void set(final Byte e) {
        set(e.byteValue());
    }

    @Override
    public void add(final Byte e) {
        add(e.byteValue());
    }
}