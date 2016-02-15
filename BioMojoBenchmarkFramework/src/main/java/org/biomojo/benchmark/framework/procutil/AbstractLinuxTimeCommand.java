package org.biomojo.benchmark.framework.procutil;

import org.biomojo.benchmark.framework.tests.AbstractPreparer;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public abstract class AbstractLinuxTimeCommand extends AbstractPreparer {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractLinuxTimeCommand.class);

    // command line ^ major faults ^ minor faults ^ max rss ^ percent cpu ^
    // system time ^ user time ^ elapsed time
    protected static final char[] TIME_FIELDS = { 'C', 'F', 'R', 'M', 'P', 'S', 'U', 'e' };
    protected static final String FIELD_FORMAT_STRING;

    static {
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < TIME_FIELDS.length; ++i) {
            if (i > 0) {
                buffer.append("^");
            }
            buffer.append("%");
            buffer.append(TIME_FIELDS[i]);
        }
        FIELD_FORMAT_STRING = buffer.toString();
    }
}
