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

package org.biomojo.util;

import java.io.IOException;
import java.io.OutputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class OutputUtil.
 */
public class OutputUtil {

    /**
     * Write split lines.
     *
     * @param outputStream the output stream
     * @param maxLineLength the max line length
     * @param data the data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeSplitLines(final OutputStream outputStream, final int maxLineLength, final byte[] data)
            throws IOException {
        final int size = data.length;
        for (int i = 0; i < size; i += maxLineLength) {
            if (size - i < maxLineLength) {
                outputStream.write(data, i, size - i);
            } else {
                outputStream.write(data, i, maxLineLength);
            }
            outputStream.write('\n');
        }
    }
}