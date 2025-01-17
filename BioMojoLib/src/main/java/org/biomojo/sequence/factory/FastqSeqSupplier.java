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
package org.biomojo.sequence.factory;

import java.util.function.Supplier;

import org.biomojo.BioMojo;
import org.biomojo.alphabet.Alphabet;
import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.sequence.BasicByteSeq;
import org.biomojo.sequence.BasicFastqSeq;
import org.biomojo.sequence.FastqSeq;

/**
 * @author Hugh Eaves
 *
 */
public class FastqSeqSupplier<A extends Nucleotide<?>, Q extends ByteQuality> implements Supplier<FastqSeq<A, Q>> {

    protected final A alphabet;
    protected final Q qualityScoreAlphabet;

    public FastqSeqSupplier(final A alphabet, final Q qualityScoreAlphabet) {
        this.alphabet = alphabet;
        this.qualityScoreAlphabet = qualityScoreAlphabet;
    }

    public FastqSeqSupplier(final Class<? extends A> alphabetClass,
            final Class<? extends Q> qualityScoreAlphabetClass) {
        alphabet = BioMojo.getObject(alphabetClass);
        qualityScoreAlphabet = BioMojo.getObject(qualityScoreAlphabetClass);
    }

    @SuppressWarnings("unchecked")
    public FastqSeqSupplier(final long alphabetId, final long qualityScoreAlphabetId) {
        alphabet = (A) BioMojo.getObject(Alphabet.class, alphabetId);
        qualityScoreAlphabet = (Q) BioMojo.getObject(Alphabet.class, qualityScoreAlphabetId);
    }

    /**
     * @see java.util.function.Supplier#get()
     */
    @Override
    public FastqSeq<A, Q> get() {
        return new BasicFastqSeq<>(alphabet, new BasicByteSeq<>(qualityScoreAlphabet));
    }

}
