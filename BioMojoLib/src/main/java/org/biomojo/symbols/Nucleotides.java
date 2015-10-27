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

// TODO: Auto-generated Javadoc
/**
 * The Class Nucleotides.
 *
 * @author Hugh Eaves
 */
public class Nucleotides extends CommonSymbols {
    
    /**
     * Instantiates a new nucleotides.
     */
    private Nucleotides() {

    }

    /** The Constant ADENINE. */
    public static final byte ADENINE = 'A';
    
    /** The Constant CYTOSINE. */
    public static final byte CYTOSINE = 'C';
    
    /** The Constant GUANINE. */
    public static final byte GUANINE = 'G';
    
    /** The Constant THYMINE. */
    public static final byte THYMINE = 'T';
    
    /** The Constant URACIL. */
    public static final byte URACIL = 'U';

    /** The Constant ANY. */
    public static final byte ANY = 'N';

    /** The Constant A_OR_G. */
    public static final byte A_OR_G = 'R';
    
    /** The Constant PURINE. */
    public static final byte PURINE = A_OR_G;

    /** The Constant C_OR_T. */
    public static final byte C_OR_T = 'Y';
    
    /** The Constant PYRIMIDINE. */
    public static final byte PYRIMIDINE = C_OR_T;

    /** The Constant A_OR_T. */
    public static final byte A_OR_T = 'W';
    
    /** The Constant WEAK. */
    public static final byte WEAK = A_OR_T;

    /** The Constant C_OR_G. */
    public static final byte C_OR_G = 'S';
    
    /** The Constant STRONG. */
    public static final byte STRONG = C_OR_G;

    /** The Constant A_OR_C. */
    public static final byte A_OR_C = 'M';
    
    /** The Constant AMINO. */
    public static final byte AMINO = A_OR_C;

    /** The Constant G_OR_T. */
    public static final byte G_OR_T = 'K';
    
    /** The Constant KETO. */
    public static final byte KETO = G_OR_T;

    /** The Constant NOT_A. */
    public static final byte NOT_A = 'B';
    
    /** The Constant NOT_T. */
    public static final byte NOT_T = 'V';
    
    /** The Constant NOT_G. */
    public static final byte NOT_G = 'H';
    
    /** The Constant NOT_C. */
    public static final byte NOT_C = 'D';

    /** The Constant MASK. */
    public static final byte MASK = 'X';

    /** The Constant AMBIGUITY_SYMBOLS. */
    public static final byte[] AMBIGUITY_SYMBOLS = { ANY, A_OR_G, C_OR_T, C_OR_G, A_OR_T, G_OR_T, A_OR_C, NOT_A, NOT_C,
            NOT_G, NOT_T };

    /** The Constant DNA_SYMBOLS. */
    public static final byte[] DNA_SYMBOLS = { ADENINE, CYTOSINE, GUANINE, THYMINE };
    
    /** The Constant RNA_SYMBOLS. */
    public static final byte[] RNA_SYMBOLS = { ADENINE, CYTOSINE, GUANINE, URACIL };

    /** The Constant CORE_SYMBOLS. */
    public static final byte[] CORE_SYMBOLS = { ADENINE, CYTOSINE, GUANINE, URACIL, THYMINE };

}
