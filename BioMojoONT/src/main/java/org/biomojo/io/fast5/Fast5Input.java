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
package org.biomojo.io.fast5;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.io.SeqInput;
import org.biomojo.io.fastx.FastqInput;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.java0.core.exception.ParseException;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;
import org.java0.string.CharArrayInputStream;
import org.java0.util.Nullsafe;

import ucar.ma2.Array;
import ucar.nc2.Attribute;
import ucar.nc2.Group;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class Fast5Input<A extends Nucleotide<?>, Q extends ByteQuality> implements SeqInput<FastqSeq<A, Q>> {
    private static final Logger logger = LoggerFactory.getLogger(Fast5Input.class.getName());

    private static final String _1_ANALYSES = "Analyses";

    private static final String _2_BASECALL_2D = "Basecall_2D_000";

    private static final String _2_BASECALL_1D = "Basecall_1D_000";

    private static final String _3_CONFIGURATION = "Configuration";

    private static final String _3_BASECALLED_2D = "BaseCalled_2D";
    private static final String _3_BASECALLED_COMPLEMENT = "BaseCalled_complement";
    private static final String _3_BASECALLED_TEMPLATE = "BaseCalled_template";

    private static final String _4_EVENTS = "Events";
    private static final String _4_FASTQ = "Fastq";
    private static final String _4_MODEL = "Model";

    private static final String _2_EVENT_DETECTION = "EventDetection_000";

    private static final String _1_SEQUENCES = "Sequences";
    private static final String _1_UNIQUEGLOBALKEY = "UniqueGlobalKey";

    private static final int BUFFER_SIZE = 1024 * 256;
    // private static final int BUFFER_SIZE = -1;

    private final Supplier<? extends FastqSeq<A, Q>> seqSupplier;

    private Collection<String> fileList;
    private Iterator<String> fileIterator;

    public Fast5Input(final File fileOrDir) throws UncheckedIOException {
        // RandomAccessFile.setDebugAccess(true);
        // RandomAccessFile.setDebugLeaks(true);
        this(fileOrDir, new FastqSeqSupplier<A, Q>(AlphabetId.NUCLEOTIDE, AlphabetId.QUALITY_SANGER));
    }

    public Fast5Input(final File fileOrDir, final Supplier<? extends FastqSeq<A, Q>> seqSupplier)
            throws UncheckedIOException {
        buildFileList(fileOrDir);
        this.seqSupplier = seqSupplier;
    }

    private void buildFileList(final File fileOrDir) {
        logger.info("building file list");
        fileList = new ArrayList<>();
        try {
            if (fileOrDir.isDirectory()) {

                Files.newDirectoryStream(fileOrDir.toPath(), "*.{fast5}")
                        .forEach(e -> fileList.add(e.toAbsolutePath().toString()));

            } else {
                fileList.add(fileOrDir.getAbsolutePath().toString());
            }
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }

        fileIterator = fileList.iterator();

        logger.info("done building file list");
    }

    public String getNextFile() {
        if (fileIterator.hasNext()) {
            return fileIterator.next();
        }
        return null;
    }

    @Override
    public boolean read(final FastqSeq<A, Q> seq) throws ParseException {
        String path;
        for (path = getNextFile(); path != null; path = getNextFile()) {

            logger.debug("Opening {}", path);
            try (final NetcdfFile file = NetcdfFile.open(path)) {
                logger.debug("Opened");
                if (readSequence(file, seq)) {
                    return true;
                }

            } catch (final IOException e) {
                throw new ParseException(e);
            }
        }

        return false;

    }

    private boolean readSequence(final NetcdfFile file, final FastqSeq<A, Q> seq) throws IOException {
        logger.debug(_1_ANALYSES);

        final Group analyses = Nullsafe.call(file.getRootGroup(), Group::findGroup, _1_ANALYSES);

        logger.debug(_2_BASECALL_2D);
        final Group baseCall2D = Nullsafe.call(analyses, Group::findGroup, _2_BASECALL_2D);

        logger.debug(_3_BASECALLED_2D);
        Variable fastq = Nullsafe.call(baseCall2D, Group::findGroup, _3_BASECALLED_2D, Group::findVariable, _4_FASTQ);

        if (fastq == null) {
            final Group baseCall1D = Nullsafe.call(analyses, Group::findGroup, _2_BASECALL_1D);
            if (baseCall1D != null) {
                fastq = Nullsafe.call(baseCall1D, Group::findGroup, _3_BASECALLED_COMPLEMENT, Group::findVariable,
                        _4_FASTQ);
                if (fastq == null) {
                    fastq = Nullsafe.call(baseCall1D, Group::findGroup, _3_BASECALLED_TEMPLATE, Group::findVariable,
                            _4_FASTQ);
                }
            } else {
                fastq = Nullsafe.call(baseCall2D, Group::findGroup, _3_BASECALLED_COMPLEMENT, Group::findVariable,
                        _4_FASTQ);
                if (fastq == null) {
                    fastq = Nullsafe.call(baseCall2D, Group::findGroup, _3_BASECALLED_TEMPLATE, Group::findVariable,
                            _4_FASTQ);
                }
            }
        }

        if (fastq != null) {
            logger.debug("Converting Fastq");
            final Array data = fastq.read();
            final char[] d1 = (char[]) data.get1DJavaArray(char.class);
            final FastqInput<A, Q> inputStream = new FastqInput<>(new CharArrayInputStream(d1));
            inputStream.read(seq);
            inputStream.close();
            return true;
        }
        return false;
    }

    @Override
    public void close() throws IOException {
        fileList = null;
    }

    public void dumpGroup(final int maxDepth, final Group parent) {
        dumpGroup(0, maxDepth, parent);
    }

    public void dumpGroup(final int depth, final int maxDepth, final Group parent) {

        if (parent == null || depth > maxDepth) {
            return;
        }
        write(depth, "Group: " + parent.getFullName());
        write(depth, "Attributes: ");
        for (final Attribute attribute : parent.getAttributes()) {
            write(depth + 1, "" + attribute.getFullName());
            for (int i = 0; i < attribute.getLength(); ++i) {
                final Object obj = attribute.getValue(i);
                write(depth + 2, "value[" + i + "] = " + obj);
            }
        }
        write(depth, "Variables: ");
        for (final Variable variable : parent.getVariables()) {
            final StringBuilder builder = new StringBuilder();
            variable.getNameAndDimensions(builder);
            write(depth + 1, "" + builder);
        }
        if (depth < maxDepth) {
            for (final Group subgroup : parent.getGroups()) {
                write(depth, "Subgroups: ");
                dumpGroup(depth + 1, maxDepth, subgroup);
            }
        }

    }

    public void write(final int indent, final String value) {
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indent; ++i) {
            buffer.append("  ");
        }
        buffer.append(value);
        logger.info(buffer.toString());
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
