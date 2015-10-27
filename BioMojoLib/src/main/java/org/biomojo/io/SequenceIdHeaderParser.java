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

import org.biomojo.sequence.Seq;
import org.java0.string.ByteArrayConverter;

// TODO: Auto-generated Javadoc
/**
 * The Class SequenceIdHeaderParser.
 *
 * @author Hugh Eaves
 */
public class SequenceIdHeaderParser implements HeaderParser {
    
    /**
     * Parses the header.
     *
     * @param sequence the sequence
     * @param headerData the header data
     * @see org.biomojo.io.HeaderParser#parseHeader(org.biomojo.sequence.SeqI,
     *      byte[])
     */
    @Override
    public void parseHeader(final Seq<?, ?> sequence, final byte[] headerData) {
        sequence.setId(ByteArrayConverter.toLong(headerData));
    }
}
