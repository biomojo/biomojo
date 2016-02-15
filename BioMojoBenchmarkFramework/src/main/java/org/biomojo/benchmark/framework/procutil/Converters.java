package org.biomojo.benchmark.framework.procutil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class Converters {
    private static final Logger logger = LoggerFactory.getLogger(Converters.class);

    private static final BigDecimal MAX_LONG_VALUE = new BigDecimal(Long.MAX_VALUE);

    private Converters() {

    }

    private static Map<Class<?>, Function<String, ?>> standardConverters = new HashMap<>();

    static {
        standardConverters.put(Long.class, Long::parseLong);
        standardConverters.put(Integer.class, Integer::parseInt);
        standardConverters.put(Byte.class, Byte::parseByte);
        standardConverters.put(String.class, String::toString);
    }

    public static <T extends Object> Function<String, T> getConverterFor(final Class<T> key) {
        return (Function<String, T>) standardConverters.get(key);
    }

    public static long clockTicksToMS(final String value) {
        return Long.parseLong(value) * (1000L / 100L);
    }

    /**
     * Converts a string that represents an unsigned 64bit long value to a java
     * signed long value. As we can't store the largest unsigned values in the
     * Java signed long type, any unsigned values greater than Long.MAX_LONG are
     * returned as the negative two's complement value.
     * 
     * @param value
     * @return
     */
    public static long unsignedLongToLong(final String value) {
        final BigDecimal bigValue = new BigDecimal(value);
        if (MAX_LONG_VALUE.compareTo(bigValue) >= 0) {
            return bigValue.longValue();
        } else {
            return MAX_LONG_VALUE.subtract(bigValue).longValue();
        }
    }

    public static <T extends Field> Map<T, Object> stringsToFields(final T[] fields, final String[] values) {
        final Map<T, Object> parsedFields = new HashMap<>();

        for (int i = 0; i < values.length; ++i) {
            final T field = fields[i];
            final String value = values[i];
            logger.debug("field {} = {}", field, value);
            final Object convertedValue = field.convert(value);
            if (convertedValue != null) {
                parsedFields.put(field, convertedValue);
            } else {
                logger.error("Error converting value");
            }
        }

        return parsedFields;
    }

}
