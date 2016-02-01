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

package org.biomojo.io.fastx;

import java.io.InputStream;
import java.util.function.Supplier;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.DefaultHeaderParser;
import org.biomojo.io.HeaderParser;
import org.biomojo.io.MarkAndCopyInputStream;
import org.biomojo.io.ParseException;
import org.biomojo.sequence.ByteSeq;

/**
 * The Class FastaInputStream.
 */
public class FastaInputStream<T extends ByteAlphabet> extends MarkAndCopyInputStream<ByteSeq<T>> {

    /** The header parser. */
    private final HeaderParser headerParser;

    /** The validate sequence data. */
    private final boolean validateSequenceData;

    private final Supplier<? extends ByteSeq<T>> factory;

    /**
     * Instantiates a new fasta input stream.
     *
     * @param inputStream
     *            the input stream
     */
    public FastaInputStream(final InputStream inputStream) {
        super(inputStream);
        headerParser = new DefaultHeaderParser();
        validateSequenceData = true;
        factory = null;
    }

    public FastaInputStream(final InputStream inputStream, final Supplier<? extends ByteSeq<T>> factory) {
        super(inputStream);
        headerParser = new DefaultHeaderParser();
        validateSequenceData = true;
        this.factory = factory;
    }

    /**
     * Instantiates a new fasta input stream.
     *
     * @param inputStream
     *            the input stream
     * @param sequenceHeaderParser
     *            the sequence header parser
     */
    public FastaInputStream(final InputStream inputStream, final HeaderParser sequenceHeaderParser,
            final Supplier<? extends ByteSeq<T>> factory) {
        super(inputStream);
        headerParser = sequenceHeaderParser;
        validateSequenceData = true;
        this.factory = factory;
    }

    /**
     * Instantiates a new fasta input stream.
     *
     * @param inputStream
     *            the input stream
     * @param readBufferSize
     *            the read buffer size
     */
    public FastaInputStream(final InputStream inputStream, final int readBufferSize) {
        super(inputStream, readBufferSize);
        headerParser = new DefaultHeaderParser();
        validateSequenceData = true;
        factory = null;
    }

    /**
     * Instantiates a new fasta input stream.
     *
     * @param inputStream
     *            the input stream
     * @param validateSequenceData
     *            the validate sequence data
     */
    public FastaInputStream(final InputStream inputStream, final boolean validateSequenceData) {
        super(inputStream);
        headerParser = new DefaultHeaderParser();
        this.validateSequenceData = validateSequenceData;
        factory = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.io.SequenceInputStream#read(org.biomojo.sequence.Seq)
     */
    @Override
    public boolean read(final ByteSeq<T> seq) throws ParseException {
        if (isEof()) {
            return false;
        }

        skipByte(FastaConst.RECORD_DELIMITER);

        readToEndOfLine();

        headerParser.parseHeader(seq, assembleSegments());

        while ((peek() != FastaConst.RECORD_DELIMITER) && !readToEndOfLineOrEOF())
            ;

        if (getTotalLength() == 0) {
            throw new ParseException("Zero length sequence data");
        }

        seq.setAll(assembleSegments(), validateSequenceData);

        return true;
    }

    @Override
    public ByteSeq<T> read() throws ParseException {
        final ByteSeq<T> seq = factory.get();
        if (read(seq)) {
            return seq;
        }
        return null;
    }

}
