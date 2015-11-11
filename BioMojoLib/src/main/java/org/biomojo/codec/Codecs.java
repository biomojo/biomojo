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

package org.biomojo.codec;

import org.biomojo.core.IdBasedFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class Codecs.
 *
 * @author hugh
 */
public abstract class Codecs {

    /** The factory. */
    private static IdBasedFactory<Codec<?, ?>> factory = new DefaultCodecFactory();

    /**
     * Gets the codec.
     *
     * @param <T> the generic type
     * @param codecId the codec id
     * @return the codec
     */
    public static <T extends Codec<?, ?>> T getCodec(int codecId) {
        return (T) factory.getInstance(codecId, Codec.class);
    }

    /**
     * Gets the codec.
     *
     * @param <T> the generic type
     * @param codecId the codec id
     * @param requiredType the required type
     * @return the codec
     */
    public static <T extends Codec<?, ?>> T getCodec(int codecId, Class<T> requiredType) {
        return factory.getInstance(codecId, requiredType);
    }
}
