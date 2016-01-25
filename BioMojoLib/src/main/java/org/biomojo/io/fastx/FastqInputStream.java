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

import org.biomojo.alphabet.Nucleotide;
import org.biomojo.alphabet.QualityScore;
import org.biomojo.io.DefaultHeaderParser;
import org.biomojo.io.HeaderParser;
import org.biomojo.io.MarkAndCopyInputStream;
import org.biomojo.io.ParseException;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FastqInputStream.
 */
public class FastqInputStream<T extends Nucleotide<T>> extends MarkAndCopyInputStream
        implements SequenceInputStream<FastqSeq<T>> {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(FastqInputStream.class.getName());

    /** The sequence header parser. */
    private final HeaderParser sequenceHeaderParser;

    /** The validate sequence data. */
    private final boolean validateSequenceData;

    private final Supplier<? extends FastqSeq<T>> factory;

    /**
     * Instantiates a new fastq input stream.
     *
     * @param inputStream
     *            the input stream
     */
    public FastqInputStream(final InputStream inputStream) {
        super(inputStream);
        validateSequenceData = true;
        sequenceHeaderParser = new DefaultHeaderParser();
        factory = null;
    }

    public FastqInputStream(final InputStream inputStream, final Supplier<? extends FastqSeq<T>> factory) {
        super(inputStream);
        validateSequenceData = true;
        sequenceHeaderParser = new DefaultHeaderParser();
        this.factory = factory;
    }

    /**
     * Instantiates a new fastq input stream.
     *
     * @param inputStream
     *            the input stream
     * @param sequenceHeaderParser
     *            the sequence header parser
     */
    public FastqInputStream(final InputStream inputStream, final HeaderParser sequenceHeaderParser) {
        super(inputStream);
        validateSequenceData = true;
        this.sequenceHeaderParser = sequenceHeaderParser;
        factory = null;
    }

    public FastqInputStream(final InputStream inputStream, final HeaderParser sequenceHeaderParser,
            final Supplier<? extends FastqSeq<T>> factory) {
        super(inputStream);
        validateSequenceData = true;
        this.sequenceHeaderParser = sequenceHeaderParser;
        this.factory = factory;
    }

    public FastqInputStream(final InputStream inputStream, final HeaderParser sequenceHeaderParser,
            final Supplier<? extends FastqSeq<T>> factory, final boolean validateSequenceData) {
        super(inputStream);
        this.validateSequenceData = validateSequenceData;
        this.sequenceHeaderParser = sequenceHeaderParser;
        this.factory = factory;
    }

    /**
     * Instantiates a new fastq input stream.
     *
     * @param inputStream
     *            the input stream
     * @param bufferSize
     *            the buffer size
     */
    public FastqInputStream(final InputStream inputStream, final int bufferSize) {
        super(inputStream, bufferSize);
        validateSequenceData = true;
        sequenceHeaderParser = new DefaultHeaderParser();
        factory = null;
    }

    /**
     * Instantiates a new fastq input stream.
     *
     * @param inputStream
     *            the input stream
     * @param validateSequence
     *            the validate sequence
     */
    public FastqInputStream(final InputStream inputStream, final boolean validateSequence) {
        super(inputStream);
        this.validateSequenceData = validateSequence;
        sequenceHeaderParser = new DefaultHeaderParser();
        factory = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.io.SequenceInputStream#read(org.biomojo.sequence.Seq)
     */
    @Override
    public boolean read(final FastqSeq<T> fastQSeq) throws ParseException {
        if (isEof()) {
            return false;
        }

        skipByte(FastqConst.RECORD_DELIMITER);

        // read the Header line
        readToEndOfLine();

        // parse the header
        sequenceHeaderParser.parseHeader(fastQSeq, assembleSegments());

        // read all the sequence data
        while (peek() != FastqConst.QUALITY_DELIMITER) {
            readToEndOfLine();
        }

        final int sequenceLength = getTotalLength();

        if (sequenceLength == 0) {
            throw new ParseException("Zero length sequence data");
        }

        // assembly the sequence data
        fastQSeq.setAll(assembleSegments(), validateSequenceData);

        // read and discard the second Header line
        skipNextLine();

        final ByteSeq<QualityScore> qualityScores = fastQSeq.getQualityScores();

        while (getTotalLength() < sequenceLength && !readToEndOfLineOrEOF())
            ;

        if (getTotalLength() != sequenceLength) {
            throw new ParseException("Length of quality data differs from sequence data");
        }

        qualityScores.setAll(assembleSegments(), validateSequenceData);

        return true;
    }

    @Override
    public FastqSeq<T> readSeq() throws ParseException {
        final FastqSeq<T> seq = factory.get();
        if (read(seq)) {
            return seq;
        }
        return null;
    }
}
