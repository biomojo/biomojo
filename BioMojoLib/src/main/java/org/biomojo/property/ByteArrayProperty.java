package org.biomojo.property;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "B")
public class ByteArrayProperty extends BasicProperty {

    /**
     *
     */
    private static final long serialVersionUID = 1653825404706625049L;

    protected byte[] byteArrayValue;

    @Override
    public void setValue(Object value) {
        byteArrayValue = (byte[]) value;
    }

    @Override
    public Object getValue() {
        return byteArrayValue;
    }
}
