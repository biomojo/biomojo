package org.biomojo.benchmark.framework.benchmark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.java0.core.exception.UncheckedException;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class JavaScriptUtil {
    private static final Logger logger = LoggerFactory.getLogger(JavaScriptUtil.class);

    public static Object convertValue(final Object value, final boolean allowNulls) {
        final Object converted = convertValueRecursive(new ArrayList<String>(), "ROOT", value, allowNulls);
        logger.debug("Conversion result: {}", converted);
        return converted;
    }

    @SuppressWarnings("restriction")
    private static Object convertValueRecursive(final List<String> stack, final String currentLevel, final Object value,
            final boolean allowNulls) {

        stack.add(currentLevel);

        Object converted = value;

        if (value == null) {
            logger.debug("{} converting Null value", stack);
            if (!allowNulls) {
                throw new NullPointerException("Null value not allowed @ " + stack.toString());
            }
        } else if (ScriptObjectMirror.isUndefined(value)) {
            logger.debug("{} converting Undefined value", stack);
            if (!allowNulls) {
                throw new NullPointerException("Undefined value not allowed @ " + stack.toString());
            }
            converted = null;
        } else if (value instanceof Number) {
            logger.debug("{} converting Number", stack);
            final Number num = (Number) value;
            if (num.longValue() == num.doubleValue()) {
                converted = num.longValue();
            }
        } else if (value instanceof String) {
            logger.debug("{} converting String", stack);
        } else if (value instanceof Boolean) {
            logger.debug("{} converting Boolean", stack);
        } else if (value instanceof ScriptObjectMirror) {
            final ScriptObjectMirror som = (ScriptObjectMirror) value;
            if (som.isFunction()) {
                throw new UncheckedException("JavaScript functions cannot be converted");
            } else if (som.isArray()) {
                logger.debug("{} converting array", stack);
                final List<Object> list = new ArrayList<>();
                for (int i = 0; i < som.size(); ++i) {
                    logger.debug("{} converting array entry {}", stack, i);
                    list.add(convertValueRecursive(stack, "array entry " + i, som.getSlot(i), allowNulls));
                }
                converted = list;
            } else {
                logger.debug("{} converting map", stack);
                final Map<String, Object> map = new HashMap<>();
                for (final Entry<String, Object> entry : som.entrySet()) {
                    logger.debug("{} converting map entry {}", stack, entry.getKey());
                    map.put(entry.getKey(),
                            convertValueRecursive(stack, "map entry " + entry.getKey(), entry.getValue(), allowNulls));
                }
                converted = map;
            }
        } else {
            throw new UncheckedException("unrecognized type: " + value.getClass().getName());
        }

        if (converted == null) {
            logger.debug("{} converted value = null", stack);
        } else {
            logger.debug("{} converted type = {}, value = {}", stack, converted.getClass().getName(),
                    converted.toString());
        }

        stack.remove(stack.size() - 1);

        return converted;
    }
}
