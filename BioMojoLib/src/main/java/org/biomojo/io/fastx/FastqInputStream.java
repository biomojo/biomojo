/*
 * Copyright (C) 2014  Hugh Eaves
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

import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.alphabet.QualityScoreAlphabet;
import org.biomojo.io.DefaultHeaderParser;
import org.biomojo.io.HeaderParser;
import org.biomojo.io.MarkAndCopyInputStream;
import org.biomojo.io.ParseException;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastqInputStream extends MarkAndCopyInputStream
        implements SequenceInputStream<FastqSeq<? extends NucleotideAlphabet>> {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(FastqInputStream.class.getName());

    private final HeaderParser sequenceHeaderParser;
    private final boolean validateSequenceData;

    public FastqInputStream(final InputStream inputStream) {
        super(inputStream);
        validateSequenceData = false;
        sequenceHeaderParser = new DefaultHeaderParser();
    }

    public FastqInputStream(final InputStream inputStream, final HeaderParser sequenceHeaderParser) {
        super(inputStream);
        validateSequenceData = false;
        this.sequenceHeaderParser = sequenceHeaderParser;
    }

    public FastqInputStream(final InputStream inputStream, final int bufferSize) {
        super(inputStream, bufferSize);
        validateSequenceData = false;
        sequenceHeaderParser = new DefaultHeaderParser();
    }

    public FastqInputStream(final InputStream inputStream, final boolean validateSequence) {
        super(inputStream);
        this.validateSequenceData = validateSequence;
        sequenceHeaderParser = new DefaultHeaderParser();
    }

    @Override
    public boolean read(final FastqSeq<? extends NucleotideAlphabet> fastQSeq) throws ParseException {
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

        final ByteSeq<QualityScoreAlphabet> qualityScores = fastQSeq.getQualityScores();

        while (getTotalLength() < sequenceLength && !readToEndOfLineOrEOF())
            ;

        if (getTotalLength() != sequenceLength) {
            throw new ParseException("Length of quality data differs from sequence data");
        }

        qualityScores.setAll(assembleSegments(), validateSequenceData);

        return true;
    }
}
