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
package org.biomojo.benchmark.framework.executor;

import javax.persistence.Entity;

import org.biomojo.benchmark.framework.procutil.BasicProcessInfo;
import org.biomojo.core.AbstractEntity;

/**
 * @author Hugh Eaves
 *
 */
@SuppressWarnings("serial")
@Entity
public class BenchmarkSample extends AbstractEntity implements BasicProcessInfo {

    private final long sampleTimeMilliseconds;

    protected int pid = MISSING_VALUE;
    protected int parentPid = MISSING_VALUE;

    protected long userMilliseconds = MISSING_VALUE;
    protected long systemMilliseconds = MISSING_VALUE;
    protected long elapsedMilliseconds = MISSING_VALUE;

    protected long residentBytes = MISSING_VALUE;
    protected long virtualBytes = MISSING_VALUE;

    protected long majorFaults = MISSING_VALUE;
    protected long minorFaults = MISSING_VALUE;

    public BenchmarkSample(final BasicProcessInfo info) {
        sampleTimeMilliseconds = System.currentTimeMillis();
        copyInfo(info);
    }

    @Override
    public int getPid() {
        return pid;
    }

    @Override
    public void setPid(final int pid) {
        this.pid = pid;
    }

    @Override
    public int getParentPid() {
        return parentPid;
    }

    @Override
    public void setParentPid(final int parentPid) {
        this.parentPid = parentPid;
    }

    @Override
    public long getUserMilliseconds() {
        return userMilliseconds;
    }

    @Override
    public void setUserMilliseconds(final long userMilliseconds) {
        this.userMilliseconds = userMilliseconds;
    }

    @Override
    public long getSystemMilliseconds() {
        return systemMilliseconds;
    }

    @Override
    public void setSystemMilliseconds(final long systemMilliseconds) {
        this.systemMilliseconds = systemMilliseconds;
    }

    @Override
    public long getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    @Override
    public void setElapsedMilliseconds(final long elapsedMilliseconds) {
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    @Override
    public long getResidentBytes() {
        return residentBytes;
    }

    @Override
    public void setResidentBytes(final long residentBytes) {
        this.residentBytes = residentBytes;
    }

    @Override
    public long getVirtualBytes() {
        return virtualBytes;
    }

    @Override
    public void setVirtualBytes(final long virtualBytes) {
        this.virtualBytes = virtualBytes;
    }

    @Override
    public long getMajorFaults() {
        return majorFaults;
    }

    @Override
    public void setMajorFaults(final long majorFaults) {
        this.majorFaults = majorFaults;
    }

    @Override
    public long getMinorFaults() {
        return minorFaults;
    }

    @Override
    public void setMinorFaults(final long minorFaults) {
        this.minorFaults = minorFaults;
    }

    public long getSampleTimeMilliseconds() {
        return sampleTimeMilliseconds;
    }

}
