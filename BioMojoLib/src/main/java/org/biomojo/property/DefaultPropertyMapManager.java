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
package org.biomojo.property;

import java.util.Map;

/**
 * @author Hugh Eaves
 *
 */
public class DefaultPropertyMapManager implements PropertyMapManager {
    public static final DefaultPropertyMapManager INSTANCE = new DefaultPropertyMapManager();

    private DefaultPropertyMapManager() {

    }

    /**
     * @see org.biomojo.property.PropertyMapManager#put(java.util.Map,
     *      java.lang.String, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T put(Map<String, Object> properties, String key, Object value) {
        return (T) properties.put(key, value);
    }

    /**
     * @see org.biomojo.property.PropertyMapManager#get(java.util.Map,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Map<String, Object> properties, String key) {
        return (T) properties.get(key);
    }

    /**
     * @see org.biomojo.property.PropertyMapManager#remove(java.util.Map,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T remove(Map<String, Object> properties, String key) {
        return (T) properties.remove(key);
    }
}
