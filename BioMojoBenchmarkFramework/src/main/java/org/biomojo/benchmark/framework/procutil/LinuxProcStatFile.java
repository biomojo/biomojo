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
package org.biomojo.benchmark.framework.procutil;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.regex.Pattern;

import org.java0.core.exception.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class LinuxProcStatFile implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(LinuxProcStatFile.class.getName());

    private final int pid;
    private boolean invalidPid = false;
    private RandomAccessFile statFile;

    private static final Pattern splitPattern = Pattern.compile("\\s+");
    private static final Path procDir = FileSystems.getDefault().getPath(LinuxProcConst.PROC_DIR);

    public LinuxProcStatFile(final int pid) {
        this.pid = pid;
    }

    private void openStatFile() throws FileNotFoundException {

        if (statFile == null) {
            final Path procFilePath = procDir.resolve(Integer.toString(pid)).resolve(LinuxProcConst.PROCESS_STAT_FILE);
            statFile = new RandomAccessFile(procFilePath.toFile(), "r");
        }
    }

    public LinuxProcessInfo getInfo() {
        String line = null;
        LinuxProcessInfo linuxProcessInfo = null;

        if (!invalidPid) {
            try {
                openStatFile();
                statFile.seek(0);
                line = statFile.readLine();
                logger.trace("proc stat line: {}", line);
                linuxProcessInfo = new LinuxProcessInfo(splitPattern.split(line));
            } catch (final ParseException e) {
                logger.error("Parse exception", e);
                logger.error("Unable to parse proc/stat line: {}", line);
            } catch (final FileNotFoundException e) {
                logger.info("Process {} went away", pid);
                invalidPid = true;
            } catch (final IOException e) {
                // We get here with an "java.io.IOException: No such process" if
                // we tried to read the process file for a process that no
                // longer
                // exists. Unfortunately, we can't easily tell if the
                // IOException
                // was the
                // result of something more unusual.
                // For now, we'll just treat is as a normal process
                // disappearance
                logger.info("Process {} went away", pid);
                invalidPid = true;
            }
        }

        return linuxProcessInfo;
    }

    /**
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        if (statFile != null) {
            statFile.close();
        }
        statFile = null;
    }

}
