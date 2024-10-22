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
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.codec.ByteByteCodec;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.EncodedByteSeq;

/**
 * @author Hugh Eaves
 *
 */
public class EncodedByteSeqSupplier<A extends ByteAlphabet> extends ByteSeqSupplier<A> implements Supplier<ByteSeq<A>> {

    private final ByteByteCodec codec;

    public EncodedByteSeqSupplier(final A alphabet, final ByteByteCodec codec) {
        super(alphabet);
        this.codec = codec;
    }

    public EncodedByteSeqSupplier(final Class<? extends A> alphabetClass,
            final Class<? extends ByteByteCodec> codecClass) {
        super(BioMojo.getObject(alphabetClass));
        codec = BioMojo.getObject(codecClass);
    }

    public EncodedByteSeqSupplier(final long alphabetId, final long codecId) {
        super(alphabetId);
        codec = BioMojo.getObject(ByteByteCodec.class, codecId);
    }

    /**
     * @see java.util.function.Supplier#get()
     */
    @Override
    public ByteSeq<A> get() {
        return new EncodedByteSeq<>(alphabet, codec);
    }

}
