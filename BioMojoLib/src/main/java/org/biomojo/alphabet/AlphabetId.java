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
package org.biomojo.alphabet;

import org.biomojo.GlobalConst;

// TODO: Auto-generated Javadoc
/**
 * The Class AlphabetId.
 *
 * @author Hugh Eaves
 */
public class AlphabetId {

    /**
     * Instantiates a new alphabet id.
     */
    private AlphabetId() {

    }

    public static final long START = GlobalConst.GROUP_ID * 1L;

    /** The Constant ALL_BYTE. */
    public static final long ALL_BYTE = START + 32;

    public static final long ASCII_UPPERCASE = START + 34;
    public static final long ASCII = START + 35;

    public static final long LETTERS_UPPERCASE = START + 36;
    public static final long LETTERS = START + 37;

    /** The Constant NUCLEOTIDE. */
    public static final long NUCLEOTIDE = START + 64;

    /** The Constant DNA. */
    public static final long DNA = START + 96;

    /** The Constant RNA. */
    public static final long RNA = START + 128;

    /** The Constant AMINO_ACID. */
    public static final long AMINO_ACID = START + 160;

    public static final long QUALITY_PHRED = START + 192;

    /** The Constant QUALITY_SANGER. */
    public static final long QUALITY_SANGER = START + 193;

    /** The Constant QUALITY_ILLUMINA_10. */
    public static final long QUALITY_ILLUMINA_10 = START + 194;

    /** The Constant QUALITY_ILLUMINA_13. */
    public static final long QUALITY_ILLUMINA_13 = START + 195;

    /** The Constant LAST_ALPHABET_ID. */
    public static final long LAST_ALPHABET_ID = START + 195;

}
