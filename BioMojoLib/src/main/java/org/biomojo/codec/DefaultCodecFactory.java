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

import org.biomojo.core.AbstractIdBasedFactory;
import org.java0.factory.Config;
import org.java0.factory.ConfiguredObjectProvider;
import org.java0.factory.FactoryException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating DefaultCodec objects.
 */
public class DefaultCodecFactory extends AbstractIdBasedFactory<Codec<?, ?>> {

    /**
     * Instantiates a new default codec factory.
     */
    public DefaultCodecFactory() {
        super(new Codec<?, ?>[0]);
        registerProvider(CodecId.NULL_BYTE_CODEC, new ConfiguredObjectProvider<Codec<?, ?>>() {
            @Override
            public Codec<?, ?> getObject(final Config<Codec<?, ?>> config) throws FactoryException {
                return new NullByteCodec((int) config.values()[0]);
            }
        }, true);
        registerProvider(CodecId.TWO_BIT_BYTE_CODEC, new ConfiguredObjectProvider<Codec<?, ?>>() {
            @Override
            public Codec<?, ?> getObject(final Config<Codec<?, ?>> config) throws FactoryException {
                return new TwoBitByteCodec((int) config.values()[0]);
            }
        }, true);
        registerProvider(CodecId.THREE_BIT_BYTE_CODEC, new ConfiguredObjectProvider<Codec<?, ?>>() {
            @Override
            public Codec<?, ?> getObject(final Config<Codec<?, ?>> config) throws FactoryException {
                return new ThreeBitByteCodec((int) config.values()[0]);
            }
        }, true);
        registerProvider(CodecId.FOUR_BIT_BYTE_CODEC, new ConfiguredObjectProvider<Codec<?, ?>>() {
            @Override
            public Codec<?, ?> getObject(final Config<Codec<?, ?>> config) throws FactoryException {
                return new FourBitByteCodec((int) config.values()[0]);
            }
        }, true);
        registerProvider(CodecId.FIVE_BIT_BYTE_CODEC, new ConfiguredObjectProvider<Codec<?, ?>>() {
            @Override
            public Codec<?, ?> getObject(final Config<Codec<?, ?>> config) throws FactoryException {
                return new FiveBitByteCodec((int) config.values()[0]);
            }
        }, true);
        registerProvider(CodecId.ZLIB_BYTE_CODEC, new ConfiguredObjectProvider<Codec<?, ?>>() {
            @Override
            public Codec<?, ?> getObject(final Config<Codec<?, ?>> config) throws FactoryException {
                return new ZLibByteCodec((int) config.values()[0]);
            }
        }, false);
    }
}
