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

package org.biomojo;

import java.util.function.Function;

public class GlobalConst {
    /** The Constant DEFAULT_BUFFER_SIZE. */
    public static final int DEFAULT_BUFFER_SIZE;
    public static final float GC_RATIO;
    public static final boolean DEBUG_MEMORY;
    public static final boolean VALIDATE_INPUT_SEQS;
    public static final String LIB_SPRING_CONTEXT = "biomojolib-context.xml";

    private static final String PROPERTY_PREFIX = "biomojo.";

    static {
        DEFAULT_BUFFER_SIZE = getProperty("readBufferSize", Integer::parseInt, 0xFFFF);
        GC_RATIO = getProperty("gcRatio", Float::parseFloat, (float) -1);
        DEBUG_MEMORY = getProperty("debugMemory", Boolean::parseBoolean, false);
        VALIDATE_INPUT_SEQS = getProperty("validateInputSeqs", Boolean::parseBoolean, true);
    }

    static <T> T getProperty(final String key, final Function<String, T> converter, final T defaultValue) {
        T value = defaultValue;
        final String property = System.getProperty(PROPERTY_PREFIX + key);
        if (property != null) {
            try {
                value = converter.apply(property);
            } catch (final NumberFormatException e) {
            }
        }
        return value;
    }
}
