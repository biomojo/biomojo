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

// TODO: Auto-generated Javadoc
/**
 * The Class TranslatedSeq.
 *
 * @author Hugh Eaves
 */
public class TranslatedSeq extends AbstractByteSeq<AminoAcidAlphabet> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The codon table. */
    protected CodonTable codonTable;

    /** The seq. */
    protected ByteSeq<NucleotideAlphabet> seq;

    /**
     * Create a new TranslatedSeq.
     */
    public TranslatedSeq() {
        super(Alphabets.getAlphabet(AlphabetId.AMINO_ACID, AminoAcidAlphabet.class));
        codonTable = CodonTables.getCodonTable(CodonTableId.STANDARD);
    }

    /**
     * Create a new TranslatedSeq.
     *
     * @param sequence the sequence
     */
    public TranslatedSeq(final ByteSeqImpl<NucleotideAlphabet> sequence) {
        this();
        this.seq = sequence;
    }

    /**
     * Create a new TranslatedSeq.
     *
     * @param sequence the sequence
     * @param alphabet the alphabet
     */
    public TranslatedSeq(final ByteSeqImpl<NucleotideAlphabet> sequence, final AminoAcidAlphabet alphabet) {
        super(alphabet);
        codonTable = CodonTables.getCodonTable(CodonTableId.STANDARD);
        this.seq = sequence;
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#setAll(byte[])
     */
    @Override
    public void setAll(final byte[] sequence) {
        setAll(sequence, true);
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#getAllBytes()
     */
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

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#getValue(int)
     */
    @Override
    public byte getValue(final int index) {
        return codonTable.getAminoAcid(seq.getValue(index * 3), seq.getValue(index * 3 + 1),
                seq.getValue(index * 3 + 2));
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#setAll(byte[], boolean)
     */
    @Override
    public void setAll(final byte[] sequence, final boolean validate) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#setValue(byte, int)
     */
    @Override
    public void setValue(final byte value, final int pos) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.Seq#size()
     */
    @Override
    public int size() {
        return seq.size() / 3;
    }

    /* (non-Javadoc)
     * @see org.biomojo.core.Described#getDescription()
     */
    @Override
    public CharSequence getDescription() {
        return seq.getDescription();
    }

    /* (non-Javadoc)
     * @see org.biomojo.core.Described#setDescription(java.lang.CharSequence)
     */
    @Override
    public void setDescription(final CharSequence description) {
        seq.setDescription(description);
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.Seq#canonicalize()
     */
    @Override
    public void canonicalize() {
        seq.canonicalize();
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#replace(byte[], int, int, int)
     */
    @Override
    public void replace(final byte[] srcSeq, final int srcPos, final int destPos, final int length) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.Seq#setAlphabet(org.biomojo.alphabet.Alphabet)
     */
    @Override
    public void setAlphabet(final AminoAcidAlphabet alphabet) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.ByteSeq#append(byte)
     */
    @Override
    public void append(final byte symbol) {
        throw new UnsupportedOperationException();
    }

}
