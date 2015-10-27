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
 * The Class AminoAcids.
 *
 * @author Hugh Eaves
 */
public class AminoAcids extends CommonSymbols {
    
    /**
     * Instantiates a new amino acids.
     */
    private AminoAcids() {

    }

    /** The Constant ALANINE. */
    public static final byte ALANINE = 'A';
    
    /** The Constant CYSTEINE. */
    public static final byte CYSTEINE = 'C';
    
    /** The Constant ASPARTIC_ACID. */
    public static final byte ASPARTIC_ACID = 'D';
    
    /** The Constant GLUTAMIC_ACID. */
    public static final byte GLUTAMIC_ACID = 'E';
    
    /** The Constant PHENYLALANINE. */
    public static final byte PHENYLALANINE = 'F';
    
    /** The Constant GLYCINE. */
    public static final byte GLYCINE = 'G';
    
    /** The Constant HISTIDINE. */
    public static final byte HISTIDINE = 'H';
    
    /** The Constant ISOLEUCINE. */
    public static final byte ISOLEUCINE = 'I';
    
    /** The Constant LYSINE. */
    public static final byte LYSINE = 'K';
    
    /** The Constant LEUCINE. */
    public static final byte LEUCINE = 'L';
    
    /** The Constant METHIONINE. */
    public static final byte METHIONINE = 'M';
    
    /** The Constant ASPARAGINE. */
    public static final byte ASPARAGINE = 'N';
    
    /** The Constant PROLINE. */
    public static final byte PROLINE = 'P';
    
    /** The Constant GLUTAMINE. */
    public static final byte GLUTAMINE = 'Q';
    
    /** The Constant ARGININE. */
    public static final byte ARGININE = 'R';
    
    /** The Constant SERINE. */
    public static final byte SERINE = 'S';
    
    /** The Constant THREONINE. */
    public static final byte THREONINE = 'T';
    
    /** The Constant VALINE. */
    public static final byte VALINE = 'V';
    
    /** The Constant TRYPTOPHAN. */
    public static final byte TRYPTOPHAN = 'W';
    
    /** The Constant TYROSINE. */
    public static final byte TYROSINE = 'Y';

    /** The Constant ASPARTIC_ACID_OR_ASPARAGINE. */
    public static final byte ASPARTIC_ACID_OR_ASPARAGINE = 'B';
    
    /** The Constant GLUTAMIC_ACID_OR_GLUTAMINE. */
    public static final byte GLUTAMIC_ACID_OR_GLUTAMINE = 'Z';
    
    /** The Constant ANY. */
    public static final byte ANY = 'X';

    /** The Constant STOP. */
    public static final byte STOP = '*';

    /** The Constant CORE_SYMBOLS. */
    public static final byte[] CORE_SYMBOLS = { ALANINE, CYSTEINE, ASPARTIC_ACID, GLUTAMIC_ACID, PHENYLALANINE, GLYCINE,
            HISTIDINE, ISOLEUCINE, LYSINE, LEUCINE, METHIONINE, ASPARAGINE, PROLINE, GLUTAMINE, ARGININE, SERINE,
            THREONINE, VALINE, TRYPTOPHAN, TYROSINE, STOP };

    /** The Constant AMBIGUITY_SYMBOLS. */
    public static final byte[] AMBIGUITY_SYMBOLS = { ANY, ASPARTIC_ACID_OR_ASPARAGINE, GLUTAMIC_ACID_OR_GLUTAMINE };

}
