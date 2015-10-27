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

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.DefaultHeaderParser;
import org.biomojo.io.HeaderParser;
import org.biomojo.io.MarkAndCopyInputStream;
import org.biomojo.io.ParseException;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.sequence.ByteSeq;

public class FastaInputStream extends MarkAndCopyInputStream
        implements SequenceInputStream<ByteSeq<? extends ByteAlphabet>> {

    private final HeaderParser headerParser;
    private final boolean validateSequenceData;

    public FastaInputStream(final InputStream inputStream) {
        super(inputStream);
        headerParser = new DefaultHeaderParser();
        validateSequenceData = true;
    }

    public FastaInputStream(final InputStream inputStream, final HeaderParser sequenceHeaderParser) {
        super(inputStream);
        this.headerParser = sequenceHeaderParser;
        validateSequenceData = true;
    }

    public FastaInputStream(final InputStream inputStream, final int readBufferSize) {
        super(inputStream, readBufferSize);
        headerParser = new DefaultHeaderParser();
        validateSequenceData = true;
    }

    public FastaInputStream(final InputStream inputStream, final boolean validateSequenceData) {
        super(inputStream);
        headerParser = new DefaultHeaderParser();
        this.validateSequenceData = validateSequenceData;
    }

    @Override
    public boolean read(final ByteSeq<? extends ByteAlphabet> seq) throws ParseException {
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
}
