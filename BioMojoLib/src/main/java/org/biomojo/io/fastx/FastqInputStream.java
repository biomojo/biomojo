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

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.ByteQualityScore;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.io.DefaultHeaderParser;
import org.biomojo.io.HeaderParser;
import org.biomojo.io.MarkAndCopyInputStream;
import org.biomojo.io.ParseException;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FastqInputStream.
 */
public class FastqInputStream<A extends Nucleotide<?>, Q extends ByteQualityScore<?>>
        extends MarkAndCopyInputStream<FastqSeq<A, Q>> {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(FastqInputStream.class.getName());

    /** The sequence header parser. */
    private final HeaderParser headerParser;

    /** The validate sequence data. */
    private final boolean validateSequenceData;

    private final Supplier<? extends FastqSeq<A, Q>> seqSupplier;

    public FastqInputStream(final String inputFileName) throws FileNotFoundException {
        this(new FileInputStream(inputFileName));
    }

    public FastqInputStream(final InputStream inputStream) {
        this(inputStream, new DefaultHeaderParser(),
                new FastqSeqSupplier<A, Q>(AlphabetId.NUCLEOTIDE, AlphabetId.QUALITY_SANGER), true);
    }

    public FastqInputStream(final InputStream inputStream, final int bufSize) {
        this(inputStream, bufSize, new DefaultHeaderParser(),
                new FastqSeqSupplier<A, Q>(AlphabetId.NUCLEOTIDE, AlphabetId.QUALITY_SANGER), true);
    }

    public FastqInputStream(final ByteArrayInputStream inputStream, final int bufSize,
            final FastqSeqSupplier<A, Q> supplier) {
        this(inputStream, bufSize, new DefaultHeaderParser(), supplier, true);
    }

    public FastqInputStream(final InputStream inputStream, final ByteAlphabet alphabet) {
        this(inputStream, new DefaultHeaderParser(),
                new FastqSeqSupplier<A, Q>(alphabet.getId(), AlphabetId.QUALITY_SANGER), true);
    }

    public FastqInputStream(final InputStream inputStream, final Supplier<? extends FastqSeq<A, Q>> seqSupplier) {
        this(inputStream, new DefaultHeaderParser(), seqSupplier, true);
    }

    public FastqInputStream(final InputStream inputStream, final HeaderParser headerParser,
            final Supplier<? extends FastqSeq<A, Q>> seqSupplier) {
        this(inputStream, headerParser, seqSupplier, true);
    }

    public FastqInputStream(final InputStream inputStream, final boolean validateSequenceData) {
        this(inputStream, new DefaultHeaderParser(),
                new FastqSeqSupplier<A, Q>(AlphabetId.NUCLEOTIDE, AlphabetId.QUALITY_SANGER), validateSequenceData);
    }

    public FastqInputStream(final InputStream inputStream, final HeaderParser headerParser,
            final Supplier<? extends FastqSeq<A, Q>> seqSupplier, final boolean validateSequenceData) {
        super(inputStream);
        this.headerParser = headerParser;
        this.validateSequenceData = validateSequenceData;
        this.seqSupplier = seqSupplier;
    }

    public FastqInputStream(final InputStream inputStream, final int bufSize, final HeaderParser headerParser,
            final Supplier<? extends FastqSeq<A, Q>> seqSupplier, final boolean validateSequenceData) {
        super(inputStream, bufSize);
        this.headerParser = headerParser;
        this.validateSequenceData = validateSequenceData;
        this.seqSupplier = seqSupplier;
    }

    @Override
    public boolean read(final FastqSeq<A, Q> fastQSeq) throws ParseException {
        if (isEof()) {
            return false;
        }

        skipByte(FastqConst.RECORD_DELIMITER);

        // read the Header line
        readToEndOfLine();

        // parse the header
        headerParser.parseHeader(fastQSeq, assembleSegments());

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

        final ByteSeq<Q> qualityScores = fastQSeq.getQualityScores();

        while (getTotalLength() < sequenceLength && !readToEndOfLineOrEOF())
            ;

        if (getTotalLength() != sequenceLength) {
            throw new ParseException("Length of quality data differs from sequence data");
        }

        qualityScores.setAll(assembleSegments(), validateSequenceData);

        return true;
    }

    @Override
    public FastqSeq<A, Q> read() throws ParseException {
        final FastqSeq<A, Q> seq = seqSupplier.get();
        if (read(seq)) {
            return seq;
        }
        return null;
    }
}
