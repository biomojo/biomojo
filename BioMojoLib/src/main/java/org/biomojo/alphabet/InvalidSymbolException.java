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
package org.biomojo.alphabet;

import org.java0.core.exception.UncheckedException;

// TODO: Auto-generated Javadoc
/**
 * The Class InvalidSymbolException.
 */
public class InvalidSymbolException extends UncheckedException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3542399307112927726L;

    /** The info. */
    private final InvalidSymbolInfo info;

    /**
     * Instantiates a new invalid symbol exception.
     *
     * @param symbol the symbol
     * @param position the position
     */
    public InvalidSymbolException(final Object symbol, final int position) {
        this.info = new InvalidSymbolInfo(symbol, position);
    }

    /**
     * Instantiates a new invalid symbol exception.
     *
     * @param symbol the symbol
     */
    public InvalidSymbolException(final Object symbol) {
        this.info = new InvalidSymbolInfo(symbol, null);

    }

    /**
     * Instantiates a new invalid symbol exception.
     *
     * @param info the info
     */
    public InvalidSymbolException(final InvalidSymbolInfo info) {
        this.info = info;
    }

    /**
     * Gets the symbol.
     *
     * @return the symbol
     */
    public Object getSymbol() {
        return info.getSymbol();
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Integer getPosition() {
        return info.getPosition();
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        return info.toString();
    }

}
