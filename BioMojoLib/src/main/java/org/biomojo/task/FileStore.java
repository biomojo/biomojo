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
package org.biomojo.task;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

// TODO: Auto-generated Javadoc
/**
 * The Class FileStore.
 */
@Entity
public class FileStore extends NamedResource {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The default path. */
    @ManyToOne
    private FileSystemPath defaultPath;

    /** The path overrides. */
    @ManyToMany
    private Map<NodeGroup, FileSystemPath> pathOverrides;

    /**
     * Gets the default path.
     *
     * @return the default path
     */
    public FileSystemPath getDefaultPath() {
        return defaultPath;
    }

    /**
     * Sets the default path.
     *
     * @param defaultPath the new default path
     */
    public void setDefaultPath(FileSystemPath defaultPath) {
        this.defaultPath = defaultPath;
    }

    /**
     * Gets the path overrides.
     *
     * @return the path overrides
     */
    public Map<NodeGroup, FileSystemPath> getPathOverrides() {
        return pathOverrides;
    }

    /**
     * Sets the path overrides.
     *
     * @param pathOverrides the path overrides
     */
    public void setPathOverrides(Map<NodeGroup, FileSystemPath> pathOverrides) {
        this.pathOverrides = pathOverrides;
    }

}
