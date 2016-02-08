/*
 * Copyright (C) 2015  Hugh Eaves
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.biomojo.benchmark.executor;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Entity;

import org.biomojo.core.AbstractEntity;

/**
 * @author Hugh Eaves
 *
 */
@SuppressWarnings("serial")
@Entity
public class BenchmarkSample extends AbstractEntity {

    private Timestamp sampleTime;
    private int pid;
    private int parentPid;
    private long userMilliseconds;
    private long systemMilliseconds;
    private long residentBytes;
    private long totalBytes;
    private long majorFaults;
    private long minorFaults;
    private long childMajorFaults;

    public Timestamp getSampleTime() {
        return sampleTime;
    }

    public void setSampleTime(Timestamp sampleTime) {
        this.sampleTime = sampleTime;
    }

    public long getUserMilliseconds() {
        return userMilliseconds;
    }

    public void setUserMilliseconds(long userMilliseconds) {
        this.userMilliseconds = userMilliseconds;
    }

    public long getSystemMilliseconds() {
        return systemMilliseconds;
    }

    public void setSystemMilliseconds(long systemMilliseconds) {
        this.systemMilliseconds = systemMilliseconds;
    }

    public long getChildMajorFaults() {
        return childMajorFaults;
    }

    public void setChildMajorFaults(long childMajorFaults) {
        this.childMajorFaults = childMajorFaults;
    }

    public long getChildMinorFaults() {
        return childMinorFaults;
    }

    public void setChildMinorFaults(long childMinorFaults) {
        this.childMinorFaults = childMinorFaults;
    }

    public long getChildUserMillis() {
        return childUserMillis;
    }

    public void setChildUserMillis(long childUserMillis) {
        this.childUserMillis = childUserMillis;
    }

    public long getChildSystemMillis() {
        return childSystemMillis;
    }

    public void setChildSystemMillis(long childSystemMillis) {
        this.childSystemMillis = childSystemMillis;
    }

    private long childMinorFaults;
    private long childUserMillis;
    private long childSystemMillis;

    public BenchmarkSample(Map<LinuxProcStatField, Object> info) {
        sampleTime = new Timestamp(System.currentTimeMillis());
        this.pid = (int) info.get(LinuxProcStatField.PID);
        this.parentPid = (int) info.get(LinuxProcStatField.PPID);
        this.userMilliseconds = (long) info.get(LinuxProcStatField.USER_TIME);
        this.systemMilliseconds = (long) info.get(LinuxProcStatField.SYSTEM_TIME);
        this.residentBytes = (long) info.get(LinuxProcStatField.RSS);
        this.totalBytes = (long) info.get(LinuxProcStatField.TOTAL_MEM);
        this.majorFaults = (long) info.get(LinuxProcStatField.MAJOR_FAULTS);
        this.minorFaults = (long) info.get(LinuxProcStatField.MINOR_FAULTS);
        this.childMajorFaults = (long) info.get(LinuxProcStatField.CHILD_MAJOR_FAULTS);
        this.childMinorFaults = (long) info.get(LinuxProcStatField.CHILD_MINOR_FAULTS);
        this.childUserMillis = (long) info.get(LinuxProcStatField.CHILD_USER_TIME);
        this.childSystemMillis = (long) info.get(LinuxProcStatField.CHILD_SYSTEM_TIME);
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getParentPid() {
        return parentPid;
    }

    public void setParentPid(int parentPid) {
        this.parentPid = parentPid;
    }

    public long getResidentBytes() {
        return residentBytes;
    }

    public void setResidentBytes(long residentBytes) {
        this.residentBytes = residentBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    /**
     * @return the majorFaults
     */
    public long getMajorFaults() {
        return majorFaults;
    }

    /**
     * @param majorFaults
     *            the majorFaults to set
     */
    public void setMajorFaults(long majorFaults) {
        this.majorFaults = majorFaults;
    }

    /**
     * @return the minorFaults
     */
    public long getMinorFaults() {
        return minorFaults;
    }

    /**
     * @param minorFaults
     *            the minorFaults to set
     */
    public void setMinorFaults(long minorFaults) {
        this.minorFaults = minorFaults;
    }

}
