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
package org.biomojo.codon;

import org.biomojo.core.IntegerIdentified;

// TODO: Auto-generated Javadoc
/**
 * The Interface CodonTable.
 *
 * @author Hugh Eaves
 */
public interface CodonTable extends IntegerIdentified {

    /**
     * Gets the amino acid.
     *
     * @param nucleotides the nucleotides
     * @param offset the offset
     * @return the amino acid
     */
    byte getAminoAcid(byte[] nucleotides, int offset);

    /**
     * Gets the amino acid.
     *
     * @param nucleotides the nucleotides
     * @return the amino acid
     */
    byte getAminoAcid(byte[] nucleotides);

    /**
     * Gets the amino acid.
     *
     * @param nucleotide1 the nucleotide1
     * @param nucleotide2 the nucleotide2
     * @param nucleotide3 the nucleotide3
     * @return the amino acid
     */
    byte getAminoAcid(byte nucleotide1, byte nucleotide2, byte nucleotide3);
}
