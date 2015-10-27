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

package org.biomojo.project;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Version;

import org.biomojo.core.AbstractPropertiedEntity;
import org.biomojo.core.CommonProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class Project.
 */
@Entity
public class Project extends AbstractPropertiedEntity {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The description. */
    protected String description;

    /** The version. */
    @Version
    protected long version;

    /** The last update. */
    protected Date lastUpdate;

    /**
     * Instantiates a new project.
     */
    public Project() {

    }

    /**
     * Instantiates a new project.
     *
     * @param name the name
     */
    public Project(String name) {
        setProp(CommonProperties.NAME, name);
    }

    /* (non-Javadoc)
     * @see org.biomojo.core.AbstractEntity#getId()
     */
    @Override
    public long getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see org.biomojo.core.AbstractEntity#setId(long)
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the last update.
     *
     * @return the last update
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last update.
     *
     * @param lastUpdate the new last update
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
