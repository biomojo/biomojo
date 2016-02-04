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
 * The Class DefaultPropertyMapManager.
 *
 * @author Hugh Eaves
 */
public class DefaultPropertyMapManager implements PropertyMapManager {

    /** The Constant INSTANCE. */
    public static final DefaultPropertyMapManager INSTANCE = new DefaultPropertyMapManager();

    /**
     * Instantiates a new default property map manager.
     */
    private DefaultPropertyMapManager() {

    }

    /**
     * Put.
     *
     * @param <T>
     *            the generic type
     * @param properties
     *            the properties
     * @param key
     *            the key
     * @param value
     *            the value
     * @return the t
     * @see org.biomojo.property.PropertyMapManager#put(java.util.Map,
     *      java.lang.String, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T put(final Map<String, Object> properties, final String key, final Object value) {
        return (T) properties.put(key, value);
    }

    /**
     * Gets the.
     *
     * @param <T>
     *            the generic type
     * @param properties
     *            the properties
     * @param key
     *            the key
     * @return the t
     * @see org.biomojo.property.PropertyMapManager#get(java.util.Map,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Map<String, Object> properties, final String key) {
        return (T) properties.get(key);
    }

    /**
     * Removes the.
     *
     * @param <T>
     *            the generic type
     * @param properties
     *            the properties
     * @param key
     *            the key
     * @return the t
     * @see org.biomojo.property.PropertyMapManager#remove(java.util.Map,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T remove(final Map<String, Object> properties, final String key) {
        return (T) properties.remove(key);
    }

    @Override
    public Map<String, Object> convert(final Map<String, Object> properties) {
        return properties;
    }
}
