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

import java.util.HashMap;
import java.util.Map;

import org.biomojo.sequence.ByteSeqImpl;
import org.biomojo.sequence.EncodedByteSeq;
import org.java0.core.exception.UncheckedException;

// TODO: Auto-generated Javadoc
/**
 * The Class DbPropertyMapManager.
 */
public class DbPropertyMapManager implements PropertyMapManager {

    /** The Constant INSTANCE. */
    public static final DbPropertyMapManager INSTANCE = new DbPropertyMapManager();

    /** The Constant typeMap. */
    private static final Map<Class<?>, Class<? extends BasicProperty>> typeMap = new HashMap<>();

    static {
        typeMap.put(Long.class, LongProperty.class);
        typeMap.put(Integer.class, IntegerProperty.class);
        typeMap.put(Double.class, DoubleProperty.class);
        typeMap.put(String.class, StringProperty.class);
        typeMap.put(ByteSeqImpl.class, SeqProperty.class);
        typeMap.put(EncodedByteSeq.class, SeqProperty.class);
    }

    /**
     * Instantiates a new db property map manager.
     */
    private DbPropertyMapManager() {

    }

    /* (non-Javadoc)
     * @see org.biomojo.property.PropertyMapManager#put(java.util.Map, java.lang.String, java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T put(Map<String, Object> properties, String key, Object value) {
        return (T) unwrap(properties.put(key, wrap(value)));
    }

    /**
     * Wrap.
     *
     * @param value the value
     * @return the basic property
     */
    public static BasicProperty wrap(Object value) {
        if (value instanceof BasicProperty) {
            return (BasicProperty) value;
        }

        Class<? extends BasicProperty> cls = typeMap.get(value.getClass());
        if (cls == null) {
            throw new IllegalArgumentException("Unhandled property type " + value.getClass().getName());
        }
        BasicProperty property;
        try {
            property = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UncheckedException("Unable to instantiate class " + cls.getName());
        }
        property.setValue(value);
        return property;
    }

    /**
     * Unwrap.
     *
     * @param property the property
     * @return the object
     */
    public static Object unwrap(Object property) {
        if (property != null) {
            if (property instanceof BasicProperty) {
                return ((BasicProperty) property).getValue();
            } else {
                return property;
            }
        } else {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.biomojo.property.PropertyMapManager#get(java.util.Map, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Map<String, Object> properties, String key) {
        return (T) unwrap(properties.get(key));
    }

    /* (non-Javadoc)
     * @see org.biomojo.property.PropertyMapManager#remove(java.util.Map, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T remove(Map<String, Object> properties, String key) {
        return (T) unwrap(properties.remove(key));
    }
}
