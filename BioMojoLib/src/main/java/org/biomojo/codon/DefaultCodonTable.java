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
package org.biomojo.codon;

import java.util.Arrays;

import org.biomojo.symbols.AminoAcids;
import org.biomojo.symbols.Nucleotides;
import org.java0.factory.LifecycleEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class CodonTableImpl.
 *
 * @author Hugh Eaves
 */
public class DefaultCodonTable implements CodonTable {

    /** The id. */
    int id;

    /** The amino acid lookup table. */
    private final byte[] aminoAcidLookupTable;

    /** The start codon lookup table. */
    private final boolean[] startCodonLookupTable;

    /** The Constant TABLE_SIZE. */
    private static final int TABLE_SIZE = (Byte.MAX_VALUE + 1) * (Byte.MAX_VALUE + 1) * (Byte.MAX_VALUE + 1);

    /**
     * Instantiates a new codon table impl.
     *
     * @param id
     *            the id
     * @param aminoAcids
     *            the amino acids
     * @param startCodons
     *            the start codons
     */
    DefaultCodonTable(final int id, final String aminoAcids, final String startCodons) {
        this.id = id;
        final char BASES[] = { Nucleotides.URACIL, Nucleotides.CYTOSINE, Nucleotides.ADENINE, Nucleotides.GUANINE };
        aminoAcidLookupTable = new byte[TABLE_SIZE];
        Arrays.fill(aminoAcidLookupTable, AminoAcids.ANY);
        startCodonLookupTable = new boolean[TABLE_SIZE];
        int strPos = 0;
        for (int base1 = 0; base1 < BASES.length; ++base1) {
            for (int base2 = 0; base2 < BASES.length; ++base2) {
                for (int base3 = 0; base3 < BASES.length; ++base3) {
                    addCodon(BASES[base1], BASES[base2], BASES[base3], (byte) aminoAcids.charAt(strPos),
                            startCodons.charAt(strPos) == 'M');
                    strPos++;
                }
            }
        }
    }

    /**
     * Gets the id.
     *
     * @return the id
     * @see org.biomojo.codon.CodonTable#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Index.
     *
     * @param base1
     *            the base1
     * @param base2
     *            the base2
     * @param base3
     *            the base3
     * @return the int
     */
    private int index(final char base1, final char base2, final char base3) {
        return (base1 * (Byte.MAX_VALUE + 1) * (Byte.MAX_VALUE + 1) + base2 * (Byte.MAX_VALUE + 1) + base3);
    }

    /**
     * Index.
     *
     * @param base1
     *            the base1
     * @param base2
     *            the base2
     * @param base3
     *            the base3
     * @return the int
     */
    private int index(final byte base1, final byte base2, final byte base3) {
        return (base1 * (Byte.MAX_VALUE + 1) * (Byte.MAX_VALUE + 1) + base2 * (Byte.MAX_VALUE + 1) + base3);
    }

    /**
     * Adds the codon.
     *
     * @param base1u
     *            the base1u
     * @param base2u
     *            the base2u
     * @param base3u
     *            the base3u
     * @param aminoAcid
     *            the amino acid
     * @param startCodon
     *            the start codon
     */
    private void addCodon(final char base1u, final char base2u, final char base3u, final byte aminoAcid,
            final boolean startCodon) {
        final char bases1[] = getAlternates(base1u);
        final char bases2[] = getAlternates(base2u);
        final char bases3[] = getAlternates(base3u);
        for (final char base1 : bases1) {
            for (final char base2 : bases2) {
                for (final char base3 : bases3) {
                    aminoAcidLookupTable[index(base1, base2, base3)] = aminoAcid;
                    startCodonLookupTable[index(base1, base2, base3)] = startCodon;
                }
            }
        }
    }

    /**
     * Gets the alternates.
     *
     * @param base
     *            the base
     * @return the alternates
     */
    private char[] getAlternates(final char base) {
        if (base == Nucleotides.URACIL) {
            return new char[] { base, Character.toLowerCase(base), Nucleotides.THYMINE,
                    Character.toLowerCase((char) Nucleotides.THYMINE) };
        } else {
            return new char[] { base, Character.toLowerCase(base) };
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.codon.CodonTable#getAminoAcid(byte[], int)
     */
    @Override
    public byte getAminoAcid(final byte[] nucleotides, final int offset) {
        return aminoAcidLookupTable[index(nucleotides[offset], nucleotides[offset + 1], nucleotides[offset + 2])];

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.codon.CodonTable#getAminoAcid(byte[])
     */
    @Override
    public byte getAminoAcid(final byte[] nucleotides) {
        return aminoAcidLookupTable[index(nucleotides[0], nucleotides[1], nucleotides[2])];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.codon.CodonTable#getAminoAcid(byte, byte, byte)
     */
    @Override
    public byte getAminoAcid(final byte nucleotide1, final byte nucleotide2, final byte nucleotide3) {
        return aminoAcidLookupTable[index(nucleotide1, nucleotide2, nucleotide3)];
    }

    @Override
    public void notify(final LifecycleEvent event) {

    }
}
