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
    private float gcRatio = -1;
    private boolean logMemory = false;

    private static final Runtime runtime = Runtime.getRuntime();

    public GCUtil() {
        final String gcRatioStr = System.getProperty("gcRatio");
        if (gcRatioStr != null) {
            try {
                gcRatio = Float.parseFloat(gcRatioStr);
            } catch (final NumberFormatException e) {
                logger.error("Unable to convert gcInterval value");
            }
        }

        if (System.getProperty("logMemory") != null) {
            logMemory = true;
        }
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

        if (logMemory) {
            checkMemory();
        }

        if (gcRatio > 0) {
            final int log = (int) (Math.log(recordCount) / Math.log(gcRatio));
            if (log != lastLog) {
                lastLog = log;
                logMemory("Before GC");
                logger.info("Forcing GC");
                logMemory("After GC");
                System.gc();
            }
        }
    }

    private void checkMemory() {
        final long totalMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long maxMemory = runtime.maxMemory();

        if (totalMemory != lastTotalMemory) {
            logger.info("Total memory: {}KB ( delta = {}KB )", totalMemory / 1024,
                    (totalMemory - lastTotalMemory) / (1024));
        }

        if (freeMemory != lastFreeMemory) {
            logger.info("Free memory: {}KB ( delta = {}KB )", freeMemory / 1024,
                    (freeMemory - lastFreeMemory) / (1024));
        }

        if (maxMemory != lastMaxMemory) {
            logger.info("Max memory: {}KB ( delta = {}KB )", maxMemory / 1024, (maxMemory - lastMaxMemory) / (1024));
        }

        lastTotalMemory = totalMemory;
        lastFreeMemory = freeMemory;
        lastMaxMemory = maxMemory;
    }

    private void logMemory(final String msg) {
        final long totalMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long maxMemory = runtime.maxMemory();
        logger.info("Memory status ({}): totalMemory = {}, freeMemory = {}, maxMemory = {}", msg, totalMemory,
                freeMemory, maxMemory);
    }

}
