/*
 * Copyright (C) 2015  Hugh Eaves
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.biomojo.benchmark.executor;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class LinuxProcStat implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(LinuxProcStat.class.getName());

    private String[] fieldValues;

    private RandomAccessFile statFile;

    private int pid;

    private static final Pattern splitPattern = Pattern.compile("\\s+");

    public LinuxProcStat(Process process) {
        try {
            // grab the private pid field via reflection
            Field pidField = process.getClass().getDeclaredField("pid");
            pidField.setAccessible(true);
            pid = (Integer) pidField.get(process);
            statFile = new RandomAccessFile("/proc/" + pid + "/stat", "r");
        } catch (FileNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException
                | IllegalAccessException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }
    }

    public Map<LinuxProcStatField, Object> getInfo() throws IOException {
        if (statFile != null) {
            try {
                statFile.seek(0);
                String line = statFile.readLine();
                logger.info("proc stat line: {}", line);
                fieldValues = splitPattern.split(line);
                Map<LinuxProcStatField, Object> result = new HashMap<>();
                for (LinuxProcStatField field : LinuxProcStatField.values()) {
                    logger.info("field {} : {}", field, fieldValues[field.ordinal()]);
                    result.put(field, field.getValue(fieldValues));
                }
                return result;
            } catch (IOException e) {
                logger.error("Caught exception in auto-generated catch block", e);
            }
        }
        return null;
    }

    /**
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        statFile.close();
    }

}
