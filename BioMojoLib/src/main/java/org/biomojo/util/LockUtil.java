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
package org.biomojo.util;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class LockUtil.
 */
public class LockUtil {
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(LockUtil.class.getName());

    /** The Constant MAX_ATTEMPTS. */
    private static final int MAX_ATTEMPTS = 1000;

    /** The path. */
    private Path path;
    
    /** The file channel. */
    private FileChannel fileChannel;
    
    /** The file lock. */
    private FileLock fileLock;

    /**
     * Instantiates a new lock util.
     *
     * @param file the file
     */
    public LockUtil(File file) {
        try {
            path = FileSystems.getDefault().getPath(file.getCanonicalPath() + ".lock");
        } catch (IOException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * Lock.
     */
    public void lock() {

        if (fileLock != null) {
            throw new UncheckedException("lock file " + path + " already locked");
        }

        try {
            logger.debug("Creating lock file: " + path);
            fileChannel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new UncheckedException(e);
        }

        for (int i = 1; i <= MAX_ATTEMPTS; ++i) {
            try {
                logger.debug("Attempting lock on lock file: " + path);
                fileLock = fileChannel.lock();
                logger.debug("Obtained lock on lock file: " + path);
                break;
            } catch (IOException e) {
                logger.debug("Lock attempt " + i + ", received IOException: " + e.toString());
                int sleepTime = (5000 * (i - 1)) + 2000;
                logger.debug("Sleeping for " + sleepTime + "ms before next attempt");
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e1) {
                }
            }
        }

        if (fileLock == null) {
            throw new UncheckedException("Unable to obtain lock on lock file: " + path);
        }

    }

    /**
     * Unlock.
     */
    public void unlock() {
        try {
            logger.debug("Releasing lock on lock file: " + path);

            fileLock.release();
            fileChannel.close();

            logger.debug("Released lock on lock file: " + path);

            fileLock = null;
            fileChannel = null;
        } catch (IOException e) {
            throw new UncheckedException(e);
        }
    }
}
