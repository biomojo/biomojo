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
package org.biomojo.sequence;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.AminoAcidAlphabet;
import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.codon.CodonTable;
import org.biomojo.codon.CodonTableId;
import org.biomojo.codon.CodonTables;

/**
 * @author Hugh Eaves
 *
 */
public class TranslatedSeq extends AbstractByteSeq<AminoAcidAlphabet> {
	private static final long serialVersionUID = 1L;

	protected CodonTable codonTable;

	protected ByteSeq<NucleotideAlphabet> seq;

	/**
	 * Create a new TranslatedSeq.
	 *
	 * @param byteAlphabet
	 */
	public TranslatedSeq() {
		super(Alphabets.getAlphabet(AlphabetId.AMINO_ACID,
				AminoAcidAlphabet.class));
		codonTable = CodonTables.getCodonTable(CodonTableId.STANDARD);
	}

	/**
	 * Create a new TranslatedSeq.
	 *
	 * @param sequence
	 */
	public TranslatedSeq(final ByteSeqImpl<NucleotideAlphabet> sequence) {
		this();
		this.seq = sequence;
	}

	/**
	 * Create a new TranslatedSeq.
	 *
	 * @param sequence
	 * @param alphabet
	 */
	public TranslatedSeq(final ByteSeqImpl<NucleotideAlphabet> sequence,
			final AminoAcidAlphabet alphabet) {
		super(alphabet);
		codonTable = CodonTables.getCodonTable(CodonTableId.STANDARD);
		this.seq = sequence;
	}

	@Override
	public void setAll(final byte[] sequence) {
		setAll(sequence, true);
	}

	@Override
	public byte[] getAllBytes() {
		final byte[] nucleotides = seq.getAllBytes();
		final byte[] aminoAcids = new byte[size()];
		int j = 0;
		for (int i = 0; i < nucleotides.length - 2; i += 3) {
			aminoAcids[j++] = codonTable.getAminoAcid(nucleotides, i);
		}
		return aminoAcids;
	}

	@Override
	public byte getValue(final int index) {
		return codonTable.getAminoAcid(seq.getValue(index * 3),
				seq.getValue(index * 3 + 1), seq.getValue(index * 3 + 2));
	}

	@Override
	public void setAll(final byte[] sequence, final boolean validate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue(final byte value, final int pos) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return seq.size() / 3;
	}

	@Override
	public CharSequence getDescription() {
		return seq.getDescription();
	}

	@Override
	public void setDescription(final CharSequence description) {
		seq.setDescription(description);
	}

	@Override
	public void canonicalize() {
		seq.canonicalize();
	}

	@Override
	public void replace(final byte[] srcSeq, final int srcPos,
			final int destPos, final int length) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAlphabet(final AminoAcidAlphabet alphabet) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void append(final byte symbol) {
		throw new UnsupportedOperationException();
	}

}
