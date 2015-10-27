package org.biomojo.property;

import java.util.HashMap;
import java.util.Map;

import org.biomojo.sequence.ByteSeqImpl;
import org.biomojo.sequence.EncodedByteSeq;
import org.java0.core.exception.UncheckedException;

public class DbPropertyMapManager implements PropertyMapManager {

    public static final DbPropertyMapManager INSTANCE = new DbPropertyMapManager();

    private static final Map<Class<?>, Class<? extends BasicProperty>> typeMap = new HashMap<>();

    static {
        typeMap.put(Long.class, LongProperty.class);
        typeMap.put(Integer.class, IntegerProperty.class);
        typeMap.put(Double.class, DoubleProperty.class);
        typeMap.put(String.class, StringProperty.class);
        typeMap.put(ByteSeqImpl.class, SeqProperty.class);
        typeMap.put(EncodedByteSeq.class, SeqProperty.class);
    }

    private DbPropertyMapManager() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T put(Map<String, Object> properties, String key, Object value) {
        return (T) unwrap(properties.put(key, wrap(value)));
    }

    public static BasicProperty wrap(Object value) {
        if (value instanceof BasicProperty) {
            return (BasicProperty) value;
        }

        Class<? extends BasicProperty> cls = typeMap.get(value.getClass());
        if (cls == null) {
            throw new IllegalArgumentException("Unhandled property type " + value.getClass().getName());
        }
        BasicProperty property;
        try {
            property = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UncheckedException("Unable to instantiate class " + cls.getName());
        }
        property.setValue(value);
        return property;
    }

    public static Object unwrap(Object property) {
        if (property != null) {
            if (property instanceof BasicProperty) {
                return ((BasicProperty) property).getValue();
            } else {
                return property;
            }
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Map<String, Object> properties, String key) {
        return (T) unwrap(properties.get(key));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T remove(Map<String, Object> properties, String key) {
        return (T) unwrap(properties.remove(key));
    }
}
