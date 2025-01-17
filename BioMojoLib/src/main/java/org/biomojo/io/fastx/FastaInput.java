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
import java.io.InputStream;
import java.util.function.Supplier;

import org.biomojo.GlobalConst;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.DefaultHeaderParser;
import org.biomojo.io.HeaderParser;
import org.biomojo.io.MarkAndCopySeqInput;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.factory.ByteSeqSupplier;
import org.java0.core.exception.ParseException;

/**
 * The Class FastaInputStream.
 */
public class FastaInput<T extends ByteAlphabet> extends MarkAndCopySeqInput<ByteSeq<T>> {

    /** The header parser. */
    private final HeaderParser headerParser;

    /** The validate sequence data. */
    private boolean validateSequenceData;

    private final Supplier<? extends ByteSeq<T>> seqSupplier;

    public FastaInput(final ByteArrayInputStream inputStream, final int bufSize, final ByteSeqSupplier<T> supplier) {
        this(inputStream, bufSize, new DefaultHeaderParser(), supplier, GlobalConst.VALIDATE_INPUT_SEQS);
    }

    public FastaInput(final InputStream inputStream, final ByteAlphabet alphabet) {
        this(inputStream, new DefaultHeaderParser(), new ByteSeqSupplier<T>(alphabet.getId()),
                GlobalConst.VALIDATE_INPUT_SEQS);
    }

    public FastaInput(final InputStream inputStream, final Class<T> alphabetClass) {
        this(inputStream, new DefaultHeaderParser(), new ByteSeqSupplier<T>(alphabetClass),
                GlobalConst.VALIDATE_INPUT_SEQS);
    }

    public FastaInput(final InputStream inputStream, final Supplier<? extends ByteSeq<T>> seqSupplier) {
        this(inputStream, new DefaultHeaderParser(), seqSupplier, GlobalConst.VALIDATE_INPUT_SEQS);
    }

    public FastaInput(final String inputFileName, final HeaderParser headerParser,
            final Supplier<? extends ByteSeq<T>> seqSupplier, final boolean validateSequenceData) {
        super(inputFileName);
        this.headerParser = headerParser;
        this.validateSequenceData = validateSequenceData;
        this.seqSupplier = seqSupplier;
    }

    public FastaInput(final InputStream inputStream, final HeaderParser headerParser,
            final Supplier<? extends ByteSeq<T>> seqSupplier) {
        this(inputStream, headerParser, seqSupplier, GlobalConst.VALIDATE_INPUT_SEQS);
    }

    public FastaInput(final InputStream inputStream, final HeaderParser headerParser,
            final Supplier<? extends ByteSeq<T>> seqSupplier, final boolean validateSequenceData) {
        super(inputStream);
        this.headerParser = headerParser;
        this.validateSequenceData = validateSequenceData;
        this.seqSupplier = seqSupplier;
    }

    public FastaInput(final InputStream inputStream, final int bufSize, final HeaderParser headerParser,
            final Supplier<? extends ByteSeq<T>> seqSupplier, final boolean validateSequenceData) {
        super(inputStream, bufSize);
        this.headerParser = headerParser;
        this.validateSequenceData = validateSequenceData;
        this.seqSupplier = seqSupplier;
    }

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
        final ByteSeq<T> seq = seqSupplier.get();
        if (read(seq)) {
            return seq;
        }
        return null;
    }

}
