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

/**
 * @author Hugh Eaves
 *
 */
public class AlphabetId {
    private AlphabetId() {

    }

    public static final int UNKNOWN = 0;

    public static final int ALL_BYTE = 32;

    public static final int NUCLEOTIDE = 64;

    public static final int DNA = 96;

    public static final int RNA = 128;

    public static final int AMINO_ACID = 160;

    public static final int QUALITY_SANGER = 192;
    public static final int QUALITY_ILLUMINA_10 = 193;
    public static final int QUALITY_ILLUMINA_13 = 194;

    public static final int ASCII = 224;

    public static final int LAST_ALPHABET_ID = ASCII;

}
