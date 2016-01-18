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
package org.biomojo.sequence;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.codec.ByteByteCodec;
import org.biomojo.codec.CodecId;
import org.biomojo.codec.Codecs;

// TODO: Auto-generated Javadoc
/**
 * The Class EncodedByteSeq.
 *
 * @author Hugh Eaves
 * @param <A>
 *            the generic type
 */
@Entity
@DiscriminatorValue("E")
public class EncodedByteSeq<A extends ByteAlphabet> extends BasicByteSeq<A> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The codec. */
    protected ByteByteCodec codec;

    /**
     * Instantiates a new encoded byte seq.
     */
    public EncodedByteSeq() {
        super();
        setCodec(Codecs.getCodec(CodecId.NULL_BYTE_CODEC));
    }

    /**
     * Instantiates a new encoded byte seq.
     *
     * @param data
     *            the data
     * @param alphabet
     *            the alphabet
     */
    public EncodedByteSeq(final byte[] data, final A alphabet) {
        super(data, alphabet);
        setCodec(Codecs.getCodec(CodecId.NULL_BYTE_CODEC));
    }

    /**
     * Instantiates a new encoded byte seq.
     *
     * @param data
     *            the data
     * @param alphabet
     *            the alphabet
     * @param codec
     *            the codec
     */
    public EncodedByteSeq(final byte[] data, final A alphabet, final ByteByteCodec codec) {
        super(data, alphabet);
        setCodec(codec);
    }

    /**
     * Instantiates a new encoded byte seq.
     *
     * @param data
     *            the data
     */
    public EncodedByteSeq(final byte[] data) {
        super(data);
        setCodec(Codecs.getCodec(CodecId.NULL_BYTE_CODEC));
    }

    /**
     * Instantiates a new encoded byte seq.
     *
     * @param alphabet
     *            the alphabet
     */
    public EncodedByteSeq(final A alphabet) {
        super(alphabet);
        setCodec(Codecs.getCodec(CodecId.NULL_BYTE_CODEC));
    }

    /**
     * Instantiates a new encoded byte seq.
     *
     * @param alphabet
     *            the alphabet
     * @param codec
     *            the codec
     */
    public EncodedByteSeq(final A alphabet, final ByteByteCodec codec) {
        super(alphabet);
        setCodec(codec);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.ByteSeqImpl#getAllBytes()
     */
    @Override
    public byte[] toByteArray() {
        return codec.decodeAll(getAlphabet(), data, length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.ByteSeqImpl#setAll(byte[], boolean)
     */
    @Override
    public void setAll(final byte[] sequence, final boolean validate) {
        if (validate) {
            alphabet.validate(sequence);
        }
        this.length = sequence.length;
        data = codec.encode(getAlphabet(), data, sequence.length, sequence);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.ByteSeqImpl#getValue(int)
     */
    @Override
    public byte getByte(final int index) {
        return codec.decode(getAlphabet(), data, length, index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.sequence.ByteSeqImpl#setValue(byte, int)
     */
    @Override
    public void set(final int index, final byte value) {
        alphabet.validate(value);
        codec.encode(getAlphabet(), data, length, value, index);
    }

    /**
     * Gets the codec.
     *
     * @return the codec
     */
    public ByteByteCodec getCodec() {
        return codec;
    }

    /**
     * Sets the codec.
     *
     * @param codec
     *            the codec to set
     */
    public void setCodec(final ByteByteCodec codec) {
        if (codec.supportsAlphabet(getAlphabet())) {
            this.codec = codec;
        } else {
            throw new IllegalArgumentException("Codec does not support alphabet");
        }
    }

}