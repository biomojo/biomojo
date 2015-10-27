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

package org.biomojo.property;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class DoubleProperty.
 */
@Entity
@DiscriminatorValue(value = "D")
public class DoubleProperty extends NumericProperty {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2134118530015957486L;

    /** The double value. */
    protected double doubleValue;

    /**
     * Instantiates a new double property.
     */
    public DoubleProperty() {

    }

    /**
     * Instantiates a new double property.
     *
     * @param doubleValue the double value
     */
    public DoubleProperty(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    /* (non-Javadoc)
     * @see org.biomojo.property.BasicProperty#setValue(java.lang.Object)
     */
    @Override
    public void setValue(Object value) {
        setDouble((double) value);
    }

    /* (non-Javadoc)
     * @see org.biomojo.property.BasicProperty#getValue()
     */
    @Override
    public Object getValue() {
        return doubleValue;
    }

    /**
     * Sets the double.
     *
     * @param doubleValue the new double
     */
    public void setDouble(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    /**
     * Gets the double.
     *
     * @return the double
     */
    public double getDouble() {
        return doubleValue;
    }

}
