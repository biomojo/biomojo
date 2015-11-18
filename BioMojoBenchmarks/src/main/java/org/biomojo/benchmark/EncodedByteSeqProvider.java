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
package org.biomojo.benchmark;

import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.IUPACAlphabet;
import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.codec.ByteByteCodec;
import org.biomojo.codec.CodecId;
import org.biomojo.codec.Codecs;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.EncodedByteSeq;

/**
 * @author Hugh Eaves
 *
 */
public class EncodedByteSeqProvider implements Supplier<ByteSeq<IUPACAlphabet>> {

    /**
     * @see java.util.function.Supplier#get()
     */
    @Override
    public ByteSeq<IUPACAlphabet> get() {
        final EncodedByteSeq<IUPACAlphabet> seq = new EncodedByteSeq<>(
                Alphabets.getAlphabet(AlphabetId.DNA, NucleotideAlphabet.class),
                Codecs.getCodec(CodecId.TWO_BIT_BYTE_CODEC, ByteByteCodec.class));
        return seq;
    }
}
