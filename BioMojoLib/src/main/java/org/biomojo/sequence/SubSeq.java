package org.biomojo.sequence;

import org.biomojo.alphabet.Alphabet;

public class SubSeq<T, A extends Alphabet<T>, S extends Seq<T, A>> extends AbstractSeq<T, A> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected final S target;

    protected final long start;
    protected final long end;

    public SubSeq(final S target, final long start, final long end) {
        this.target = target;
        this.start = start;
        this.end = end;
    }

    @Override
    public A getAlphabet() {
        return target.getAlphabet();
    }

    @Override
    public void setAlphabet(final A alphabet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void canonicalize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final long index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(final long index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(final long index) {
        return target.get(start + index);
    }

    @Override
    public long sizeL() {
        return start - end;
    }

    @Override
    public void setDescription(final CharSequence description) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharSequence getDescription() {
        return target.getDescription();
    }

    @Override
    public SeqIterator<T> listIterator(final long index) {
        return new DefaultSeqIterator<T, SubSeq<T, A, S>>(this, index);
    }

}
