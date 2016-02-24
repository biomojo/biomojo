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
package org.biomojo.benchmark.framework.benchmark;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.biomojo.benchmark.framework.procutil.BasicProcessInfo;
import org.biomojo.benchmark.framework.procutil.LinuxProcessInfo;
import org.biomojo.core.AbstractPropertiedEntity;

/**
 * @author Hugh Eaves
 *
 */
@SuppressWarnings("serial")
@Entity
public class BenchmarkRun extends AbstractPropertiedEntity implements BasicProcessInfo {

    private String runGroup;
    private Timestamp startTime;
    private Timestamp endTime;
    private String library;
    private String benchmark;
    private int exitStatus;
    private long testId;
    private long runNumber;

    protected int pid = MISSING_VALUE;
    protected int parentPid = MISSING_VALUE;

    protected long userMilliseconds = MISSING_VALUE;
    protected long systemMilliseconds = MISSING_VALUE;
    protected long elapsedMilliseconds = MISSING_VALUE;

    protected long residentBytes = MISSING_VALUE;
    protected long virtualBytes = MISSING_VALUE;

    protected long majorFaults = MISSING_VALUE;
    protected long minorFaults = MISSING_VALUE;

    @Column(length = Integer.MAX_VALUE)
    private String standardOut;

    @ElementCollection
    Map<String, String> config = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<BenchmarkSample> samples = new ArrayList<BenchmarkSample>();

    public BenchmarkRun() {

    }

    public BenchmarkRun(final String runGroup, final long runNumber, final String library, final String benchmark) {
        this.runGroup = runGroup;
        this.runNumber = runNumber;
        this.library = library.toUpperCase();
        this.benchmark = benchmark.toUpperCase();
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(final Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(final Timestamp endTime) {
        this.endTime = endTime;
    }

    public List<BenchmarkSample> getSamples() {
        return samples;
    }

    public void setSamples(final List<BenchmarkSample> samples) {
        this.samples = samples;
    }

    public void addProcesInfo(final LinuxProcessInfo info) {
        samples.add(new BenchmarkSample(info));
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(final String library) {
        this.library = library;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(final String benchmark) {
        this.benchmark = benchmark;
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

    @Override
    public long getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    @Override
    public void setElapsedMilliseconds(final long elapsedMilliseconds) {
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    public String getStandardOut() {
        return standardOut;
    }

    public void setStandardOut(final String standardOut) {
        this.standardOut = standardOut;
    }

    public long getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(final long runNumber) {
        this.runNumber = runNumber;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(final Map<String, String> config) {
        this.config = config;
    }

    /**
     * @return the exitStatus
     */
    public int getExitStatus() {
        return exitStatus;
    }

    /**
     * @param exitStatus
     *            the exitStatus to set
     */
    public void setExitStatus(final int exitStatus) {
        this.exitStatus = exitStatus;
    }

    /**
     * @return the runGroup
     */
    public String getRunGroup() {
        return runGroup;
    }

    /**
     * @param runGroup
     *            the runGroup to set
     */
    public void setRunGroup(final String runGroup) {
        this.runGroup = runGroup;
    }

    public void setParameters(final Set<Entry<String, Object>> parameters) {
        for (final Entry<String, Object> entry : parameters) {
            String strValue = entry.getValue().toString();
            if (strValue.length() > 240) {
                strValue = strValue.substring(0, 240) + "<<<TRUNCATED";
            }
            config.put(entry.getKey(), strValue);
            setProp(entry.getKey(), entry.getValue());
        }
    }
}
