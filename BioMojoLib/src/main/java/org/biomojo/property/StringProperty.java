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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class StringProperty.
 */
@Entity
@DiscriminatorValue(value = "S")
public class StringProperty extends BasicProperty {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -902224446132501216L;

    /** The string value. */
    private String stringValue;

    /**
     * Instantiates a new string property.
     */
    public StringProperty() {

    }

    /**
     * Instantiates a new string property.
     *
     * @param stringValue the string value
     */
    public StringProperty(String stringValue) {
        this.stringValue = stringValue;
    }

    /* (non-Javadoc)
     * @see org.biomojo.property.BasicProperty#setValue(java.lang.Object)
     */
    @Override
    public void setValue(Object value) {
        setString((String) value);
    }

    /**
     * Sets the string.
     *
     * @param stringValue the new string
     */
    public void setString(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * Gets the string.
     *
     * @return the string
     */
    public String getString() {
        return stringValue;
    }

    /* (non-Javadoc)
     * @see org.biomojo.property.BasicProperty#getValue()
     */
    @Override
    public Object getValue() {
        return stringValue;
    }
}
