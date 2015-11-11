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
 * The Class LongProperty.
 */
@Entity
@DiscriminatorValue(value = "L")
public class LongProperty extends NumericProperty {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1448961994278386666L;

    /** The long value. */
    protected long longValue;

    /**
     * Instantiates a new long property.
     */
    public LongProperty() {
    }

    /**
     * Instantiates a new long property.
     *
     * @param longValue the long value
     */
    public LongProperty(long longValue) {
        this.longValue = longValue;
    }

    /* (non-Javadoc)
     * @see org.biomojo.property.BasicProperty#setValue(java.lang.Object)
     */
    @Override
    public void setValue(Object value) {
        setLong((long) value);
    }

    /* (non-Javadoc)
     * @see org.biomojo.property.BasicProperty#getValue()
     */
    @Override
    public Object getValue() {
        return longValue;
    }

    /**
     * Sets the long.
     *
     * @param longValue the new long
     */
    public void setLong(long longValue) {
        this.longValue = longValue;
    }

    /**
     * Gets the long.
     *
     * @return the long
     */
    public long getLong() {
        return longValue;
    }

}
