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

import org.biojava.nbio.core.sequence.compound.DNACompoundSet;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;

/**
 * @author Hugh Eaves
 *
 */
public class BioJavaDNACompoundFactory extends
		AbstractBioJavaCompoundFactory<NucleotideCompound> {

	public static final BioJavaCompoundFactory<NucleotideCompound> INSTANCE = new BioJavaDNACompoundFactory(
			DNACompoundSet.getDNACompoundSet());

	/**
	 * Create a new BioJavaNucleotideCompondFactory.
	 *
	 * @param dnaCompoundSet
	 */
	public BioJavaDNACompoundFactory(DNACompoundSet compoundSet) {
		super(compoundSet);
	}
}
