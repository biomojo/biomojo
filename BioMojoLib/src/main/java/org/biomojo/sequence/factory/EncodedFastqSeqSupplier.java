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

import org.biomojo.BioMojo;
import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.codec.ByteByteCodec;
import org.biomojo.sequence.BasicByteSeq;
import org.biomojo.sequence.EncodedFastqSeq;
import org.biomojo.sequence.FastqSeq;

/**
 * @author Hugh Eaves
 *
 */
public class EncodedFastqSeqSupplier<A extends Nucleotide<A>, Q extends ByteQuality> extends FastqSeqSupplier<A, Q> {

    private final ByteByteCodec codec;

    public EncodedFastqSeqSupplier(final A alphabet, final ByteByteCodec codec, final Q qualityScoreAlphabet) {
        super(alphabet, qualityScoreAlphabet);
        this.codec = codec;
    }

    public EncodedFastqSeqSupplier(final Class<? extends A> alphabetClass,
            final Class<? extends ByteByteCodec> codecClass, final Class<? extends Q> qualityScoreAlphabetClass) {
        super(BioMojo.getObject(alphabetClass), BioMojo.getObject(qualityScoreAlphabetClass));
        codec = BioMojo.getObject(codecClass);
    }

    public EncodedFastqSeqSupplier(final long alphabetId, final long codecId, final long qualityScoreAlphabetId) {
        super(alphabetId, qualityScoreAlphabetId);
        codec = BioMojo.getObject(ByteByteCodec.class, codecId);
    }

    /**
     * @see java.util.function.Supplier#get()
     */
    @Override
    public FastqSeq<A, Q> get() {
        return new EncodedFastqSeq<>(alphabet, codec, new BasicByteSeq<>(qualityScoreAlphabet));
    }

}
