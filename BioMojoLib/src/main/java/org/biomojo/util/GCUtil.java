package org.biomojo.util;

import org.biomojo.GlobalConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GCUtil {
    private static final Logger logger = LoggerFactory.getLogger(GCUtil.class);

    private int checkCount = 0;
    private int lastLog = 0;

    private long lastFreeMemory = 0;
    private long lastTotalMemory = 0;

    private static final Runtime runtime = Runtime.getRuntime();

    public GCUtil() {
        reset();
    }

    public void reset() {
        checkCount = 0;
        lastLog = 0;
        lastTotalMemory = runtime.totalMemory();
        lastFreeMemory = runtime.freeMemory();
    }

    public void check() {
        ++checkCount;

        if (GlobalConst.DEBUG_MEMORY) {
            logMemory("check");
        }

        if (GlobalConst.GC_RATIO > 0) {
            final int log = (int) (Math.log(checkCount) / Math.log(GlobalConst.GC_RATIO));
            if (log != lastLog) {
                lastLog = log;
                forceGC();
            }
        }
    }

    public void forceGC() {
        logger.info("force GC called, checkCount = {} ", checkCount);
        logMemory("Pre GC");
        System.gc();
        logMemory("PostGC");
    }

    public void logMemory(final String message) {
        final long totalMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();

        logger.info("Memory ({}): free: {}/{}KB, /_\\free: {}KB, /_\\total: {}KB", message, freeMemory / 1024,
                totalMemory / 1024, (freeMemory - lastFreeMemory) / (1024), (totalMemory - lastTotalMemory) / (1024));

        lastTotalMemory = totalMemory;
        lastFreeMemory = freeMemory;
    }

}
