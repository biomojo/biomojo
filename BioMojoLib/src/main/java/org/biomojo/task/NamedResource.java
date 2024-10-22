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

import org.java0.core.type.NamedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class NamedResource.
 *
 * @author Hugh Eaves
 */
public class NamedResource extends Resource implements NamedObject {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4996505227539819704L;

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(NamedResource.class.getName());

    /** The name. */
    protected String name;

    /* (non-Javadoc)
     * @see org.java0.core.type.NamedObject#getName()
     */
    @Override
    public String getName() {
        return name;
    }
}
