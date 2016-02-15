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
package org.biomojo.core;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating IdBased objects.
 *
 * @author Hugh Eaves
 * @param <T>
 *            the generic type
 */
public interface IdBasedFactory<T extends IntegerIdentified> {

    /**
     * Gets the single instance of IdBasedFactory.
     * 
     * @param type
     *            the type
     * @param objectId
     *            the object id
     *
     * @param <Z>
     *            the generic type
     * @return single instance of IdBasedFactory
     */
    public <Z extends T> Z getInstance(Class<Z> type, int objectId);

}
