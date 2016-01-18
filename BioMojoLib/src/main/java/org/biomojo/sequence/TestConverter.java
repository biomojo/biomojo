package org.biomojo.sequence;

import javax.persistence.AttributeConverter;

public class TestConverter implements AttributeConverter<long[], byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(long[] attribute) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long[] convertToEntityAttribute(byte[] dbData) {
        // TODO Auto-generated method stub
        return null;
    }

}
