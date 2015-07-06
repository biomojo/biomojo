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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.io.DefaultHeaderBuilder;
import org.biomojo.io.HeaderBuilder;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.util.OutputUtil;

public class FastqOutputStream extends FilterOutputStream implements
		SequenceOutputStream<FastqSeq<? extends NucleotideAlphabet>> {
	private static final int DEFAULT_LINE_LENGTH = Integer.MAX_VALUE;

	private final HeaderBuilder headerBuilder;
	private final int maxLineLength;

	public FastqOutputStream(final OutputStream outputStream) {
		super(outputStream);
		maxLineLength = DEFAULT_LINE_LENGTH;
		headerBuilder = new DefaultHeaderBuilder();
	}

	public FastqOutputStream(final OutputStream outputStream,
			final HeaderBuilder sequenceHeaderBuilder) {
		super(outputStream);
		maxLineLength = DEFAULT_LINE_LENGTH;
		this.headerBuilder = sequenceHeaderBuilder;
	}

	public FastqOutputStream(final OutputStream outputStream,
			final HeaderBuilder sequenceHeaderBuilder, final int maxLineLength) {
		super(outputStream);
		this.maxLineLength = maxLineLength;
		this.headerBuilder = sequenceHeaderBuilder;
	}

	public FastqOutputStream(final OutputStream outputStream,
			final int maxLineLength) {
		super(outputStream);
		this.maxLineLength = maxLineLength;
		headerBuilder = new DefaultHeaderBuilder();
	}

	@Override
	public void write(final FastqSeq<? extends NucleotideAlphabet> sequence)
			throws IOException {
		out.write(FastqConst.RECORD_DELIMITER);
		out.write(headerBuilder.buildHeader(sequence));
		out.write('\n');
		OutputUtil.writeSplitLines(out, maxLineLength, sequence.getAllBytes());
		out.write(FastqConst.QUALITY_DELIMITER);
		out.write('\n');
		OutputUtil.writeSplitLines(out, maxLineLength, sequence
				.getQualityScores().getAllBytes());
	}
}
