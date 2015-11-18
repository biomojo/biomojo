package org.biomojo.codec;

import org.biomojo.alphabet.ByteAlphabet;

public class TwoBitByteLongCodec extends AbstractByteLongCodec {

    public TwoBitByteLongCodec(int codecId) {
        super(codecId);
    }

    @Override
    public byte[] decode(ByteAlphabet alphabet, long[] encodedData, int length) {
        final byte[] decodedData = new byte[length];

        return decodedData;
    }

    @Override
    public byte decode(ByteAlphabet alphabet, long[] encodedData, int length, int index) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long[] encode(ByteAlphabet alphabet, long[] encodedData, int length, byte[] decodedData) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void encode(ByteAlphabet alphabet, long[] encodedData, int length, byte symbol, int index) {
        // TODO Auto-generated method stub

    }

}
