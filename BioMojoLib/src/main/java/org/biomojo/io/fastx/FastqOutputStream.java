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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.io.DefaultHeaderBuilder;
import org.biomojo.io.HeaderBuilder;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.util.OutputUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class FastqOutputStream.
 */
public class FastqOutputStream<T extends Nucleotide<?>, Q extends ByteQuality> extends FilterOutputStream
        implements SequenceOutputStream<FastqSeq<T, Q>> {

    /** The Constant DEFAULT_LINE_LENGTH. */
    private static final int DEFAULT_LINE_LENGTH = Integer.MAX_VALUE;

    /** The header builder. */
    private final HeaderBuilder headerBuilder;

    /** The max line length. */
    private final int maxLineLength;

    /**
     * Instantiates a new fastq output stream.
     *
     * @param outputStream
     *            the output stream
     */
    public FastqOutputStream(final OutputStream outputStream) {
        super(outputStream);
        maxLineLength = DEFAULT_LINE_LENGTH;
        headerBuilder = new DefaultHeaderBuilder();
    }

    /**
     * Instantiates a new fastq output stream.
     *
     * @param outputStream
     *            the output stream
     * @param sequenceHeaderBuilder
     *            the sequence header builder
     */
    public FastqOutputStream(final OutputStream outputStream, final HeaderBuilder sequenceHeaderBuilder) {
        super(outputStream);
        maxLineLength = DEFAULT_LINE_LENGTH;
        this.headerBuilder = sequenceHeaderBuilder;
    }

    /**
     * Instantiates a new fastq output stream.
     *
     * @param outputStream
     *            the output stream
     * @param sequenceHeaderBuilder
     *            the sequence header builder
     * @param maxLineLength
     *            the max line length
     */
    public FastqOutputStream(final OutputStream outputStream, final HeaderBuilder sequenceHeaderBuilder,
            final int maxLineLength) {
        super(outputStream);
        this.maxLineLength = maxLineLength;
        this.headerBuilder = sequenceHeaderBuilder;
    }

    /**
     * Instantiates a new fastq output stream.
     *
     * @param outputStream
     *            the output stream
     * @param maxLineLength
     *            the max line length
     */
    public FastqOutputStream(final OutputStream outputStream, final int maxLineLength) {
        super(outputStream);
        this.maxLineLength = maxLineLength;
        headerBuilder = new DefaultHeaderBuilder();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.io.SequenceOutputStream#write(org.biomojo.sequence.Seq)
     */
    @Override
    public <X extends FastqSeq<T, Q>> void write(final X sequence) throws IOException {
        out.write(FastqConst.RECORD_DELIMITER);
        out.write(headerBuilder.buildHeader(sequence));
        out.write('\n');
        OutputUtil.writeSplitLines(out, maxLineLength, sequence.toByteArray());
        out.write(FastqConst.QUALITY_DELIMITER);
        out.write('\n');
        OutputUtil.writeSplitLines(out, maxLineLength, sequence.getQualityScores().toByteArray());
    }
}
