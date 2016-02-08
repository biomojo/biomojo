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

import org.biomojo.BioMojo;
import org.biomojo.benchmark.commands.AlignCommand;
import org.biomojo.benchmark.commands.CountKmersCommand;
import org.biomojo.benchmark.commands.GenFastaCommand;
import org.biomojo.benchmark.commands.GenFastqCommand;
import org.biomojo.benchmark.commands.LoadFastaCommand;
import org.biomojo.benchmark.commands.LoadFastqCommand;
import org.biomojo.benchmark.commands.ReadFastaCommand;
import org.biomojo.benchmark.commands.ReadFastqCommand;
import org.biomojo.benchmark.commands.TranslateCommand;
import org.biomojo.benchmark.commands.TrimCommand;
import org.biomojo.benchmark.executor.ExecuteCommand;
import org.biomojo.tools.fast5.ConvertFast5Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * @author Hugh Eaves
 *
 */
public class Main {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());

    public static void main(final String[] args) {
        BioMojo.init(args, new LoadFastaCommand(), new LoadFastqCommand(), new ReadFastaCommand(),
                new ReadFastqCommand(), new TrimCommand(), new AlignCommand(), new GenFastaCommand(),
                new GenFastqCommand(), new ExecuteCommand(), new TranslateCommand(), new CountKmersCommand(),
                new ConvertFast5Command());
    }
}
