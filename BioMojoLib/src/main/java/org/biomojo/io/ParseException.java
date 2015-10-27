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
package org.biomojo.io;

import org.java0.core.exception.UncheckedException;

// TODO: Auto-generated Javadoc
/**
 * The Class ParseException.
 */
public class ParseException extends UncheckedException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new parses the exception.
     */
    public ParseException() {
        super();

    }

    /**
     * Instantiates a new parses the exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public ParseException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }

    /**
     * Instantiates a new parses the exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public ParseException(final String message, final Throwable cause) {
        super(message, cause);

    }

    /**
     * Instantiates a new parses the exception.
     *
     * @param message the message
     */
    public ParseException(final String message) {
        super(message);

    }

    /**
     * Instantiates a new parses the exception.
     *
     * @param cause the cause
     */
    public ParseException(final Throwable cause) {
        super(cause);

    }

}
