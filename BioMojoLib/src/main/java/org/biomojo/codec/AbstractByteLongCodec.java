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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractByteLongCodec.
 *
 * @author Hugh Eaves
 */
public abstract class AbstractByteLongCodec extends AbstractCodec<Byte, Long> implements ByteLongCodec {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractByteLongCodec.class.getName());

    /**
     * Create a new AbstractByteCodec.
     *
     * @param codecId
     *            the codec id
     */
    public AbstractByteLongCodec(final long codecId) {
        super(codecId);
    }

}
