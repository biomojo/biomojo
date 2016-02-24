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

import org.biomojo.BioMojo;

public class AbstractByteQuality extends AbstractCanonicalizableByteAlphabet<SangerQuality> implements ByteQuality {

    /** The start. */
    private final int minValue;

    /** The end. */
    private final int maxValue;

    private SangerQuality canonicalAlphabet;

    protected AbstractByteQuality(final long id, final int start, final int end) {
        super(id);
        this.minValue = start;
        this.maxValue = end;
    }

    @Override
    public int numSymbols() {
        return maxValue - minValue;
    }

    @Override
    public int numCanonicalSymbols() {
        return 0;
    }

    @Override
    public int getOrdinalForSymbol(final byte value) {
        return value - minValue;
    }

    @Override
    public byte getByteSymbolForOrdinal(final int ordinal) {
        return (byte) ((minValue + ordinal) & 0xff);
    }

    @Override
    public boolean isValid(final byte symbol) {
        return (symbol >= minValue && symbol < maxValue);
    }

    @Override
    public boolean isValid(final byte[] symbols, final int start, final int end) {
        for (final byte symbol : symbols) {
            if (symbol < start && symbol >= end) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void validate(final byte[] symbols, final int start, final int end) throws InvalidSymbolException {
        for (int i = start; i < end; ++i) {
            if (symbols[i] < start && symbols[i] >= end)
                throw new InvalidSymbolException(symbols[i], i);
        }
        return;
    }

    @Override
    public byte getMinValue() {
        return (byte) minValue;
    }

    @Override
    public byte getMaxValue() {
        return (byte) maxValue;
    }

    @Override
    public byte getCanonical(final byte symbol) {
        final int canonicalMinValue = getCanonical().getMinValue();
        final int diff = this.getMinValue() - canonicalMinValue;
        return (byte) (symbol - diff);
    }

    @Override
    public byte[] getCanonical(final byte[] symbols, final int start, final int end) {
        final int canonicalMinValue = getCanonical().getMinValue();
        final int diff = this.getMinValue() - canonicalMinValue;
        final byte[] canonicals = new byte[symbols.length];
        int pos = 0;
        for (final byte symbol : symbols) {
            canonicals[pos++] = (byte) (symbol - diff);
        }
        return canonicals;
    }

    @Override
    public boolean isCanonical(final byte symbol) {
        return false;
    }

    @Override
    public boolean isCanonical() {
        return false;
    }

    @Override
    public SangerQuality getCanonical() {
        if (canonicalAlphabet == null) {
            canonicalAlphabet = BioMojo.getObject(SangerQuality.class, AlphabetId.QUALITY_SANGER);
        }
        return canonicalAlphabet;
    }
}