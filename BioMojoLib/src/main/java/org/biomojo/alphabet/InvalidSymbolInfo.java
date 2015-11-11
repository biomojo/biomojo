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
package org.biomojo.alphabet;

import org.java0.string.ASCII;

// TODO: Auto-generated Javadoc
/**
 * The Class InvalidSymbolInfo.
 *
 * @author Hugh Eaves
 */
public class InvalidSymbolInfo {
    
    /** The symbol. */
    private final Object symbol;

    /** The position. */
    private final Integer position;

    /**
     * Instantiates a new invalid symbol info.
     *
     * @param symbol the symbol
     * @param position the position
     */
    public InvalidSymbolInfo(final Object symbol, final Integer position) {
        super();
        this.symbol = symbol;
        this.position = position;
    }

    /**
     * Instantiates a new invalid symbol info.
     *
     * @param symbol the symbol
     */
    public InvalidSymbolInfo(final Object symbol) {
        super();
        this.symbol = symbol;
        position = null;
    }

    /**
     * Gets the symbol.
     *
     * @return the symbol
     */
    public Object getSymbol() {
        return symbol;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Object printableSymbol = symbol;
        if (symbol instanceof Byte) {
            final byte ch = ((Byte) symbol);
            if (ASCII.isPrintable((Byte) symbol)) {
                printableSymbol = new Character((char) ch);
            } else {
                printableSymbol = "byte value(" + ch + ")";
            }
        }
        if (position == null) {
            return "Invalid symbol [" + printableSymbol.toString() + "] @ unspecified position";
        } else {
            return "Invalid symbol [" + printableSymbol.toString() + "] @ position " + position;
        }
    }
}
