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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.ParseException;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.sequence.ByteSeq;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;
import org.java0.util.Nullsafe;

import ucar.ma2.Array;
import ucar.nc2.Attribute;
import ucar.nc2.Group;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class Fast5InputStream implements SequenceInputStream<ByteSeq<? extends ByteAlphabet>> {
    private static final String TWOD_GROUP = "BaseCalled_2D";

    private static final String COMPLEMENT_GROUP = "BaseCalled_complement";

    private static final String TEMPLATE_GROUP = "BaseCalled_template";

    private static final String FASTQ_VARIABLE = "Fastq";

    private static final String BASECALL_GROUP = "Basecall_2D_000";

    private static final String ROOT_GROUP = "Analyses";

    private static final Logger logger = LoggerFactory.getLogger(Fast5InputStream.class.getName());

    final Iterator<Path> fileList;

    protected Fast5InputStream(final File directory) throws IOException {
        fileList = Files.newDirectoryStream(directory.toPath(), "*.{fast5}").iterator();
    }

    @Override
    public boolean read(final ByteSeq<? extends ByteAlphabet> sequence) throws ParseException {
        if (fileList.hasNext()) {
            final Path path = fileList.next();
            try {
                final NetcdfFile file = NetcdfFile.open(path.toAbsolutePath().toString());

                final Group baseGroup = Nullsafe.call(file.getRootGroup(), Group::findGroup, ROOT_GROUP,
                        Group::findGroup, BASECALL_GROUP);

                final Group template = Nullsafe.call(baseGroup, Group::findGroup, TEMPLATE_GROUP);
                final Group complement = Nullsafe.call(baseGroup, Group::findGroup, COMPLEMENT_GROUP);
                final Group twoD = Nullsafe.call(baseGroup, Group::findGroup, TWOD_GROUP);

                final Variable fastq = Nullsafe.call(twoD, Group::findVariable, FASTQ_VARIABLE);

                if (fastq != null) {
                    System.out.println(fastq);
                    final Array seq = fastq.read();
                    final char[] d1 = (char[]) seq.copyTo1DJavaArray();
                    final long len = seq.getSize();
                    for (int i = 0; i < len; ++i) {
                        System.out.print(d1[i]);
                    }
                }

            } catch (final IOException e) {
                throw new ParseException(e);
            }
        }
        return false;
    }

    @Override
    public ByteSeq<? extends ByteAlphabet> readSeq() throws ParseException {
        return null;
    }

    @Override
    public void close() throws IOException {

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

}
