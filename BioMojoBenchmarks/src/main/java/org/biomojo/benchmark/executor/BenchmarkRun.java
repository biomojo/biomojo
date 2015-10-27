/*
 * Copyright (C) 2014  Hugh Eaves
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import org.biomojo.core.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
@SuppressWarnings("serial")
@Entity
public class BenchmarkRun extends AbstractEntity {
    private static final Logger logger = LoggerFactory.getLogger(BenchmarkRun.class.getName());

    private String runGroup;
    @Column(length = 10000)
    private Timestamp startTime;
    private Timestamp endTime;
    @Enumerated(EnumType.STRING)
    private Library library;
    @Enumerated(EnumType.STRING)
    private Benchmark benchmark;
    private int exitStatus;
    private long userMilliseconds;
    private long systemMilliseconds;
    private long maxResidentBytes;
    private long avgResidentBytes;
    private long majorFaults;
    private long minorFaults;
    private long elapsedMilliseconds;
    @Column(length = Integer.MAX_VALUE)
    private String standardOut;
    @Column(length = Integer.MAX_VALUE)
    private String gcLog;
    private int runNumber;

    @ElementCollection
    Map<String, String> config = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<BenchmarkSample> samples = new ArrayList<BenchmarkSample>();

    public BenchmarkRun() {

    }

    public BenchmarkRun(String runGroup) {
        this.runGroup = runGroup;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public List<BenchmarkSample> getSamples() {
        return samples;
    }

    public void setSamples(List<BenchmarkSample> samples) {
        this.samples = samples;
    }

    public void addProcesInfo(Map<LinuxProcStatField, Object> info) {
        samples.add(new BenchmarkSample(info));
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Benchmark getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    public void runBenchmark(int runNumber, TestCase testCase) {
        File processOutputFile = null;

        this.runNumber = runNumber;
        this.library = testCase.getLibrary();
        this.benchmark = testCase.getBenchmark();
        this.config.putAll(testCase.getConfig());

        try {

            processOutputFile = File.createTempFile("output", ".txt");

            List<String> command = testCase.getCommandLine();

            LinuxTimeCommand timeCapture = new LinuxTimeCommand();
            command = timeCapture.prepareCommandLine(command);
            logger.info("Running benchmark {} {} {}", library, benchmark, command);

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(processOutputFile);

            Process process = processBuilder.start();
            Thread closer = new Thread() {
                @Override
                public void run() {
                    System.out.println("Running shutdown hook");
                    process.destroy();
                }
            };

            Runtime.getRuntime().addShutdownHook(closer);

            setStartTime(new Timestamp(System.currentTimeMillis()));

            process.waitFor();
            // LinuxProcStat procStat = new LinuxProcStat(process);

            // while (process.isAlive()) {
            // Map<LinuxProcStatField, Object> info = procStat.getInfo();
            // if (info != null) {
            // addProcesInfo(info);
            // }
            // Thread.sleep(200);
            // }

            setExitStatus(process.exitValue());

            setEndTime(new Timestamp(System.currentTimeMillis()));

            Runtime.getRuntime().removeShutdownHook(closer);

            standardOut = new String(Files.readAllBytes(processOutputFile.toPath()));

            File gcLogFile = new File(testCase.get(ConfigParams.GC_LOG_FILE));
            if (gcLogFile.exists()) {
                gcLog = new String(Files.readAllBytes(gcLogFile.toPath()));
            }

            Map<LinuxTimeCommandField, Object> timeFields = timeCapture.getExecutionResults();
            if (timeFields != null) {
                userMilliseconds = (long) timeFields.get(LinuxTimeCommandField.USER_TIME);
                systemMilliseconds = (long) timeFields.get(LinuxTimeCommandField.SYSTEM_TIME);
                maxResidentBytes = (long) timeFields.get(LinuxTimeCommandField.MAX_RSS);
                avgResidentBytes = (long) timeFields.get(LinuxTimeCommandField.AVG_RSS);
                majorFaults = (long) timeFields.get(LinuxTimeCommandField.MAJOR_FAULTS);
                minorFaults = (long) timeFields.get(LinuxTimeCommandField.MINOR_FAULTS);
                elapsedMilliseconds = (long) timeFields.get(LinuxTimeCommandField.WALL_CLOCK_TIME);
            }

        } catch (IOException | SecurityException | InterruptedException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        } finally {
            if (processOutputFile != null) {
                processOutputFile.delete();
            }
        }
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

    public long getMaxResidentBytes() {
        return maxResidentBytes;
    }

    public void setMaxResidentBytes(long maxResidentBytes) {
        this.maxResidentBytes = maxResidentBytes;
    }

    public long getAvgResidentBytes() {
        return avgResidentBytes;
    }

    public void setAvgResidentBytes(long avgResidentBytes) {
        this.avgResidentBytes = avgResidentBytes;
    }

    public long getMajorFaults() {
        return majorFaults;
    }

    public void setMajorFaults(long majorFaults) {
        this.majorFaults = majorFaults;
    }

    public long getMinorFaults() {
        return minorFaults;
    }

    public void setMinorFaults(long minorFaults) {
        this.minorFaults = minorFaults;
    }

    public long getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    public void setElapsedMilliseconds(long elapsedMilliseconds) {
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    public String getStandardOut() {
        return standardOut;
    }

    public void setStandardOut(String standardOut) {
        this.standardOut = standardOut;
    }

    public String getGcLog() {
        return gcLog;
    }

    public void setGcLog(String gcLog) {
        this.gcLog = gcLog;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
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
    public void setExitStatus(int exitStatus) {
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
    public void setRunGroup(String runGroup) {
        this.runGroup = runGroup;
    }

}
