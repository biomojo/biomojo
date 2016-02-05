package org.biomojo.sequence;

public class DefaultSeqIterator<T, S extends Seq<T, ?>> implements SeqIterator<T> {

    /** The target List. */
    protected S target;

    /** The current position. */
    protected long currentPosition = 0;

    /** The active element. */
    protected long activeElement = -1;

    public DefaultSeqIterator(final S target, final long index) {
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
    public T next() {
        final T next = target.get(currentPosition);
        activeElement = currentPosition;
        currentPosition = currentPosition + 1;
        return next;
    }

    /**
     * @see java.util.ListIterator#set(java.lang.Object)
     */
    @Override
    public void set(final T e) {
        if (activeElement < 0) {
            throw new IllegalStateException();
        }
        target.set(activeElement, e);
    }

    /**
     * @see java.util.ListIterator#add(java.lang.Object)
     */
    @Override
    public void add(final T e) {
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
    public T previous() {
        currentPosition = currentPosition - 1;
        final T previous = target.get(currentPosition);
        activeElement = currentPosition;
        return previous;

    }

    @Override
    public long nextIndexL() {
        return currentPosition;
    }

    @Override
    public long previousIndexL() {
        return currentPosition - 1;
    }

}
