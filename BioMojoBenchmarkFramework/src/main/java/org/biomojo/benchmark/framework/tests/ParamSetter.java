package org.biomojo.benchmark.framework.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.java0.core.exception.UncheckedException;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class ParamSetter extends AbstractPreparer {
    private static final Logger logger = LoggerFactory.getLogger(ParamSetter.class);

    Map<String, Object> valuesMap = new HashMap<>();

    public ParamSetter(final Object... values) {
        valuesMap = paramsToMap(values);
        this.setOutputParams(valuesMap.keySet().toArray(new String[0]));
    }

    private Map<String, Object> paramsToMap(final Object... values) {
        final Map<String, Object> valuesMap = new HashMap<>();
        if (values.length % 2 != 0) {
            throw new UncheckedException("Number of values is odd, must be even");
        }
        for (int i = 0; i < values.length; i += 2) {
            final String key = (String) values[i];
            if (values[i + 1] != null) {
                valuesMap.put(key, values[i + 1]);
            }
        }
        return valuesMap;
    }

    @Override
    public void safeExecute(final TestParameters parameters) {
        for (final Entry<String, Object> entry : valuesMap.entrySet()) {
            logger.info("ParamSetter: setting {} to {}", entry.getKey(), entry.getValue());
            parameters.put(entry.getKey(), entry.getValue());
        }
    }

    public Operation addOptional(final Object... values) {
        valuesMap.putAll(paramsToMap(values));
        return this;
    }

}
