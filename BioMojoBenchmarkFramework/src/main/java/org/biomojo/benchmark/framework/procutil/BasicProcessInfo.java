package org.biomojo.benchmark.framework.procutil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface BasicProcessInfo {
    public static final int MISSING_VALUE = -1;
    public static final String MISSING_STRING_VALUE = "";

    public int getPid();

    public int getParentPid();

    public long getUserMilliseconds();

    public long getSystemMilliseconds();

    public long getElapsedMilliseconds();

    public long getResidentBytes();

    public long getVirtualBytes();

    public long getMajorFaults();

    public long getMinorFaults();

    public void setPid(int pid);

    public void setParentPid(int parentPid);

    public void setUserMilliseconds(long userMilliseconds);

    public void setSystemMilliseconds(long systemMilliseconds);

    public void setElapsedMilliseconds(long elapsedMilliseconds);

    public void setResidentBytes(long residentBytes);

    public void setVirtualBytes(long virtualBytes);

    public void setMajorFaults(long majorFaults);

    public void setMinorFaults(long minorFaults);

    public default void copyInfo(final BasicProcessInfo info) {
        if (info != null) {
            copyIfNotMissing(this::setPid, info::getPid);
            copyIfNotMissing(this::setParentPid, info::getParentPid);
            copyIfNotMissing(this::setUserMilliseconds, info::getUserMilliseconds);
            copyIfNotMissing(this::setSystemMilliseconds, info::getSystemMilliseconds);
            copyIfNotMissing(this::setElapsedMilliseconds, info::getElapsedMilliseconds);
            copyIfNotMissing(this::setResidentBytes, info::getResidentBytes);
            copyIfNotMissing(this::setVirtualBytes, info::getVirtualBytes);
            copyIfNotMissing(this::setMajorFaults, info::getMajorFaults);
            copyIfNotMissing(this::setMinorFaults, info::getMinorFaults);
        }
    }

    public default <T extends Number> void copyIfNotMissing(final Consumer<T> set, final Supplier<T> get) {
        final T value = get.get();
        if (value.longValue() != MISSING_VALUE) {
            set.accept(value);
        }
    }
}
