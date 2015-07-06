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

import org.biomojo.alphabet.ByteAlphabet;

/**
 * @author Hugh Eaves
 *
 */
@Entity
@DiscriminatorValue("Y")
public abstract class AbstractByteSeq<A extends ByteAlphabet> extends
		AbstractSeq<Byte, A> implements ByteSeq<A> {

	private static final long serialVersionUID = 1L;

	protected A alphabet;

	public AbstractByteSeq() {
	}

	public AbstractByteSeq(final A alphabet) {
		initAlphabet(alphabet);
	}

	protected void initAlphabet(final A alphabet) {
		this.alphabet = alphabet;
	}

	@Override
	public A getAlphabet() {
		return alphabet;
	}

}
