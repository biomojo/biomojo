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

/**
 * @author Hugh Eaves
 *
 */
public interface ConfigParams {
    public static final String JAVA_MEM = "JAVA_MEM";
    public static final String GC_LOG_FILE = "GC_LOG_FILE";
    public static final String PROGRAM_NAME = "PROGRAM_NAME";
    public static final String PROGRAM_BASE_DIR = "PROGRAM_BASE_DIR";
    public static final String INTERPRETER = "INTERPRETER";
    public static final String PROGRAM_SUB_DIR = "PROGRAM_SUB_DIR";
    public static final String ENCODED = "ENCODED";
    public static final String CUTOFF = "CUTOFF";
    public static final String INPUT_FILE = "INPUT_FILE";
    public static final String OUTPUT_FILE = "OUTPUT_FILE";
    public static final String SEQUENCE_LENGTH = "SEQUENCE_LENGTH";
    public static final String NUM_SEQUENCES = "NUM_SEQUENCES";
}
