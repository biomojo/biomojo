package org.biomojo.benchmark.util;

import java.util.logging.Logger;

public class GCUtil {
    private static final Logger logger = Logger.getLogger(GCUtil.class.getName());

    private int recordCount = 0;
    private int lastLog = 0;

    public void reset() {
        recordCount = 0;
        lastLog = 0;
    }

    public void recordAdded() {
        ++recordCount;
        int log = (int) (Math.log(recordCount) / Math.log(1.010));
        if (log != lastLog) {
            lastLog = log;
            logger.info("Loaded " + recordCount + " sequences so far");
            System.gc();
        }
    }

}
