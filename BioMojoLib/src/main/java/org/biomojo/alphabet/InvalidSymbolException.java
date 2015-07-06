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

public class InvalidSymbolException extends UncheckedException {

	private static final long serialVersionUID = 3542399307112927726L;

	private final InvalidSymbolInfo info;

	public InvalidSymbolException(final Object symbol, final int position) {
		this.info = new InvalidSymbolInfo(symbol, position);
	}

	public InvalidSymbolException(final Object symbol) {
		this.info = new InvalidSymbolInfo(symbol, null);

	}

	public InvalidSymbolException(final InvalidSymbolInfo info) {
		this.info = info;
	}

	/**
	 * @return the symbol
	 */
	public Object getSymbol() {
		return info.getSymbol();
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return info.getPosition();
	}

	@Override
	public String getMessage() {
		return info.toString();
	}

}
