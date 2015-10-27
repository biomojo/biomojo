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

import org.biomojo.symbols.Nucleotides;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractNucleotideAlphabet.
 */
public abstract class AbstractNucleotideAlphabet extends AbstractIUPACAlphabet implements NucleotideAlphabet {
    
    /** The complement. */
    protected byte[] complement = new byte[Byte.MAX_VALUE + 1];
    
    /** The base matches. */
    private static boolean[] baseMatches = new boolean[(Byte.MAX_VALUE + 1) * (Byte.MAX_VALUE + 1)];

    /**
     * Instantiates a new abstract nucleotide alphabet.
     *
     * @param id the id
     * @param coreSymbols the core symbols
     */
    protected AbstractNucleotideAlphabet(final int id, final byte[] coreSymbols) {
        super(id, coreSymbols);
        initComplements();
    }

    /* (non-Javadoc)
     * @see org.biomojo.alphabet.AbstractIUPACAlphabet#addAnySymbols()
     */
    @Override
    protected void addAnySymbols() {
        addSymbol(Nucleotides.ANY);
    }

    /* (non-Javadoc)
     * @see org.biomojo.alphabet.AbstractIUPACAlphabet#addAmbiguitySymbols()
     */
    @Override
    protected void addAmbiguitySymbols() {
        addSymbols(Nucleotides.AMBIGUITY_SYMBOLS);
    }

    /**
     * Inits the complements.
     */
    protected void initComplements() {
        for (int i = 0; i <= Byte.MAX_VALUE; ++i) {
            complement[i] = (byte) i;
        }

        addComplement(Nucleotides.CYTOSINE, Nucleotides.GUANINE);

        // start by mapping A->U and U->A
        addComplement(Nucleotides.ADENINE, Nucleotides.URACIL);

        // If we have DNA (Thymine), map A->T and T->A
        if (valid[Nucleotides.THYMINE]) {
            addComplement(Nucleotides.ADENINE, Nucleotides.THYMINE);
        }

        addComplement(Nucleotides.NOT_A, Nucleotides.NOT_T);
        addComplement(Nucleotides.NOT_G, Nucleotides.NOT_C);

        addComplement(Nucleotides.G_OR_T, Nucleotides.A_OR_C);
        addComplement(Nucleotides.A_OR_G, Nucleotides.C_OR_T);
        addComplement(Nucleotides.A_OR_T, Nucleotides.C_OR_G);
    }

    /**
     * Adds the complement.
     *
     * @param a the a
     * @param b the b
     */
    protected void addComplement(final byte a, final byte b) {
        complement[a] = b;
        complement[b] = a;
    }

    /**
     * Adds the match.
     *
     * @param c the c
     * @param d the d
     */
    private static void addMatch(final byte c, final byte d) {
        baseMatches[(c << 7) + d] = true;
        baseMatches[(d << 7) + c] = true;
    }

    static {
        addMatch(Nucleotides.ADENINE, Nucleotides.ADENINE);
        addMatch(Nucleotides.ADENINE, Nucleotides.ANY);

        addMatch(Nucleotides.CYTOSINE, Nucleotides.CYTOSINE);
        addMatch(Nucleotides.CYTOSINE, Nucleotides.ANY);

        addMatch(Nucleotides.GUANINE, Nucleotides.GUANINE);
        addMatch(Nucleotides.GUANINE, Nucleotides.ANY);

        addMatch(Nucleotides.THYMINE, Nucleotides.THYMINE);
        addMatch(Nucleotides.THYMINE, Nucleotides.ANY);

        addMatch(Nucleotides.URACIL, Nucleotides.URACIL);
        addMatch(Nucleotides.URACIL, Nucleotides.ANY);

        addMatch(Nucleotides.A_OR_T, Nucleotides.A_OR_T);
        addMatch(Nucleotides.A_OR_T, Nucleotides.ADENINE);
        addMatch(Nucleotides.A_OR_T, Nucleotides.THYMINE);
        addMatch(Nucleotides.A_OR_T, Nucleotides.ANY);

        addMatch(Nucleotides.C_OR_G, Nucleotides.C_OR_G);
        addMatch(Nucleotides.C_OR_G, Nucleotides.CYTOSINE);
        addMatch(Nucleotides.C_OR_G, Nucleotides.GUANINE);
        addMatch(Nucleotides.C_OR_G, Nucleotides.ANY);

        addMatch(Nucleotides.A_OR_C, Nucleotides.A_OR_C);
        addMatch(Nucleotides.A_OR_C, Nucleotides.ADENINE);
        addMatch(Nucleotides.A_OR_C, Nucleotides.CYTOSINE);
        addMatch(Nucleotides.A_OR_C, Nucleotides.ANY);

        addMatch(Nucleotides.G_OR_T, Nucleotides.G_OR_T);
        addMatch(Nucleotides.G_OR_T, Nucleotides.GUANINE);
        addMatch(Nucleotides.G_OR_T, Nucleotides.THYMINE);
        addMatch(Nucleotides.G_OR_T, Nucleotides.ANY);

        addMatch(Nucleotides.A_OR_G, Nucleotides.A_OR_G);
        addMatch(Nucleotides.A_OR_G, Nucleotides.ADENINE);
        addMatch(Nucleotides.A_OR_G, Nucleotides.GUANINE);
        addMatch(Nucleotides.A_OR_G, Nucleotides.ANY);

        addMatch(Nucleotides.C_OR_T, Nucleotides.C_OR_T);
        addMatch(Nucleotides.C_OR_T, Nucleotides.CYTOSINE);
        addMatch(Nucleotides.C_OR_T, Nucleotides.THYMINE);
        addMatch(Nucleotides.C_OR_T, Nucleotides.ANY);

        addMatch(Nucleotides.NOT_A, Nucleotides.NOT_A);
        addMatch(Nucleotides.NOT_A, Nucleotides.CYTOSINE);
        addMatch(Nucleotides.NOT_A, Nucleotides.GUANINE);
        addMatch(Nucleotides.NOT_A, Nucleotides.THYMINE);
        addMatch(Nucleotides.NOT_A, Nucleotides.ANY);

        addMatch(Nucleotides.NOT_C, Nucleotides.NOT_C);
        addMatch(Nucleotides.NOT_C, Nucleotides.ADENINE);
        addMatch(Nucleotides.NOT_C, Nucleotides.GUANINE);
        addMatch(Nucleotides.NOT_C, Nucleotides.THYMINE);
        addMatch(Nucleotides.NOT_C, Nucleotides.ANY);

        addMatch(Nucleotides.NOT_G, Nucleotides.NOT_G);
        addMatch(Nucleotides.NOT_G, Nucleotides.ADENINE);
        addMatch(Nucleotides.NOT_G, Nucleotides.CYTOSINE);
        addMatch(Nucleotides.NOT_G, Nucleotides.THYMINE);
        addMatch(Nucleotides.NOT_G, Nucleotides.ANY);

        addMatch(Nucleotides.NOT_T, Nucleotides.NOT_T);
        addMatch(Nucleotides.NOT_T, Nucleotides.ADENINE);
        addMatch(Nucleotides.NOT_T, Nucleotides.CYTOSINE);
        addMatch(Nucleotides.NOT_T, Nucleotides.GUANINE);
        addMatch(Nucleotides.NOT_T, Nucleotides.ANY);

        addMatch(Nucleotides.ANY, Nucleotides.ANY);
    }
}
