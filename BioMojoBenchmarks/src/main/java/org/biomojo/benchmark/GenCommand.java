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
package org.biomojo.benchmark;

import org.java0.cli.AbstractCommand;
import org.java0.cli.OutputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;

/**
 * @author Hugh Eaves
 *
 */

public abstract class GenCommand extends AbstractCommand {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(GenCommand.class.getName());

    @Parameter(names = { "-n", "--numseqs" }, description = "Number of sequences", required = true)
    protected Integer numSeqs;

    @Parameter(names = { "-l", "--seqlength" }, description = "Length of sequences", required = true)
    protected Integer seqLength;

    @Parameter(names = { "-o", "--out" }, description = "Output file name", required = true)
    protected OutputFile outputFile;
}
