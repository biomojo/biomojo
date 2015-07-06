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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public abstract class TestCase {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TestCase.class
			.getName());

	protected final Benchmark benchmark;
	protected final Library library;
	protected final Map<String, String> config = new HashMap<>();

	public Benchmark getBenchmark() {
		return benchmark;
	}

	public Library getLibrary() {
		return library;
	}

	public String get(final String key) {
		return config.get(key);
	}

	public TestCase(final Benchmark benchmark, final Library library) {
		this.library = library;
		this.benchmark = benchmark;
	}

	public TestCase add(final String string, final Object object) {
		config.put(string, object.toString());
		return this;
	}

	public abstract List<String> getCommandLine();

	/**
	 * @return
	 */
	public Map<String, String> getConfig() {
		return config;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Test case:");
		builder.append("\n  Library: ");
		builder.append(library);
		builder.append("\n  Benchmark: ");
		builder.append(benchmark);
		builder.append("\n  Config:");
		for (final Entry<String, String> entry : config.entrySet()) {
			builder.append("\n      ");
			builder.append(entry.getKey());
			builder.append(" = [");
			builder.append(entry.getValue());
			builder.append("]");
		}
		return builder.toString();
	}
}
