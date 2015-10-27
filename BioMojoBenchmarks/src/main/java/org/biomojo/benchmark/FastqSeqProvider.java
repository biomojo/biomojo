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
package org.biomojo.benchmark;

import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.sequence.FastqSeqImpl;
import org.biomojo.sequence.FastqSeq;

/**
 * @author Hugh Eaves
 *
 */
public class FastqSeqProvider implements Supplier<FastqSeq<NucleotideAlphabet>> {

    /**
     * @see java.util.function.Supplier#get()
     */
    @Override
    public FastqSeq<NucleotideAlphabet> get() {
        final FastqSeq<NucleotideAlphabet> seq = new FastqSeqImpl<NucleotideAlphabet>(
                Alphabets.getAlphabet(AlphabetId.DNA, NucleotideAlphabet.class));
        return seq;
    }
}
