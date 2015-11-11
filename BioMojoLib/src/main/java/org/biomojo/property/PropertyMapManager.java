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

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Interface PropertyMapManager.
 *
 * @author Hugh Eaves
 */
public interface PropertyMapManager {
    
    /**
     * Put.
     *
     * @param <T> the generic type
     * @param properties the properties
     * @param key the key
     * @param value the value
     * @return the t
     */
    public <T> T put(Map<String, Object> properties, String key, Object value);

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param properties the properties
     * @param key the key
     * @return the t
     */
    public <T> T get(Map<String, Object> properties, String key);

    /**
     * Removes the.
     *
     * @param <T> the generic type
     * @param properties the properties
     * @param key the key
     * @return the t
     */
    public <T> T remove(Map<String, Object> properties, String key);
}
