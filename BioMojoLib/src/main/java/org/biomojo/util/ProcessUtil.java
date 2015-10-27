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

package org.biomojo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessUtil {
    private static final Logger logger = LoggerFactory.getLogger(ProcessUtil.class.getName());

    public static Process executeProcess(String[] params) {
        try {
            logger.info("Running process, command line params = " + Arrays.toString(params));

            ProcessBuilder pb = new ProcessBuilder(params);
            Process pr = pb.start();

            final BufferedReader errorReader = new BufferedReader(new InputStreamReader(pr.getErrorStream()));

            /*
             * This thread reads the stderr output of the process. If we don't
             * do this, the process will hang.
             */
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        for (String line = errorReader.readLine(); line != null; line = errorReader.readLine()) {
                            logger.info("Subprocess stderr output: " + line);
                        }
                        errorReader.close();
                    } catch (IOException e) {
                        logger.error("Error reading sub-process stderr: " + e.toString());
                    }
                }
            };
            thread.start();

            return pr;
        } catch (IOException e) {
            throw new UncheckedException(e);
        }
    }

    public static int executeProcessWithExitCode(String[] params) {

        try {
            Process pr = executeProcess(params);

            BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }

            try {
                pr.waitFor();
            } catch (InterruptedException e) {
            }

            return pr.exitValue();
        } catch (IOException e) {
            throw new UncheckedException(e);
        }

    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

}
