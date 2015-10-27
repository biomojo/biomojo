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
package org.biomojo.sequence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.biomojo.core.AbstractPropertiedEntity;
import org.java0.collection.DefaultList;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractSeqList.
 *
 * @author Hugh Eaves
 * @param <T> the generic type
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractSeqList<T extends Seq<?, ?>> extends AbstractPropertiedEntity
        implements SeqList<T>, Serializable, DefaultList<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 717088554484595404L;

    /** The description. */
    CharSequence description;

    /* (non-Javadoc)
     * @see org.biomojo.core.Described#getDescription()
     */
    @Override
    public CharSequence getDescription() {
        return description;
    }

    /* (non-Javadoc)
     * @see org.biomojo.core.Described#setDescription(java.lang.CharSequence)
     */
    @Override
    public void setDescription(final CharSequence description) {
        this.description = description;
    }
}
