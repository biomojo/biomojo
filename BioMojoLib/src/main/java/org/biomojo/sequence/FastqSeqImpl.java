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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.alphabet.QualityScoreAlphabet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
@Entity
@DiscriminatorValue("F")
public class FastqSeqImpl<A extends NucleotideAlphabet> extends ByteSeqImpl<A>
		implements FastqSeq<A> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FastqSeqImpl.class
			.getName());

	@OneToOne(targetEntity = ByteSeqImpl.class)
	protected ByteSeq<QualityScoreAlphabet> seq;

	public FastqSeqImpl() {
		super();
		seq = new ByteSeqImpl<QualityScoreAlphabet>(Alphabets.getAlphabet(
				AlphabetId.QUALITY_SANGER, QualityScoreAlphabet.class));

	}

	public FastqSeqImpl(final byte[] data, final A alphabet) {
		super(data, alphabet);
		seq = new ByteSeqImpl<QualityScoreAlphabet>(Alphabets.getAlphabet(
				AlphabetId.QUALITY_SANGER, QualityScoreAlphabet.class));

	}

	public FastqSeqImpl(final byte[] data) {
		super(data);
		seq = new ByteSeqImpl<QualityScoreAlphabet>(Alphabets.getAlphabet(
				AlphabetId.QUALITY_SANGER, QualityScoreAlphabet.class));

	}

	public FastqSeqImpl(final A alphabet) {
		super(alphabet);
		seq = new ByteSeqImpl<QualityScoreAlphabet>(Alphabets.getAlphabet(
				AlphabetId.QUALITY_SANGER, QualityScoreAlphabet.class));

	}

	/**
	 * @return the qualityScores
	 */
	@Override
	public ByteSeq<QualityScoreAlphabet> getQualityScores() {
		return seq;
	}

	/**
	 * @param qualityScores
	 *            the qualityScores to set
	 */
	public void setQualityScores(
			final ByteSeq<QualityScoreAlphabet> qualityScores) {
		this.seq = qualityScores;
	}

}
