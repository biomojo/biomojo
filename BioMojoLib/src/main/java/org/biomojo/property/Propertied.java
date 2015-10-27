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

// TODO: Auto-generated Javadoc
/**
 * Interface for an object that has properties.
 *
 * @author Hugh Eaves
 *
 */
public interface Propertied {

    /**
     * Gets the value of the property identified by the key.
     *
     * @param
     *            <P>
     *            the generic type
     * @param key
     *            the key
     * @return the prop
     */
    public <P> P getProp(String key);

    /**
     * Gets the prop.
     *
     * @param
     *            <P>
     *            the generic type
     * @param key
     *            the key
     * @param type
     *            the type
     * @return the prop
     */
    public <P> P getProp(String key, Class<P> type);

    /**
     * Adds the prop.
     *
     * @param
     *            <P>
     *            the generic type
     * @param key
     *            the key
     * @param value
     *            the value
     * @return the t
     */
    public <P> P setProp(String key, Object value);

    /**
     * Removes the prop.
     *
     * @param
     *            <P>
     *            the generic type
     * @param key
     *            the key
     * @return the t
     */
    public <P> P removeProp(String key);
}
