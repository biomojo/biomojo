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
package org.biomojo.property;

// TODO: Auto-generated Javadoc
/**
 * The Class IntegerProperty.
 *
 * @author Hugh Eaves
 */
public class IntegerProperty extends NumericProperty {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1448961994278386666L;

    /** The int value. */
    protected int intValue;

    /**
     * Instantiates a new integer property.
     */
    public IntegerProperty() {
    }

    /**
     * Instantiates a new integer property.
     *
     * @param intValue the int value
     */
    public IntegerProperty(int intValue) {
        this.intValue = intValue;
    }

    /* (non-Javadoc)
     * @see org.biomojo.property.BasicProperty#setValue(java.lang.Object)
     */
    @Override
    public void setValue(Object value) {
        setInteger((int) value);
    }

    /* (non-Javadoc)
     * @see org.biomojo.property.BasicProperty#getValue()
     */
    @Override
    public Object getValue() {
        return intValue;
    }

    /**
     * Sets the integer.
     *
     * @param intValue the new integer
     */
    public void setInteger(int intValue) {
        this.intValue = intValue;
    }
}
