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

import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.sequence.BasicFastqSeq;
import org.biomojo.sequence.FastqSeq;

/**
 * @author Hugh Eaves
 *
 */
public class FastqSeqSupplier<A extends Nucleotide<A>> implements Supplier<FastqSeq<A>> {

    protected final A alphabet;

    @SuppressWarnings("unchecked")
    public FastqSeqSupplier(final int alphabetId) {
        alphabet = (A) Alphabets.getAlphabet(alphabetId);
    }

    /**
     * @see java.util.function.Supplier#get()
     */
    @Override
    public FastqSeq<A> get() {
        return new BasicFastqSeq<>(alphabet);
    }

}
