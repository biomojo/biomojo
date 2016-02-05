package org.biomojo.benchmark.util;

import org.biomojo.GlobalConst;
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

        if (GlobalConst.DEBUG_MEMORY) {
            checkMemory();
        }

        if (GlobalConst.GC_RATIO > 0) {
            final int log = (int) (Math.log(recordCount) / Math.log(GlobalConst.GC_RATIO));
            if (log != lastLog) {
                lastLog = log;
                logger.info("Forcing GC, recordCount = {} ", recordCount);
                logMemory("Before GC");
                System.gc();
                logMemory("After GC");
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

    public void logMemory(final String msg) {
        final long totalMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long maxMemory = runtime.maxMemory();
        logger.info("Memory status ({}): totalMemory = {}, freeMemory = {}, maxMemory = {}", msg, totalMemory,
                freeMemory, maxMemory);
    }

}
