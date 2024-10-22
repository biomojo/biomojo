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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.DefaultHeaderBuilder;
import org.biomojo.io.HeaderBuilder;
import org.biomojo.io.SeqOutput;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.util.OutputUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class FastaOutputStream.
 */
public class FastaOutput<T extends ByteAlphabet> extends FilterOutputStream implements SeqOutput<ByteSeq<T>> {

    /** The Constant DEFAULT_LINE_LENGTH. */
    private static final int DEFAULT_LINE_LENGTH = 60;

    /** The header builder. */
    private final HeaderBuilder headerBuilder;

    /** The max line length. */
    private final int maxLineLength;

    /**
     * Instantiates a new fasta output stream.
     *
     * @param outputStream
     *            the output stream
     */
    public FastaOutput(final OutputStream outputStream) {
        super(outputStream);
        maxLineLength = DEFAULT_LINE_LENGTH;
        headerBuilder = new DefaultHeaderBuilder();
    }

    public FastaOutput(final String outputFileName) throws FileNotFoundException {
        super(new FileOutputStream(outputFileName));
        maxLineLength = DEFAULT_LINE_LENGTH;
        headerBuilder = new DefaultHeaderBuilder();
    }

    /**
     * Instantiates a new fasta output stream.
     *
     * @param outputStream
     *            the output stream
     * @param sequenceHeaderBuilder
     *            the sequence header builder
     */
    public FastaOutput(final OutputStream outputStream, final HeaderBuilder sequenceHeaderBuilder) {
        super(outputStream);
        this.headerBuilder = sequenceHeaderBuilder;
        maxLineLength = DEFAULT_LINE_LENGTH;
    }

    /**
     * Instantiates a new fasta output stream.
     *
     * @param outputStream
     *            the output stream
     * @param sequenceHeaderBuilder
     *            the sequence header builder
     * @param maxLineLength
     *            the max line length
     */
    public FastaOutput(final OutputStream outputStream, final HeaderBuilder sequenceHeaderBuilder,
            final int maxLineLength) {
        super(outputStream);
        this.maxLineLength = maxLineLength;
        this.headerBuilder = sequenceHeaderBuilder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.io.SequenceOutputStream#write(org.biomojo.sequence.Seq)
     */
    @Override
    public <X extends ByteSeq<T>> void write(final X sequence) throws UncheckedIOException {
        try {
            out.write(FastaConst.RECORD_DELIMITER);
            out.write(headerBuilder.buildHeader(sequence));
            out.write('\n');
            OutputUtil.writeSplitLines(out, maxLineLength, sequence.toByteArray());
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
