package org.biomojo.benchmark.framework.procutil;

import java.util.Set;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;
import org.junit.Test;

public class ProcUtilTest {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ProcUtilTest.class);

    @Test
    public void test1() {
        final Set<Integer> allPids = LinuxProcessUtil.getAllPids();
        System.out.println(allPids);
        logger.info(LinuxProcessUtil.getChildren(1).toString());
        logger.info(LinuxProcessUtil.getChildren(2).toString());
    }
}
