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

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.biomojo.core.AbstractEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicProperty.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("P")
public abstract class BasicProperty extends AbstractEntity {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7469166508807299605L;

    /**
     * Gets the value.
     *
     * @return the value
     */
    public abstract Object getValue();

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public abstract void setValue(Object value);

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof BasicProperty) {
            BasicProperty attribute = (BasicProperty) obj;
            if (getValue().equals(attribute.getValue())) {
                return true;
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
