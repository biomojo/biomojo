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

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.biomojo.property.BasicProperty;
import org.biomojo.property.DbPropertyMapManager;
import org.biomojo.property.DefaultPropertyMapManager;
import org.biomojo.property.Propertied;
import org.biomojo.property.PropertyMapManager;
import org.java0.collection.ArrayMap;

/**
 * @author Hugh Eaves
 *
 */
@MappedSuperclass
public abstract class AbstractPropertiedEntity extends AbstractEntity implements Propertied {

    private static final long serialVersionUID = 1L;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = BasicProperty.class)
    protected Map<String, Object> properties = null;

    @Transient
    private PropertyMapManager propertyMapManager = DefaultPropertyMapManager.INSTANCE;

    @PrePersist
    @PreUpdate
    protected void wrapProps() {
        if (propertyMapManager != DbPropertyMapManager.INSTANCE) {
            properties.replaceAll((k, v) -> DbPropertyMapManager.wrap(v));
        }
    }

    @PostPersist
    @PostUpdate
    @PostLoad
    protected void useDbPropertyManager() {
        propertyMapManager = DbPropertyMapManager.INSTANCE;
    }

    @Override
    public <T> T getProp(final String key) {
        return propertyMapManager.get(getProperties(), key);
    }

    @Override
    public <T> T getProp(final String key, final Class<T> type) {
        return propertyMapManager.get(getProperties(), key);
    }

    @Override
    public <T> T setProp(final String key, final Object value) {
        return propertyMapManager.put(getProperties(), key, value);

    }

    @Override
    public <T> T removeProp(final String key) {
        return propertyMapManager.remove(getProperties(), key);
    }

    protected Map<String, Object> getProperties() {
        if (properties == null) {
            properties = new ArrayMap<>();
        }
        return properties;
    }

}
