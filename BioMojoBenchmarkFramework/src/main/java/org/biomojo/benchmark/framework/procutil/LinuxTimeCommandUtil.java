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
package org.biomojo.benchmark.framework.procutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.java0.collection.tuple.TwoTuple;
import org.java0.core.exception.ParseException;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class LinuxTimeCommandUtil {
    private static final Logger logger = LoggerFactory.getLogger(LinuxTimeCommandUtil.class.getName());

    // command line ^ major faults ^ minor faults ^ max rss ^ percent cpu ^
    // system time ^ user time ^ elapsed time
    protected static final char[] TIME_FIELDS = { 'C', 'F', 'R', 'M', 'P', 'S', 'U', 'e', 'x' };
    protected static final String FIELD_FORMAT_STRING;

    public static final int NUM_FIELDS = TIME_FIELDS.length;

    static {
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < TIME_FIELDS.length; ++i) {
            if (i > 0) {
                buffer.append("^");
            }
            buffer.append("%");
            buffer.append(TIME_FIELDS[i]);
        }
        FIELD_FORMAT_STRING = buffer.toString();
    }

    public static TwoTuple<List<String>, String> prepareCommandLine(final List<String> commandLine) {
        logger.debug("Preparing command line for linux time command");

        File timeOutputFile;
        try {
            timeOutputFile = File.createTempFile("time", ".txt");
        } catch (final IOException e) {
            logger.error("Caught exception while creating temp file", e);
            throw new UncheckedException(e);
        }

        final List<String> newCommandLine = new ArrayList<>();

        newCommandLine.add("/usr/bin/time");
        newCommandLine.add("-o");
        newCommandLine.add(timeOutputFile.getAbsolutePath());
        newCommandLine.add("-f");
        newCommandLine.add(FIELD_FORMAT_STRING);
        newCommandLine.addAll(commandLine);

        return new TwoTuple<List<String>, String>(newCommandLine, timeOutputFile.toString());

    }

    public static LinuxTimeCommandResult parseResult(final String timeOutputFileName) {
        LinuxTimeCommandResult info = null;

        final File timeOutputFile = new File(timeOutputFileName);

        try {
            final List<String> lines = Files.readAllLines(timeOutputFile.toPath());
            timeOutputFile.delete();

            if (lines.size() == 1 || lines.size() == 2) {
                try {
                    // Note, we get more than one line if the exit status != 0
                    info = new LinuxTimeCommandResult(lines.get(lines.size() - 1).split("\\^"));
                } catch (final ParseException e) {
                    logger.error("Unable to parse time command output: [{}]", lines.get(0));
                }
            } else {
                logger.error("Zero line file");
            }
        } catch (final IOException e) {
            logger.error("Caught IOException parsing time command result", e);
        }

        return info;
    }

}
