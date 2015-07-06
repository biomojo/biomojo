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

@Entity
public class FileStore extends NamedResource {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private FileSystemPath defaultPath;

	@ManyToMany
	private Map<NodeGroup, FileSystemPath> pathOverrides;

	public FileSystemPath getDefaultPath() {
		return defaultPath;
	}

	public void setDefaultPath(FileSystemPath defaultPath) {
		this.defaultPath = defaultPath;
	}

	public Map<NodeGroup, FileSystemPath> getPathOverrides() {
		return pathOverrides;
	}

	public void setPathOverrides(Map<NodeGroup, FileSystemPath> pathOverrides) {
		this.pathOverrides = pathOverrides;
	}

}
