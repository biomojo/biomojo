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
package org.biomojo.benchmark.executor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class LinuxTimeCommand {
    private static final Logger logger = LoggerFactory.getLogger(LinuxTimeCommand.class.getName());

    private File timeOutputFile;

    public List<String> prepareCommandLine(List<String> command) {
        try {
            timeOutputFile = File.createTempFile("time", ".txt");
        } catch (IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
            return command;
        }

        List<String> newTimeCommand = new ArrayList<>();

        newTimeCommand.add("/usr/bin/time");
        newTimeCommand.add("-o");
        newTimeCommand.add(timeOutputFile.getAbsolutePath());
        newTimeCommand.add("-f");
        List<String> fields = new ArrayList<>();
        for (LinuxTimeCommandField field : LinuxTimeCommandField.values()) {
            fields.add("%" + field.getFieldCode());
        }
        newTimeCommand.add(String.join("^", fields));
        newTimeCommand.addAll(command);

        return newTimeCommand;
    }

    public Map<LinuxTimeCommandField, Object> getExecutionResults() {
        List<String> lines;
        try {
            lines = Files.readAllLines(timeOutputFile.toPath());
            timeOutputFile.delete();
        } catch (IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
            return null;
        }

        logger.info("time command result: {} ", lines.get(0));

        String[] parsed = lines.get(0).split("\\^");

        if (parsed.length == LinuxTimeCommandField.values().length) {
            Map<LinuxTimeCommandField, Object> result = new HashMap<>();
            for (LinuxTimeCommandField field : LinuxTimeCommandField.values()) {
                result.put(field, field.getValue(parsed));
            }
            return result;
        } else {
            logger.error("missing fields in parsed time command result {} != {}", parsed.length,
                    LinuxTimeCommandField.values().length);

        }

        return null;
    }
}
