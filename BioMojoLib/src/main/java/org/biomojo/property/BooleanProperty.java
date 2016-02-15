package org.biomojo.property;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

@Entity
@DiscriminatorValue("B")
public class BooleanProperty extends LongProperty {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(BooleanProperty.class);

    @Override
    public Object getValue() {
        return longValue != 0;
    }

    @Override
    public void setValue(final Object value) {
        setBoolean((Boolean) value);
    }

    public void setBoolean(final boolean value) {
        this.longValue = value ? 1 : 0;
    }
}
