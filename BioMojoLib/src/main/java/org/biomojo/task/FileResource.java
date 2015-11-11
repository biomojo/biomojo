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
package org.biomojo.task;

import javax.persistence.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class FileResource.
 */
@Entity
public class FileResource extends NamedResource {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The file store. */
    private FileStore fileStore;

    /** The path. */
    private String path;

    /** The status. */
    private ResourceStatus status;

    /** The name. */
    private String name;

    /**
     * Gets the file store.
     *
     * @return the file store
     */
    public FileStore getFileStore() {
        return fileStore;
    }

    /**
     * Sets the file store.
     *
     * @param fileStore the new file store
     */
    public void setFileStore(FileStore fileStore) {
        this.fileStore = fileStore;
    }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path.
     *
     * @param path the new path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public ResourceStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(ResourceStatus status) {
        this.status = status;
    }
}
