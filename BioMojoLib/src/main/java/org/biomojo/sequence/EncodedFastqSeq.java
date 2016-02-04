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

import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.codec.ByteByteCodec;

// TODO: Auto-generated Javadoc
/**
 * The Class EncodedFastqSeq.
 *
 * @author Hugh Eaves
 * @param <A>
 *            the generic type
 */
@Entity
@DiscriminatorValue("G")
public class EncodedFastqSeq<A extends Nucleotide<A>, Q extends ByteQuality<?>> extends EncodedByteSeq<A>
        implements FastqSeq<A, Q> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The seq. */
    @OneToOne(targetEntity = BasicByteSeq.class)
    protected ByteSeq<Q> seq;

    /**
     * Instantiates a new encoded fastq seq.
     */
    public EncodedFastqSeq() {
        super();
    }

    /**
     * Instantiates a new encoded fastq seq.
     *
     * @param data
     *            the data
     * @param alphabet
     *            the alphabet
     * @param codec
     *            the codec
     */
    public EncodedFastqSeq(final byte[] data, final A alphabet, final ByteByteCodec codec,
            final ByteSeq<Q> qualityScores) {
        super(data, alphabet, codec);
        setQualityScores(qualityScores);
    }

    /**
     * Instantiates a new encoded fastq seq.
     *
     * @param data
     *            the data
     * @param alphabet
     *            the alphabet
     */
    public EncodedFastqSeq(final byte[] data, final A alphabet) {
        super(data, alphabet);
    }

    /**
     * Instantiates a new encoded fastq seq.
     *
     * @param data
     *            the data
     */
    public EncodedFastqSeq(final byte[] data) {
        super(data);
    }

    /**
     * Instantiates a new encoded fastq seq.
     *
     * @param alphabet
     *            the alphabet
     * @param codec
     *            the codec
     */
    public EncodedFastqSeq(final A alphabet, final ByteByteCodec codec, final ByteSeq<Q> qualityScores) {
        super(alphabet, codec);
        setQualityScores(qualityScores);
    }

    /**
     * Instantiates a new encoded fastq seq.
     *
     * @param alphabet
     *            the alphabet
     */
    public EncodedFastqSeq(final A alphabet) {
        super(alphabet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.FastqSeq#getQualityScores()
     */
    @Override
    public ByteSeq<Q> getQualityScores() {
        return seq;
    }

    /**
     * Sets the quality scores.
     *
     * @param qualityScores
     *            the qualityScores to set
     */
    public void setQualityScores(final ByteSeq<Q> qualityScores) {
        this.seq = qualityScores;
    }

}
