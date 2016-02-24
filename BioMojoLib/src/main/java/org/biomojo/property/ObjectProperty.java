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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.java0.core.exception.UncheckedException;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

@Entity
@DiscriminatorValue("O")
public class ObjectProperty extends BasicProperty {
    private static final Logger logger = LoggerFactory.getLogger(ObjectProperty.class);

    @Transient
    private Serializable value;

    @Column(length = Integer.MAX_VALUE)
    private byte[] byteData;

    @PrePersist
    @PreUpdate
    protected void prepareForSave() {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                final ObjectOutput output = new ObjectOutputStream(outputStream);) {
            output.writeObject(value);
            byteData = outputStream.toByteArray();
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
            throw new UncheckedException(e);
        }
    }

    @PostPersist
    @PostUpdate
    protected void cleanupAfterSave() {
        byteData = null;
    }

    @PostLoad
    protected void fromDB() {
        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(byteData);
                final ObjectInput objectInput = new ObjectInputStream(inputStream);) {
            value = (Serializable) objectInput.readObject();
            byteData = null;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Caught exception in auto-generated catch block", e);
            throw new UncheckedException(e);
        }
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(final Object value) {
        this.value = (Serializable) value;
    }
}
