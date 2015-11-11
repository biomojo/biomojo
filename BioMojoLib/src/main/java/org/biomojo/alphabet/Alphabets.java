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

import org.biomojo.core.IdBasedFactory;

// TODO: Auto-generated Javadoc
/**
 * A factory that produces {@code Alphabet}'s given an {@code Alphabet}
 * identifier.
 * 
 * @author Hugh Eaves
 *
 */
public class Alphabets {
    
    /** The factory. */
    private static IdBasedFactory<Alphabet<?>> factory = new DefaultAlphabetFactory();

    /**
     * Gets the alphabet.
     *
     * @param alphabetId the alphabet id
     * @return the alphabet
     */
    public static Alphabet<?> getAlphabet(final int alphabetId) {
        return factory.getInstance(alphabetId, Alphabet.class);
    }

    /**
     * Gets the alphabet.
     *
     * @param <T> the generic type
     * @param alphabetId the alphabet id
     * @param requiredType the required type
     * @return the alphabet
     */
    public static <T extends Alphabet<?>> T getAlphabet(final int alphabetId, final Class<T> requiredType) {
        return factory.getInstance(alphabetId, requiredType);
    }
}
