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
package org.biomojo.benchmark.biomojo;

import org.java0.cli.FileInputCommand;

import com.beust.jcommander.Parameter;

/**
 * @author Hugh Eaves
 *
 */
public abstract class BaseInputCommand extends FileInputCommand {
    @Parameter(names = { "-e", "--encode" }, description = "Use two bit encoding for sequences", arity = 1)
    protected boolean encode = false;

}
