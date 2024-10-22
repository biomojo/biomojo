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
package org.biomojo.codec;

import org.biomojo.GlobalConst;

// TODO: Auto-generated Javadoc
/**
 * The Interface CodecId.
 *
 * @author Hugh Eaves
 */
public interface CodecId {

    public static final long START = GlobalConst.GROUP_ID * 2L;

    /** The Constant NULL_BYTE_CODEC. */
    public static final long NULL_BYTE_CODEC = START + 64;

    /** The Constant TWO_BIT_BYTE_CODEC. */
    public static final long TWO_BIT_BYTE_CODEC = START + 128;

    /** The Constant THREE_BIT_BYTE_CODEC. */
    public static final long THREE_BIT_BYTE_CODEC = START + 129;

    /** The Constant FOUR_BIT_BYTE_CODEC. */
    public static final long FOUR_BIT_BYTE_CODEC = START + 130;

    /** The Constant FIVE_BIT_BYTE_CODEC. */
    public static final long FIVE_BIT_BYTE_CODEC = START + 131;

    /** The Constant ZLIB_BYTE_CODEC. */
    public static final long ZLIB_BYTE_CODEC = START + 192;

    public static final long TWO_BIT_LONG_CODEC = START + 256;
}