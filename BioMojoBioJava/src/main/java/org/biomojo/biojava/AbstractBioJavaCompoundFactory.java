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
package org.biomojo.biojava;

import org.biojava.nbio.core.sequence.template.Compound;
import org.biojava.nbio.core.sequence.template.CompoundSet;

/**
 * @author Hugh Eaves
 *
 */
public class AbstractBioJavaCompoundFactory<T extends Compound> implements
		BioJavaCompoundFactory<T> {

	/** The compounds. */
	protected Object[] compounds = new Object[Byte.MAX_VALUE + 1];

	/** The compound set. */
	protected CompoundSet<T> compoundSet;

	/**
	 * Instantiates a new compound factory.
	 *
	 * @param compoundSet
	 *            the compound set
	 */
	protected AbstractBioJavaCompoundFactory(CompoundSet<T> compoundSet) {
		this.compoundSet = compoundSet;
		char[] symbol = new char[1];
		for (char i = 0; i < compounds.length; ++i) {
			symbol[0] = i;
			compounds[i] = compoundSet.getCompoundForString(new String(symbol));
		}
	}

	/**
	 * Gets the compound for.
	 *
	 * @param b
	 *            the b
	 * @return the compound for
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T getCompoundFor(byte b) {
		return (T) compounds[b];
	}

	/**
	 * Gets the compound set.
	 *
	 * @return the compound set
	 */
	@Override
	public CompoundSet<T> getCompoundSet() {
		return compoundSet;
	}
}
