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
package org.biomojo.alphabet;

import org.java0.core.type.AbstractLongIdentified;
import org.java0.factory.LifecycleEvent;

/**
 * The Class AbstractAlphabet.
 *
 * @author Hugh Eaves
 * @param <T>
 *            the generic type
 */
public abstract class AbstractAlphabet<T> extends AbstractLongIdentified implements Alphabet<T> {

    /**
     * Instantiates a new abstract alphabet.
     *
     * @param id
     *            the id
     */
    protected AbstractAlphabet(final long id) {
        super(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.getClass().getName() + "(id=" + id + ")";
    }

    public void notify(final LifecycleEvent event) {

    }

}
