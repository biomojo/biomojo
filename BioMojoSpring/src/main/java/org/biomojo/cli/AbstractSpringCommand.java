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
package org.biomojo.cli;

import java.io.Closeable;
import java.io.IOException;

import org.biomojo.GlobalConst;
import org.biomojo.hacks.SpringDependencyHack;
import org.java0.cli.AbstractCommand;
import org.java0.cli.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The Class AbstractSpringCommand.
 */
public abstract class AbstractSpringCommand extends AbstractCommand implements SpringCommand {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AbstractSpringCommand.class.getName());

    /** The config location. */
    private final String configLocation;

    private static SpringDependencyHack unused;

    /** The context. */
    private AbstractApplicationContext applicationContext;

    public AbstractSpringCommand() {
        this.configLocation = GlobalConst.LIB_SPRING_CONTEXT;
    }

    /**
     * Instantiates a new abstract spring command.
     *
     * @param configLocation
     *            the config location
     */
    public AbstractSpringCommand(final String configLocation) {
        this.configLocation = configLocation;
    }

    @Override
    public String getConfigLocation() {
        return configLocation;
    }

    /**
     * @see org.biomojo.cli.SpringCommand#getApplicationContext()
     */
    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public Command prepare() {
        applicationContext = new ClassPathXmlApplicationContext(getConfigLocation());
        applicationContext.registerShutdownHook();
        final AbstractSpringCommand command = applicationContext.getBean(getClass());
        command.applicationContext = applicationContext;
        return command;

    }

    @Override
    public void finish() {
        if (getApplicationContext() instanceof Closeable) {
            try {
                ((Closeable) getApplicationContext()).close();
            } catch (final IOException e) {
                logger.error("Caught exception in auto-generated catch block", e);
            }
        }
    }
}
