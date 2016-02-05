package org.biomojo;

import java.util.function.Function;

public class GlobalConst {
    /** The Constant DEFAULT_BUFFER_SIZE. */
    public static final int DEFAULT_BUFFER_SIZE;
    public static final float GC_RATIO;
    public static final boolean DEBUG_MEMORY;
    public static final boolean VALIDATE_INPUT_SEQS;
    public static final String LIB_SPRING_CONTEXT = "biomojolib-context.xml";

    // private static final String PROPERTY_PREFIX = "biomojo.";
    private static final String PROPERTY_PREFIX = "";

    static {
        DEFAULT_BUFFER_SIZE = getProperty("readBufferSize", Integer::parseInt, 0xFFFF);
        GC_RATIO = getProperty("gcRatio", Float::parseFloat, (float) -1);
        DEBUG_MEMORY = getProperty("debugMemory", Boolean::parseBoolean, false);
        VALIDATE_INPUT_SEQS = getProperty("validateInputSeqs", Boolean::parseBoolean, true);
    }

    static <T> T getProperty(final String key, final Function<String, T> converter, final T defaultValue) {
        T value = defaultValue;
        final String property = System.getProperty(PROPERTY_PREFIX + key);
        if (property != null) {
            try {
                value = converter.apply(property);
            } catch (final NumberFormatException e) {
            }
        }
        return value;
    }
}
