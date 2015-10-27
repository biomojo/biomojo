package org.biomojo.codec;

import org.biomojo.core.AbstractIdBasedFactory;
import org.java0.factory.Config;
import org.java0.factory.ConfiguredObjectProvider;
import org.java0.factory.FactoryException;

public class DefaultCodecFactory extends AbstractIdBasedFactory<Codec<?, ?>> {
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
        registerProvider(CodecId.ZLIB_BYTE_CODEC, new ConfiguredObjectProvider<Codec<?, ?>>() {
            @Override
            public Codec<?, ?> getObject(final Config<Codec<?, ?>> config) throws FactoryException {
                return new ZLibByteCodec((int) config.values()[0]);
            }
        }, false);
    }
}
