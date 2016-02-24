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
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.sequence.BasicByteSeq;
import org.biomojo.sequence.ByteSeq;

/**
 * @author Hugh Eaves
 *
 */
public class ByteSeqSupplier<A extends ByteAlphabet> implements Supplier<ByteSeq<A>> {

    protected final A alphabet;

    public ByteSeqSupplier(final A alphabet) {
        this.alphabet = alphabet;
    }

    public ByteSeqSupplier(final Class<? extends A> alphabetClass) {
        alphabet = BioMojo.getObject(alphabetClass);
    }

    @SuppressWarnings("unchecked")
    public ByteSeqSupplier(final long alphabetId) {
        alphabet = (A) BioMojo.getObject(Alphabet.class, alphabetId);
    }

    /**
     * @see java.util.function.Supplier#get()
     */
    @Override
    public ByteSeq<A> get() {
        return new BasicByteSeq<>(alphabet);
    }

}
