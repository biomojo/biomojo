package org.biomojo.benchmark.framework.procutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinuxProcessInfo extends AbstractProcessInfo {
    private static final Logger logger = LoggerFactory.getLogger(LinuxProcStatFile.class.getName());

    String command = MISSING_STRING_VALUE;
    String state = MISSING_STRING_VALUE;
    int processGroupId = MISSING_VALUE;
    int session = MISSING_VALUE;
    int ttyNumber = MISSING_VALUE;
    int terminalProcessGroupId = MISSING_VALUE;
    int flags = MISSING_VALUE;
    long childMinorFaults = MISSING_VALUE;
    long childMajorFaults = MISSING_VALUE;
    long childUserMilliseconds = MISSING_VALUE;
    long childSystemMilliseconds = MISSING_VALUE;
    int priority = MISSING_VALUE;
    int nice = MISSING_VALUE;
    int numThreads = MISSING_VALUE;
    long intervalMillisecondsrRealValue = MISSING_VALUE;
    long startMilliseconds = MISSING_VALUE;
    long residentLimitBytes = MISSING_VALUE;
    long startCodeAddress = MISSING_VALUE;
    long endCodeAddress = MISSING_VALUE;
    long startStackAddress = MISSING_VALUE;
    long kernelStackESP = MISSING_VALUE;
    long kernelStackEIP = MISSING_VALUE;
    int signal = MISSING_VALUE;
    int blocked = MISSING_VALUE;
    int sigIgnore = MISSING_VALUE;
    long sigCatch = MISSING_VALUE;
    long waitChannel = MISSING_VALUE;
    long pagesSwapped = MISSING_VALUE;
    long childPagesSwapped = MISSING_VALUE;

    // Linux 2.1+
    int exitSignal = MISSING_VALUE;

    // Linux 2.2+
    int processorNum = MISSING_VALUE;

    // Linux 2.5+
    int rtPriority = MISSING_VALUE;
    int policy = MISSING_VALUE;

    // Linux 2.6+
    long blockIODelayMilliseconds = MISSING_VALUE;
    long guestMilliseconds = MISSING_VALUE;
    long childGuestMilliseconds = MISSING_VALUE;

    long startDataAddress = MISSING_VALUE;
    long endDataAddress = MISSING_VALUE;
    long startBrkAddress = MISSING_VALUE;

    long argStartAddress = MISSING_VALUE;
    long argEndAddress = MISSING_VALUE;
    long envStartAddress = MISSING_VALUE;
    long envEndAddress = MISSING_VALUE;
    int exitCode = MISSING_VALUE;

    public LinuxProcessInfo(final String[] rawValues) {
        parse(rawValues);
    }

    protected void parse(final String[] rawValues) {
        int pos = 0;

        // 1 - 10
        pid = toInt(rawValues[pos++]);
        command = rawValues[pos++];
        state = rawValues[pos++];
        parentPid = toInt(rawValues[pos++]);
        processGroupId = toInt(rawValues[pos++]);
        session = toInt(rawValues[pos++]);
        ttyNumber = toInt(rawValues[pos++]);
        terminalProcessGroupId = toInt(rawValues[pos++]);
        flags = toInt(rawValues[pos++]);
        minorFaults = toLong(rawValues[pos++]);

        // 11 - 20
        childMinorFaults = toLong(rawValues[pos++]);
        majorFaults = toLong(rawValues[pos++]);
        childMajorFaults = toLong(rawValues[pos++]);
        userMilliseconds = ticksToMilliseconds(rawValues[pos++]);
        systemMilliseconds = ticksToMilliseconds(rawValues[pos++]);
        childUserMilliseconds = ticksToMilliseconds(rawValues[pos++]);
        childSystemMilliseconds = ticksToMilliseconds(rawValues[pos++]);
        priority = toInt(rawValues[pos++]);
        nice = toInt(rawValues[pos++]);
        numThreads = toInt(rawValues[pos++]);

        // 21 - 30
        intervalMillisecondsrRealValue = toLong(rawValues[pos++]);
        startMilliseconds = ticksToMilliseconds(rawValues[pos++]);
        virtualBytes = pagesToBytes(rawValues[pos++]);
        residentBytes = pagesToBytes(rawValues[pos++]);
        residentLimitBytes = unsignedLongToLong(rawValues[pos++]);
        startCodeAddress = unsignedLongToLong(rawValues[pos++]);
        endCodeAddress = unsignedLongToLong(rawValues[pos++]);
        startStackAddress = unsignedLongToLong(rawValues[pos++]);
        kernelStackESP = unsignedLongToLong(rawValues[pos++]);
        kernelStackEIP = unsignedLongToLong(rawValues[pos++]);

        // 31 - 40
        signal = toInt(rawValues[pos++]);
        blocked = toInt(rawValues[pos++]);
        sigIgnore = toInt(rawValues[pos++]);
        sigCatch = unsignedLongToLong(rawValues[pos++]);
        waitChannel = unsignedLongToLong(rawValues[pos++]);
        pagesSwapped = toLong(rawValues[pos++]);
        childPagesSwapped = toLong(rawValues[pos++]);
        exitSignal = toInt(rawValues[pos++]);
        processorNum = toInt(rawValues[pos++]);
        rtPriority = toInt(rawValues[pos++]);

        policy = toInt(rawValues[pos++]);
        // Linux 2.6+
        blockIODelayMilliseconds = ticksToMilliseconds(rawValues[pos++]);
        guestMilliseconds = ticksToMilliseconds(rawValues[pos++]);
        childGuestMilliseconds = ticksToMilliseconds(rawValues[pos++]);
        // Linux 3.3+
        startDataAddress = unsignedLongToLong(rawValues[pos++]);
        endDataAddress = unsignedLongToLong(rawValues[pos++]);
        startBrkAddress = unsignedLongToLong(rawValues[pos++]);
        // Linux 3.5+
        argStartAddress = unsignedLongToLong(rawValues[pos++]);
        argEndAddress = unsignedLongToLong(rawValues[pos++]);
        envStartAddress = unsignedLongToLong(rawValues[pos++]);

        envEndAddress = unsignedLongToLong(rawValues[pos++]);
        exitCode = toInt(rawValues[pos++]);
    }

    private long ticksToMilliseconds(final String value) {
        return Long.parseLong(value) * (10L);
    }

    private long pagesToBytes(final String value) {
        return Long.parseLong(value) * 4096;
    }

}
