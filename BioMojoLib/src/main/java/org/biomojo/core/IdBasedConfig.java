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
package org.biomojo.core;

import org.java0.factory.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class IdBasedConfig.
 *
 * @author Hugh Eaves
 * @param <T> the generic type
 */
public class IdBasedConfig<T> implements Config<T> {
    
    /** The id. */
    public int id;

    /**
     * Create a new IdBasedConfig.
     *
     * @param id the id
     */
    public IdBasedConfig(int id) {
        this.id = id;
    }

    /**
     * Values.
     *
     * @return the object[]
     * @see org.java0.factory.Config#values()
     */
    @Override
    public Object[] values() {
        return new Object[] { id };
    }

    /**
     * Gets the value.
     *
     * @param key the key
     * @return the value
     * @see org.java0.factory.Config#getValue(java.lang.String)
     */
    @Override
    public Object getValue(Object key) {
        return null;
    }

}
