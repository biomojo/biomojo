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
package org.biomojo.io;

import java.io.Closeable;

import org.biomojo.sequence.Seq;

/**
 * The Interface SequenceInputStream.
 *
 * @author Hugh Eaves
 * @param <T>
 *            the generic type
 */
public interface SequenceInputStream<T extends Seq<?, ?>> extends Closeable {

    /**
     * Reads data from the InputStream into a {@link org.biomojo.sequence.Seq}
     * object. Returns true if the data was read successfully, false on end of
     * data.
     *
     * @param sequence
     *            the sequence
     * @return true, if successful
     * @throws ParseException
     *             the parse exception
     */
    public boolean read(T sequence) throws ParseException;

    /**
     * Reads data from the InputStream into a new
     * {@link org.biomojo.sequence.Seq} object, and returns that object. Returns
     * null on end of data.
     *
     * @param sequence
     *            the sequence
     * @return the Sequence object, null on end of data
     * @throws ParseException
     *             the parse exception
     */
    public T readSeq() throws ParseException;
}
