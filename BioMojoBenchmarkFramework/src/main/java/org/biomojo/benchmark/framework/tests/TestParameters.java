package org.biomojo.benchmark.framework.tests;

import java.util.Map.Entry;
import java.util.Set;

public interface TestParameters {
    public Object get(final String key);

    public void put(final String key, final Object value);

    public default TestParameters add(final String key, final Object value) {
        put(key, value);
        return this;
    }

    public default String getAsString(final String key) {
        final Object value = get(key);
        if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

    public default boolean isPresentAndTrue(final String key) {
        final Object value = get(key);
        if (value != null) {
            return (boolean) value;
        } else {
            return false;
        }
    }

    public void remove(final String key);

    @SuppressWarnings("unchecked")
    public default <T> T get(final String key, final Class<T> cls) {
        return (T) get(key);
    }

    public Set<Entry<String, Object>> entrySet();

}
