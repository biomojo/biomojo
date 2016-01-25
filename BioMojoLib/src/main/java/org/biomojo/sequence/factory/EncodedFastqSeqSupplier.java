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

import org.biomojo.alphabet.Nucleotide;
import org.biomojo.codec.ByteByteCodec;
import org.biomojo.codec.Codecs;
import org.biomojo.sequence.EncodedFastqSeq;

/**
 * @author Hugh Eaves
 *
 */
public class EncodedFastqSeqSupplier<A extends Nucleotide<A>> extends FastqSeqSupplier<A> {

    private final ByteByteCodec codec;

    public EncodedFastqSeqSupplier(final int alphabetId, final int codecId) {
        super(alphabetId);
        codec = Codecs.getCodec(codecId);
    }

    /**
     * @see java.util.function.Supplier#get()
     */
    @Override
    public EncodedFastqSeq<A> get() {
        return new EncodedFastqSeq<>(alphabet, codec);
    }

}
