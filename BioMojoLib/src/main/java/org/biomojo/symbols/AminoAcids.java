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
package org.biomojo.symbols;

/**
 * @author Hugh Eaves
 *
 */
public class AminoAcids extends CommonSymbols {
    private AminoAcids() {

    }

    public static final byte ALANINE = 'A';
    public static final byte CYSTEINE = 'C';
    public static final byte ASPARTIC_ACID = 'D';
    public static final byte GLUTAMIC_ACID = 'E';
    public static final byte PHENYLALANINE = 'F';
    public static final byte GLYCINE = 'G';
    public static final byte HISTIDINE = 'H';
    public static final byte ISOLEUCINE = 'I';
    public static final byte LYSINE = 'K';
    public static final byte LEUCINE = 'L';
    public static final byte METHIONINE = 'M';
    public static final byte ASPARAGINE = 'N';
    public static final byte PROLINE = 'P';
    public static final byte GLUTAMINE = 'Q';
    public static final byte ARGININE = 'R';
    public static final byte SERINE = 'S';
    public static final byte THREONINE = 'T';
    public static final byte VALINE = 'V';
    public static final byte TRYPTOPHAN = 'W';
    public static final byte TYROSINE = 'Y';

    public static final byte ASPARTIC_ACID_OR_ASPARAGINE = 'B';
    public static final byte GLUTAMIC_ACID_OR_GLUTAMINE = 'Z';
    public static final byte ANY = 'X';

    public static final byte STOP = '*';

    public static final byte[] CORE_SYMBOLS = { ALANINE, CYSTEINE, ASPARTIC_ACID, GLUTAMIC_ACID, PHENYLALANINE, GLYCINE,
            HISTIDINE, ISOLEUCINE, LYSINE, LEUCINE, METHIONINE, ASPARAGINE, PROLINE, GLUTAMINE, ARGININE, SERINE,
            THREONINE, VALINE, TRYPTOPHAN, TYROSINE, STOP };

    public static final byte[] AMBIGUITY_SYMBOLS = { ANY, ASPARTIC_ACID_OR_ASPARAGINE, GLUTAMIC_ACID_OR_GLUTAMINE };

}
