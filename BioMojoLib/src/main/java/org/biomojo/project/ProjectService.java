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

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.biomojo.core.CommonProperties;
import org.biomojo.util.DbUtil;

@Named
public class ProjectService {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private DbUtil dbUtil;

    @Transactional
    public Project createProject(String name, String description) {

        // Create the Project only if it doesn't already exist
        Project project = dbUtil.findByAttribute(Project.class,
                CommonProperties.NAME, name);

        if (project == null) {
            project = new Project(name);
            if (description == null || "".equals(description)) {
                project.setDescription("No description provided");
            } else {
                project.setDescription(description);
            }

            // persist project object
            entityManager.persist(project);

        }

        return project;

    }
}
