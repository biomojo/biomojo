/*
 * Copyright (C) 2014  Hugh Eaves
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
import java.io.IOException;

import org.biomojo.sequence.Seq;

// TODO: Auto-generated Javadoc
/**
 * The Interface SequenceOutputStream.
 *
 * @author Hugh Eaves
 * @param <T> the generic type
 */
public interface SequenceOutputStream<T extends Seq<?, ?>> extends Closeable {
    
    /**
     * Writes data from the given {@link org.biomojo.sequence.Seq} object to the
     * output stream.
     *
     * @param sequence the sequence
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void write(T sequence) throws IOException;
}
