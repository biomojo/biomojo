package org.biomojo.benchmark.framework.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class DefaultParameters implements TestParameters {
    private static final Logger logger = LoggerFactory.getLogger(DefaultParameters.class);

    private final Map<String, Object> values = new HashMap<>();

    public DefaultParameters() {

    }

    public DefaultParameters(final TestParameters parameters) {
        for (final Entry<String, Object> entry : parameters.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Object get(final String key) {
        return values.get(key);
    }

    @Override
    public void put(final String key, final Object value) {
        logger.debug("adding parameter {}, value is [{}]", key, value.toString());
        values.put(key, value);
    }

    @Override
    public void remove(final String key) {
        values.remove(key);
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return values.entrySet();
    }

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer("Parameters:\n");
        for (final Entry<String, Object> entry : values.entrySet()) {
            buffer.append(entry.getKey());
            buffer.append(": [");
            buffer.append(entry.getValue().toString());
            buffer.append("]\n");
        }
        return buffer.toString();
    }
}
