package org.biomojo.benchmark.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GCUtil {
    private static final Logger logger = LoggerFactory.getLogger(GCUtil.class);

    private int recordCount = 0;
    private int lastLog = 0;

    private long lastFreeMemory = 0;
    private long lastTotalMemory = 0;
    private long lastMaxMemory = 0;

    private static final Runtime runtime = Runtime.getRuntime();

    public GCUtil() {
        reset();
    }

    public void reset() {
        recordCount = 0;
        lastLog = 0;
        lastTotalMemory = runtime.totalMemory();
        lastFreeMemory = runtime.freeMemory();
        lastMaxMemory = runtime.maxMemory();
    }

    public void recordAdded() {

        ++recordCount;

        final long totalMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long maxMemory = runtime.maxMemory();

        if (totalMemory != lastTotalMemory) {
            logger.info("Loaded {} sequences so far", recordCount);
            logger.info("Expanded heap by {} KiB", (totalMemory - lastTotalMemory) / (1024));
            logger.info("New values: freeMemory = {}, totalMemory = {}, maxMemory = {}", freeMemory / 1024,
                    totalMemory / 1024, maxMemory / 1024);
            lastTotalMemory = totalMemory;
        }

        final int log = (int) (Math.log(recordCount) / Math.log(1.010));

        if (log != lastLog) {
            lastLog = log;
            logger.info("Loaded {} sequences so far", recordCount);
            logMemory();
            System.gc();
        }
    }

    public void logMemory() {
        final long totalMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long maxMemory = runtime.maxMemory();
        logger.info("freeMemory = {}, totalMemory = {}, maxMemory = {}", freeMemory / 1024, totalMemory / 1024,
                maxMemory / 1024);
    }
}
