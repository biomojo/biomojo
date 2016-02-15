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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.java0.core.exception.UncheckedException;
import org.java0.factory.ConfiguredObjectSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating AbstractIdBased objects.
 *
 * @author Hugh Eaves
 * @param <T>
 *            the generic type
 */
public abstract class AbstractIdBasedFactory<T extends IdBasedFactoryObject> implements IdBasedFactory<T> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AbstractIdBasedFactory.class.getName());

    /** The object cache. */
    private T[] objectCache;

    /** The providers. */
    private final Map<Integer, ConfiguredObjectSupplier<T>> providers = new HashMap<>();

    /** The create singleton. */
    private final Map<Integer, Boolean> createSingleton = new HashMap<>();

    /**
     * Instantiates a new abstract id based factory.
     *
     * @param emptyObjects
     *            the empty objects
     */
    protected AbstractIdBasedFactory(final T[] emptyObjects) {
        this.objectCache = emptyObjects;
    }

    /**
     * Register provider.
     *
     * @param objectId
     *            the object id
     * @param provider
     *            the provider
     * @param singleton
     *            the singleton
     */
    public void registerProvider(final int objectId, final ConfiguredObjectSupplier<T> provider,
            final boolean singleton) {
        checkCacheSize(objectId);
        createSingleton.put(objectId, singleton);
        final ConfiguredObjectSupplier<T> oldProvider = providers.put(objectId, provider);
        if (oldProvider != null) {
            throw new UncheckedException("Duplicate object id registered in factory, objectId = " + objectId
                    + ", current class = " + oldProvider.get().getClass().getName() + ", new class = "
                    + provider.get().getClass().getName());
        }
    }

    /**
     * Register.
     *
     * @param object
     *            the object
     */
    public void register(final T object) {
        checkCacheSize(object.getId());
        if (objectCache[object.getId()] != null) {
            if (objectCache[object.getId()] != null) {
                throw new UncheckedException("Duplicate object id registered in factory, objectId = " + object.getId()
                        + ", current class = " + objectCache[object.getId()].getClass().getName() + ", new class = "
                        + object.getClass().getName());
            }
        }
        objectCache[object.getId()] = object;
    }

    /**
     * Check cache size.
     *
     * @param id
     *            the id
     */
    protected void checkCacheSize(final int id) {
        if (objectCache.length <= id) {
            objectCache = Arrays.copyOf(objectCache, id + 1);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.core.IdBasedFactory#getInstance(int, java.lang.Class)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <Z extends T> Z getInstance(final Class<Z> type, final int objectId) {
        Z instance = (Z) objectCache[objectId];
        if (instance == null) {
            final ConfiguredObjectSupplier<T> provider = providers.get(objectId);
            if (provider != null) {
                logger.debug("Constructing new object for objectId {}", objectId);
                instance = (Z) provider.getObject(new IdBasedConfig<T>(objectId));
                logger.debug("Constructed new object {}", instance.toString());
                if (createSingleton.get(objectId)) {
                    objectCache[objectId] = instance;
                }
            }
        }
        return instance;
    }

}
