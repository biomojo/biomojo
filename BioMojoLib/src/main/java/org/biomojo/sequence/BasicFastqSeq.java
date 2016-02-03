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

import org.biomojo.alphabet.ByteQualityScore;
import org.biomojo.alphabet.Nucleotide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class FastqSeqImpl.
 *
 * @author Hugh Eaves
 * @param <A>
 *            the generic type
 */
@Entity
@DiscriminatorValue("F")
public class BasicFastqSeq<A extends Nucleotide<?>, Q extends ByteQualityScore<?>> extends BasicByteSeq<A>
        implements FastqSeq<A, Q> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(BasicFastqSeq.class.getName());

    /** The seq. */
    @OneToOne(targetEntity = AbstractByteSeq.class)
    protected ByteSeq<Q> seq;

    /**
     * Instantiates a new fastq seq impl.
     */
    public BasicFastqSeq() {
        super();
    }

    /**
     * Instantiates a new fastq seq impl.
     *
     * @param data
     *            the data
     * @param alphabet
     *            the alphabet
     */
    public BasicFastqSeq(final byte[] data, final A alphabet, final ByteSeq<Q> qualityScores) {
        super(data, alphabet);
        this.seq = qualityScores;
    }

    /**
     * Instantiates a new fastq seq impl.
     *
     * @param data
     *            the data
     */
    public BasicFastqSeq(final byte[] data, final ByteSeq<Q> qualityScores) {
        super(data);
        this.seq = qualityScores;
    }

    /**
     * Instantiates a new fastq seq impl.
     *
     * @param alphabet
     *            the alphabet
     */
    public BasicFastqSeq(final A alphabet, final ByteSeq<Q> qualityScores) {
        super(alphabet);
        this.seq = qualityScores;
    }

    /**
     * Gets the quality scores.
     *
     * @return the qualityScores
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
