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

import java.util.Arrays;

import org.biomojo.symbols.AminoAcids;
import org.biomojo.symbols.Nucleotides;

/**
 * @author Hugh Eaves
 *
 */
public class CodonTableImpl implements CodonTable {
	int id;
	private byte[] aminoAcidLookupTable;
	private boolean[] startCodonLookupTable;

	private static final int TABLE_SIZE = (Byte.MAX_VALUE + 1)
			* (Byte.MAX_VALUE + 1) * (Byte.MAX_VALUE + 1);

	CodonTableImpl(int id, String aminoAcids, String startCodons) {
		this.id = id;
		char BASES[] = { Nucleotides.URACIL, Nucleotides.CYTOSINE,
				Nucleotides.ADENINE, Nucleotides.GUANINE };
		aminoAcidLookupTable = new byte[TABLE_SIZE];
		Arrays.fill(aminoAcidLookupTable, AminoAcids.ANY);
		startCodonLookupTable = new boolean[TABLE_SIZE];
		int strPos = 0;
		for (int base1 = 0; base1 < BASES.length; ++base1) {
			for (int base2 = 0; base2 < BASES.length; ++base2) {
				for (int base3 = 0; base3 < BASES.length; ++base3) {
					addCodon(BASES[base1], BASES[base2], BASES[base3],
							(byte) aminoAcids.charAt(strPos),
							startCodons.charAt(strPos) == 'M');
					strPos++;
				}
			}
		}
	}

	/**
	 * @see org.biomojo.codon.CodonTable#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	private int index(char base1, char base2, char base3) {
		return (base1 * (Byte.MAX_VALUE + 1) * (Byte.MAX_VALUE + 1) + base2
				* (Byte.MAX_VALUE + 1) + base3);
	}

	private int index(byte base1, byte base2, byte base3) {
		return (base1 * (Byte.MAX_VALUE + 1) * (Byte.MAX_VALUE + 1) + base2
				* (Byte.MAX_VALUE + 1) + base3);
	}

	private void addCodon(char base1u, char base2u, char base3u,
			byte aminoAcid, boolean startCodon) {
		char bases1[] = getAlternates(base1u);
		char bases2[] = getAlternates(base2u);
		char bases3[] = getAlternates(base3u);
		for (char base1 : bases1) {
			for (char base2 : bases2) {
				for (char base3 : bases3) {
					aminoAcidLookupTable[index(base1, base2, base3)] = aminoAcid;
					startCodonLookupTable[index(base1, base2, base3)] = startCodon;
				}
			}
		}
	}

	private char[] getAlternates(char base) {
		if (base == Nucleotides.URACIL) {
			return new char[] { base, Character.toLowerCase(base),
					Nucleotides.THYMINE,
					Character.toLowerCase((char) Nucleotides.THYMINE) };
		} else {
			return new char[] { base, Character.toLowerCase(base) };
		}
	}

	@Override
	public byte getAminoAcid(byte[] nucleotides, int offset) {
		return aminoAcidLookupTable[index(nucleotides[offset],
				nucleotides[offset + 1], nucleotides[offset + 2])];

	}

	@Override
	public byte getAminoAcid(byte[] nucleotides) {
		return aminoAcidLookupTable[index(nucleotides[0], nucleotides[1],
				nucleotides[2])];
	}

	@Override
	public byte getAminoAcid(byte nucleotide1, byte nucleotide2,
			byte nucleotide3) {
		return aminoAcidLookupTable[index(nucleotide1, nucleotide2, nucleotide3)];
	}
}
