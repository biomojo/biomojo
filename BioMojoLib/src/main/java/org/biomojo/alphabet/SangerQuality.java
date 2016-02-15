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

/**
 * The Class SangerQuality.
 *
 * @author hugh
 * 
 *         Valid range: 33 - 126 (i.e. Phred score + 33)
 */
public class SangerQuality extends AbstractByteQuality {

    /**
     * Instantiates a new sanger quality score alphabet.
     */
    public SangerQuality() {
        super(AlphabetId.QUALITY_SANGER, 33, 126);
    }

    @Override
    public byte getCanonical(final byte symbol) {
        return symbol;
    }

    @Override
    public byte[] getCanonical(final byte[] symbols, final int start, final int end) {
        return symbols;
    }

    @Override
    public boolean isCanonical(final byte symbol) {
        return isValid(symbol);
    }

    @Override
    public boolean isCanonical() {
        return true;
    }
}
