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
package org.biomojo.benchmark.framework;

import org.biomojo.BioMojo;
import org.biomojo.benchmark.framework.commands.GenFastaCommand;
import org.biomojo.benchmark.framework.commands.GenFastqCommand;
import org.biomojo.benchmark.framework.executor.ExecuteCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

/*
 * @author Hugh Eaves
 *
 */
public class Main {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());

    public static void main(final String[] args) {
        final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // print logback's internal status
        StatusPrinter.print(lc);

        BioMojo.init(args, new GenFastaCommand(), new GenFastqCommand(), new ExecuteCommand());
    }
}
