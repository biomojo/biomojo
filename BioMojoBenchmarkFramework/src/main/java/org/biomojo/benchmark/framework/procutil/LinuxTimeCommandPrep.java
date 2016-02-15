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
import java.util.ArrayList;
import java.util.List;

import org.biomojo.benchmark.framework.tests.ConfigParams;
import org.biomojo.benchmark.framework.tests.TestParameters;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class LinuxTimeCommandPrep extends AbstractLinuxTimeCommand {
    private static final Logger logger = LoggerFactory.getLogger(LinuxTimeCommandPrep.class.getName());

    @Override
    public void safeExecute(final TestParameters parameters) {
        logger.info("Preparing command line for linux time command");

        final List<String> command = (List<String>) parameters.get(ConfigParams.COMMAND_LINE);
        File timeOutputFile;
        try {
            timeOutputFile = File.createTempFile("time", ".txt");
        } catch (final IOException e) {
            logger.error("Caught exception while creating temp file", e);
            throw new UncheckedException(e);
        }

        final List<String> newTimeCommand = new ArrayList<>();

        newTimeCommand.add("/usr/bin/time");
        newTimeCommand.add("-o");
        newTimeCommand.add(timeOutputFile.getAbsolutePath());
        newTimeCommand.add("-f");
        newTimeCommand.add(FIELD_FORMAT_STRING);
        newTimeCommand.addAll(command);

        parameters.put(ConfigParams.COMMAND_LINE, newTimeCommand);
        parameters.put(ConfigParams.TIME_OUTPUT_FILE, timeOutputFile);
    }

}
