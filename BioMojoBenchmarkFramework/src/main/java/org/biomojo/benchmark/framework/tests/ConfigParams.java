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
package org.biomojo.benchmark.framework.tests;

/**
 * @author Hugh Eaves
 *
 */
public interface ConfigParams {
    public static final String JVM_OPTS = "JVM_OPTS";
    public static final String GC_LOG_FILE = "GC_LOG_FILE";

    public static final String PROGRAM_BASE_DIR = "PROGRAM_BASE_DIR";

    public static final String ENCODED = "ENCODED";
    public static final String CUTOFF = "CUTOFF";

    public static final String DATA_DIR = "DATA_DIR";

    public static final String TESTDATA_FILE_PREFIX = "TESTDATA_FILE_PREFIX";

    public static final String INPUT_FILE_NAME = "INPUT_FILE_NAME";
    public static final String INPUT_FILE = "INPUT_FILE";
    public static final String DELETE_INPUT_FILE = "DELETE_INPUT_FILE";

    public static final String OUTPUT_FILE_PREFIX = "OUTPUT_FILE_PREFIX";
    public static final String DELETE_OUTPUT_FILE = "DELETE_OUTPUT_FILE";
    public static final String OUTPUT_FILE = "OUTPUT_FILE";

    public static final String SEQUENCE_LENGTH = "SEQUENCE_LENGTH";
    public static final String NUM_SEQUENCES = "NUM_SEQUENCES";
    public static final String KMER_LENGTH = "KMER_LENGTH";

    public static final String BENCHMARK_NAME = "BENCHMARK_NAME";

    public static final String LIBRARY = "LIBRARY";
    public static final String BENCHMARK = "BENCHMARK";

    public static final String COMMAND_LINE = "COMMAND_LINE";
    public static final String RUN_NUMBER = "RUN_NUMBER";
    public static final String TIME_OUTPUT_FILE = "TIME_OUTPUT_FILE";
    public static final String LINUX_TIME_COMMAND_INFO = "LINUX_TIME_COMMAND_INFO";
}
