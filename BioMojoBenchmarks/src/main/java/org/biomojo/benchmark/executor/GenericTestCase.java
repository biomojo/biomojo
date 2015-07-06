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
package org.biomojo.benchmark.executor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class GenericTestCase extends TestCase {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(GenericTestCase.class.getName());

	/**
	 * Create a new GenericTestCase.
	 *
	 * @param benchmark
	 * @param library
	 */
	public GenericTestCase(Benchmark benchmark, Library library) {
		super(benchmark, library);

	}

	/**
	 * @see org.biomojo.benchmark.executor.TestCase#getCommandLine(java.util.List)
	 */
	@Override
	public List<String> getCommandLine() {
		List<String> commandLine = new ArrayList<String>();
		if (config.get(ConfigParams.INTERPRETER) != null) {
			commandLine.add((String) config.get(ConfigParams.INTERPRETER));
		}

		commandLine.add(config.get(ConfigParams.PROGRAM_BASE_DIR) + "/"
				+ config.get(ConfigParams.PROGRAM_SUB_DIR) + "/"
				+ config.get(ConfigParams.PROGRAM_NAME));

		commandLine.add(benchmark.toString());

		if (config.get(ConfigParams.INPUT_FILE) != null) {
			commandLine.add(config.get(ConfigParams.INPUT_FILE));
		}
		if (config.get(ConfigParams.OUTPUT_FILE) != null) {
			commandLine.add(config.get(ConfigParams.OUTPUT_FILE));
		}
		if (config.get(ConfigParams.CUTOFF) != null) {
			commandLine.add(config.get(ConfigParams.CUTOFF));
		}
		return commandLine;
	}
}
