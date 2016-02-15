package org.biomojo.benchmark.framework.procutil;

import java.math.BigDecimal;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class AbstractProcessInfo implements BasicProcessInfo {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractProcessInfo.class);

    protected static final BigDecimal MAX_LONG_VALUE = new BigDecimal(Long.MAX_VALUE);

    protected int pid = MISSING_VALUE;
    protected int parentPid = MISSING_VALUE;

    protected long userMilliseconds = MISSING_VALUE;
    protected long systemMilliseconds = MISSING_VALUE;
    protected long elapsedMilliseconds = MISSING_VALUE;

    protected long residentBytes = MISSING_VALUE;
    protected long virtualBytes = MISSING_VALUE;

    protected long majorFaults = MISSING_VALUE;
    protected long minorFaults = MISSING_VALUE;

    @Override
    public int getPid() {
        return pid;
    }

    @Override
    public int getParentPid() {
        return parentPid;
    }

    @Override
    public long getUserMilliseconds() {
        return userMilliseconds;
    }

    @Override
    public long getSystemMilliseconds() {
        return systemMilliseconds;
    }

    @Override
    public long getResidentBytes() {
        return residentBytes;
    }

    @Override
    public long getVirtualBytes() {
        return virtualBytes;
    }

    @Override
    public long getMajorFaults() {
        return majorFaults;
    }

    @Override
    public long getMinorFaults() {
        return minorFaults;
    }

    @Override
    public long getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    @Override
    public void setPid(final int pid) {
        this.pid = pid;
    }

    @Override
    public void setParentPid(final int parentPid) {
        this.parentPid = parentPid;
    }

    @Override
    public void setUserMilliseconds(final long userMilliseconds) {
        this.userMilliseconds = userMilliseconds;
    }

    @Override
    public void setSystemMilliseconds(final long systemMilliseconds) {
        this.systemMilliseconds = systemMilliseconds;
    }

    @Override
    public void setElapsedMilliseconds(final long elapsedMilliseconds) {
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    @Override
    public void setResidentBytes(final long residentBytes) {
        this.residentBytes = residentBytes;
    }

    @Override
    public void setVirtualBytes(final long virtualBytes) {
        this.virtualBytes = virtualBytes;
    }

    @Override
    public void setMajorFaults(final long majorFaults) {
        this.majorFaults = majorFaults;
    }

    @Override
    public void setMinorFaults(final long minorFaults) {
        this.minorFaults = minorFaults;
    }

    protected long toLong(final String value) {
        return Long.parseLong(value);
    }

    protected int toInt(final String value) {
        return Integer.parseInt(value);
    }

    protected long unsignedLongToLong(final String value) {
        final BigDecimal bigValue = new BigDecimal(value);
        if (MAX_LONG_VALUE.compareTo(bigValue) >= 0) {
            return bigValue.longValue();
        } else {
            return MAX_LONG_VALUE.subtract(bigValue).longValue();
        }
    }

}
