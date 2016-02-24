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
package org.biomojo;

import org.biomojo.alphabet.DefaultAlphabets;
import org.biomojo.codec.DefaultCodecs;
import org.biomojo.codon.DefaultCodonTables;
import org.java0.cli.CLIUtil;
import org.java0.cli.Command;
import org.java0.factory.FactoryException;
import org.java0.factory.LongSelectorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class BioMojo.
 *
 * @author Hugh Eaves
 */
public class BioMojo {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(BioMojo.class.getName());

    private static final LongSelectorFactory factory = new LongSelectorFactory(GlobalConst.GROUP_ID_BITS);

    private static boolean initialized = false;

    /**
     * Instantiates a new bio mojo.
     */
    private BioMojo() {

    }

    public static void init() {
        if (!initialized) {
            initialized = true;
            DefaultAlphabets.registerWithFactory(factory);
            DefaultCodecs.registerWithFactory(factory);
            DefaultCodonTables.registerWithFactory(factory);
        }
    }

    /**
     * Inits the.
     *
     * @param args
     *            the args
     * @param commands
     *            the commands
     */
    public static void init(final String[] args, final Command... commands) {
        try {
            init();
            CLIUtil.processCommandLine(args, commands);
        } catch (final OutOfMemoryError e) {
            e.printStackTrace();
            System.err.println("BioMojo: out of memory");
            System.exit(GlobalConst.OUT_OF_MEMORY_EXIT_CODE);
        }
    }

    public static <T> T getObject(final Class<T> type, final long selector) throws FactoryException {
        return factory.getObject(type, selector);
    }

    public static <T> T getObject(final Class<T> type) throws FactoryException {
        return factory.getObject(type);
    }

}
