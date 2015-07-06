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

import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;

/**
 * @author Hugh Eaves
 *
 */
public class BioJavaAminoAcidCompoundFactory extends
		AbstractBioJavaCompoundFactory<AminoAcidCompound> {

	public static final BioJavaCompoundFactory<AminoAcidCompound> INSTANCE = new BioJavaAminoAcidCompoundFactory(
			AminoAcidCompoundSet.getAminoAcidCompoundSet());

	/**
	 * Create a new BioJavaNucleotideCompondFactory.
	 *
	 * @param dnaCompoundSet
	 */
	public BioJavaAminoAcidCompoundFactory(AminoAcidCompoundSet compoundSet) {
		super(compoundSet);
	}
}
