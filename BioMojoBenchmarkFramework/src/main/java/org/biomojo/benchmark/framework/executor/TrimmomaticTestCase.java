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
package org.biomojo.benchmark.framework.executor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class TrimmomaticTestCase extends JavaTestCase {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(TrimmomaticTestCase.class.getName());

    /**
     * Create a new TrimmomaticTestCase.
     *
     * @param benchmark
     * @param library
     */
    public TrimmomaticTestCase(Benchmark benchmark, Library library) {
        super(benchmark, library);

    }

    /**
     * @see org.biomojo.benchmark.executor.TestCase#getCommandLine()
     */
    @Override
    public List<String> getCommandLine() {
        List<String> commandLine = startCommandLine();

        commandLine.add("SE");
        commandLine.add("-phred33");
        commandLine.add(config.get(ConfigParams.INPUT_FILE));
        commandLine.add(config.get(ConfigParams.OUTPUT_FILE));
        commandLine.add("TRAILING:" + config.get(ConfigParams.CUTOFF));

        return commandLine;
    }

    @Override
    protected void addBenchmarkName(List<String> commandLine) {

    }
}
