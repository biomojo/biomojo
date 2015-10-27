package org.biomojo.io;

import java.io.Closeable;
import java.io.IOException;

import org.biomojo.sequence.Seq;

/**
 * @author Hugh Eaves
 *
 * @param <T>
 */
public interface SequenceOutputStream<T extends Seq<?, ?>> extends Closeable {
    /**
     * Writes data from the given {@link org.biomojo.sequence.Seq} object to the
     * output stream.
     *
     * @param sequence
     * @throws IOException
     */
    public void write(T sequence) throws IOException;
}
