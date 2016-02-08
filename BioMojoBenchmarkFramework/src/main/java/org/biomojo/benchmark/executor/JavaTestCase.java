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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugh Eaves
 *
 */
public class JavaTestCase extends TestCase {
    int javaMem;

    /**
     * Create a new JavaTestCase.
     *
     * @param benchmark
     * @param library
     */
    public JavaTestCase(final Benchmark benchmark, final Library library) {
        super(benchmark, library);
    }

    /**
     * @see org.biomojo.benchmark.executor.TestCase#getCommandLine(java.util.List)
     */
    @Override
    public List<String> getCommandLine() {
        final List<String> commandLine = startCommandLine();

        if (config.get(ConfigParams.INPUT_FILE) != null) {
            commandLine.add("-i");
            commandLine.add(config.get(ConfigParams.INPUT_FILE));
        }
        if (config.get(ConfigParams.OUTPUT_FILE) != null) {
            commandLine.add("-o");
            commandLine.add(config.get(ConfigParams.OUTPUT_FILE));
        }
        if (config.get(ConfigParams.CUTOFF) != null) {
            commandLine.add("-q");
            commandLine.add(config.get(ConfigParams.CUTOFF));
        }
        if (config.get(ConfigParams.ENCODED) != null) {
            commandLine.add("-e");
        }
        return commandLine;
    }

    /**
     * @return
     */
    protected List<String> startCommandLine() {
        final List<String> commandLine = new ArrayList<String>();
        commandLine.add("java");
        if (config.get(ConfigParams.JVM_OPTS) != null) {
            final String jvmOpts = config.get(ConfigParams.JVM_OPTS);
            final String[] opts = jvmOpts.split("\\s+");
            for (final String opt : opts) {
                commandLine.add(opt);
            }
        }
        // if (config.get(ConfigParams.GC_LOG_FILE) != null) {
        // commandLine.add("-Xloggc:" + config.get(ConfigParams.GC_LOG_FILE));
        // commandLine.add("-XX:+PrintGCDetails");
        // }
        commandLine.add("-jar");
        commandLine.add(config.get(ConfigParams.PROGRAM_BASE_DIR) + "/" + config.get(ConfigParams.PROGRAM_SUB_DIR) + "/"
                + config.get(ConfigParams.PROGRAM_NAME));
        addBenchmarkName(commandLine);
        return commandLine;
    }

    /**
     * @param commandLine
     */
    protected void addBenchmarkName(final List<String> commandLine) {
        commandLine.add(benchmark.toString());
    }
}
