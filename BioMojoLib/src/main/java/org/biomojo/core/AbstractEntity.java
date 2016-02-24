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
package org.biomojo.core;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

import org.java0.core.type.LongIdentified;

/**
 * The Class AbstractEntity.
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable, LongIdentified {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @TableGenerator(name = "AbstractEntityGenerator", allocationSize = 100)
    @Id
    @GeneratedValue(generator = "AbstractEntityGenerator")
    protected long id;

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.core.Identified#getId()
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(final long id) {
        this.id = id;
    }
}
