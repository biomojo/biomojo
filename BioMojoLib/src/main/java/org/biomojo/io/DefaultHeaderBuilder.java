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

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.biomojo.sequence.Seq;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultHeaderBuilder.
 */
public class DefaultHeaderBuilder implements HeaderBuilder {

    /* (non-Javadoc)
     * @see org.biomojo.io.HeaderBuilder#buildHeader(org.biomojo.sequence.Seq)
     */
    @Override
    public byte[] buildHeader(final Seq<?, ?> sequence) {
        final ByteBuffer buffer = Charset.forName("US-ASCII").encode(CharBuffer.wrap(sequence.getDescription()));
        return Arrays.copyOfRange(buffer.array(), buffer.arrayOffset(), buffer.arrayOffset() + buffer.limit());
    }

}
