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
package org.biomojo.sequence;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.alphabet.QualityScoreAlphabet;
import org.biomojo.codec.ByteByteCodec;

// TODO: Auto-generated Javadoc
/**
 * The Class EncodedFastqSeq.
 *
 * @author Hugh Eaves
 * @param <A> the generic type
 */
@Entity
@DiscriminatorValue("G")
public class EncodedFastqSeq<A extends NucleotideAlphabet> extends EncodedByteSeq<A> implements FastqSeq<A> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The seq. */
    @OneToOne(targetEntity = ByteSeqImpl.class)
    protected ByteSeq<QualityScoreAlphabet> seq;

    /**
     * Instantiates a new encoded fastq seq.
     */
    public EncodedFastqSeq() {
        super();
        seq = new ByteSeqImpl<QualityScoreAlphabet>(
                Alphabets.getAlphabet(AlphabetId.QUALITY_SANGER, QualityScoreAlphabet.class));

    }

    /**
     * Instantiates a new encoded fastq seq.
     *
     * @param data the data
     * @param alphabet the alphabet
     * @param codec the codec
     */
    public EncodedFastqSeq(final byte[] data, final A alphabet, final ByteByteCodec codec) {
        super(data, alphabet, codec);
        seq = new ByteSeqImpl<QualityScoreAlphabet>(
                Alphabets.getAlphabet(AlphabetId.QUALITY_SANGER, QualityScoreAlphabet.class));

    }

    /**
     * Instantiates a new encoded fastq seq.
     *
     * @param data the data
     * @param alphabet the alphabet
     */
    public EncodedFastqSeq(final byte[] data, final A alphabet) {
        super(data, alphabet);
        seq = new ByteSeqImpl<QualityScoreAlphabet>(
                Alphabets.getAlphabet(AlphabetId.QUALITY_SANGER, QualityScoreAlphabet.class));

    }

    /**
     * Instantiates a new encoded fastq seq.
     *
     * @param data the data
     */
    public EncodedFastqSeq(final byte[] data) {
        super(data);
        seq = new ByteSeqImpl<QualityScoreAlphabet>(
                Alphabets.getAlphabet(AlphabetId.QUALITY_SANGER, QualityScoreAlphabet.class));

    }

    /**
     * Instantiates a new encoded fastq seq.
     *
     * @param alphabet the alphabet
     * @param codec the codec
     */
    public EncodedFastqSeq(final A alphabet, final ByteByteCodec codec) {
        super(alphabet, codec);
        seq = new ByteSeqImpl<QualityScoreAlphabet>(
                Alphabets.getAlphabet(AlphabetId.QUALITY_SANGER, QualityScoreAlphabet.class));

    }

    /**
     * Instantiates a new encoded fastq seq.
     *
     * @param alphabet the alphabet
     */
    public EncodedFastqSeq(final A alphabet) {
        super(alphabet);
        seq = new ByteSeqImpl<QualityScoreAlphabet>(
                Alphabets.getAlphabet(AlphabetId.QUALITY_SANGER, QualityScoreAlphabet.class));
    }

    /* (non-Javadoc)
     * @see org.biomojo.sequence.FastqSeq#getQualityScores()
     */
    @Override
    public ByteSeq<QualityScoreAlphabet> getQualityScores() {
        return seq;
    }

}
