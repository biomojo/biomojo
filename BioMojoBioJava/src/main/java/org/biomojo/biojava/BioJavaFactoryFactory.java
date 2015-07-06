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

import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.compound.DNACompoundSet;
import org.biojava.nbio.core.sequence.compound.RNACompoundSet;
import org.biojava.nbio.core.sequence.template.Compound;
import org.biojava.nbio.core.sequence.template.CompoundSet;
import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.AminoAcidAlphabet;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.DNAAlphabet;
import org.biomojo.alphabet.RNAAlphabet;
import org.java0.core.exception.UncheckedException;

/**
 * @author Hugh Eaves
 *
 */
public class BioJavaFactoryFactory {

	@SuppressWarnings("unchecked")
	public static <T extends Compound> BioJavaCompoundFactory<T> getCompoundFactory(
			final ByteAlphabet alphabet) {
		if (alphabet instanceof DNAAlphabet) {
			return (BioJavaCompoundFactory<T>) BioJavaDNACompoundFactory.INSTANCE;
		} else if (alphabet instanceof RNAAlphabet) {
			return (BioJavaCompoundFactory<T>) BioJavaRNACompoundFactory.INSTANCE;
		} else if (alphabet instanceof AminoAcidAlphabet) {
			return (BioJavaCompoundFactory<T>) BioJavaAminoAcidCompoundFactory.INSTANCE;
		}
		throw new UncheckedException(
				"Alphabet not supported for use with BioJava");
	}

	public static <T extends ByteAlphabet> T getByteAlphabet(
			final CompoundSet<?> compoundSet) {
		if (compoundSet instanceof DNACompoundSet) {
			return (T) Alphabets
					.getAlphabet(AlphabetId.DNA, ByteAlphabet.class);
		} else if (compoundSet instanceof RNACompoundSet) {
			return (T) Alphabets
					.getAlphabet(AlphabetId.RNA, ByteAlphabet.class);
		} else if (compoundSet instanceof AminoAcidCompoundSet) {
			return (T) Alphabets.getAlphabet(AlphabetId.AMINO_ACID,
					ByteAlphabet.class);
		} else {
			throw new UncheckedException("Unsupported BioJava compound set: "
					+ compoundSet.toString());
		}
	}
}
