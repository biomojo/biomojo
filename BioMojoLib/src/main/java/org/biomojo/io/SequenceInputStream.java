package org.biomojo.io;

import java.io.Closeable;

import org.biomojo.sequence.Seq;

/**
 * @author Hugh Eaves
 *
 * @param <T>
 */
public interface SequenceInputStream<T extends Seq<?, ?>> extends Closeable {
	/**
	 * Reads data from the InputStream into a {@link org.biomojo.sequence.Seq}
	 * object. Returns true if the data was read successfully, false on end of
	 * data.
	 *
	 * @param sequence
	 * @return
	 * @throws ParseException
	 */
	public boolean read(T sequence) throws ParseException;
}
