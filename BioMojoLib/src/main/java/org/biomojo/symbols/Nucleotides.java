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
public class Nucleotides extends CommonSymbols {
    private Nucleotides() {

    }

    public static final byte ADENINE = 'A';
    public static final byte CYTOSINE = 'C';
    public static final byte GUANINE = 'G';
    public static final byte THYMINE = 'T';
    public static final byte URACIL = 'U';

    public static final byte ANY = 'N';

    public static final byte A_OR_G = 'R';
    public static final byte PURINE = A_OR_G;

    public static final byte C_OR_T = 'Y';
    public static final byte PYRIMIDINE = C_OR_T;

    public static final byte A_OR_T = 'W';
    public static final byte WEAK = A_OR_T;

    public static final byte C_OR_G = 'S';
    public static final byte STRONG = C_OR_G;

    public static final byte A_OR_C = 'M';
    public static final byte AMINO = A_OR_C;

    public static final byte G_OR_T = 'K';
    public static final byte KETO = G_OR_T;

    public static final byte NOT_A = 'B';
    public static final byte NOT_T = 'V';
    public static final byte NOT_G = 'H';
    public static final byte NOT_C = 'D';

    public static final byte MASK = 'X';

    public static final byte[] AMBIGUITY_SYMBOLS = { ANY, A_OR_G, C_OR_T, C_OR_G, A_OR_T, G_OR_T, A_OR_C, NOT_A, NOT_C,
            NOT_G, NOT_T };

    public static final byte[] DNA_SYMBOLS = { ADENINE, CYTOSINE, GUANINE, THYMINE };
    public static final byte[] RNA_SYMBOLS = { ADENINE, CYTOSINE, GUANINE, URACIL };

    public static final byte[] CORE_SYMBOLS = { ADENINE, CYTOSINE, GUANINE, URACIL, THYMINE };

}
