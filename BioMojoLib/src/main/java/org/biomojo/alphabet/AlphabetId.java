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
package org.biomojo.alphabet;

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

    /** The Constant UNKNOWN. */
    public static final int UNKNOWN = 0;

    /** The Constant ALL_BYTE. */
    public static final int ALL_BYTE = 32;

    /** The Constant NUCLEOTIDE. */
    public static final int NUCLEOTIDE = 64;

    /** The Constant DNA. */
    public static final int DNA = 96;

    /** The Constant RNA. */
    public static final int RNA = 128;

    /** The Constant AMINO_ACID. */
    public static final int AMINO_ACID = 160;

    /** The Constant QUALITY_SANGER. */
    public static final int QUALITY_SANGER = 192;
    
    /** The Constant QUALITY_ILLUMINA_10. */
    public static final int QUALITY_ILLUMINA_10 = 193;
    
    /** The Constant QUALITY_ILLUMINA_13. */
    public static final int QUALITY_ILLUMINA_13 = 194;

    /** The Constant ASCII. */
    public static final int ASCII = 224;

    /** The Constant LAST_ALPHABET_ID. */
    public static final int LAST_ALPHABET_ID = ASCII;

}
