package org.biomojo.codec;

import org.biomojo.alphabet.ByteAlphabet;

public class TwoBitByteLongCodec extends AbstractByteLongCodec {

    public TwoBitByteLongCodec(final int codecId) {
        super(codecId);
    }

    @Override
    public byte[] decode(final ByteAlphabet alphabet, final long[] encodedData, final int length) {
        final byte[] decodedData = new byte[length];

        return decodedData;
    }

    @Override
    public byte decode(final ByteAlphabet alphabet, final long[] encodedData, final int length, final int index) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long[] encode(final ByteAlphabet alphabet, final long[] encodedData, final int length,
            final byte[] decodedData) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void encode(final ByteAlphabet alphabet, final long[] encodedData, final int length, final byte symbol,
            final int index) {
        // TODO Auto-generated method stub
    }

    @Override
    public int blockSize(final int blockNum) {
        return 1;
    }

}
