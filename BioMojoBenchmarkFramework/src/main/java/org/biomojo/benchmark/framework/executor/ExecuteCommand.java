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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.biomojo.benchmark.framework.procutil.LinuxProcStatFile;
import org.biomojo.benchmark.framework.procutil.LinuxProcessInfo;
import org.biomojo.benchmark.framework.procutil.LinuxProcessUtil;
import org.biomojo.benchmark.framework.procutil.LinuxTimeCommandInfo;
import org.biomojo.benchmark.framework.procutil.LinuxTimeCommandPrep;
import org.biomojo.benchmark.framework.procutil.LinuxTimeCommandResult;
import org.biomojo.benchmark.framework.tests.ConfigParams;
import org.biomojo.benchmark.framework.tests.DefaultParameters;
import org.biomojo.benchmark.framework.tests.Operation;
import org.biomojo.benchmark.framework.tests.ParamSetter;
import org.biomojo.benchmark.framework.tests.TestBuilder;
import org.biomojo.benchmark.framework.tests.TestParameters;
import org.biomojo.cli.AbstractSpringCommand;
import org.java0.collection.setmap.SetMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "execute")
@Named
public class ExecuteCommand extends AbstractSpringCommand {

    private static final String DEFAULT_DATA_DIR = "/data/bio/BioMojo";
    private static final String DEFAULT_PROGRAM_DIR = "/home/hugh/code/biobench";
    private static final String OUTPUT_FILE_PREFIX = "output_";
    private static final String TESTDATA_FILE_PREFIX = "testdata_";
    private static final int SAMPLING_INTERVAL = 200;

    private static final Logger logger = LoggerFactory.getLogger(ExecuteCommand.class.getName());

    @Parameter(names = { "-b", "--benchmark" }, required = true, description = "Benchmark to execute")
    protected Benchmark benchmark;

    @Parameter(names = { "-d", "--datadir" }, description = "Directory for datafiles")
    protected String dataDir = DEFAULT_DATA_DIR;

    @Parameter(names = { "-e", "--executabledir" }, description = "Base directory for executables")
    protected String programBaseDir = DEFAULT_PROGRAM_DIR;

    @Parameter(names = { "-g", "--group" }, required = true, description = "Group id")
    protected String runGroup;

    @Parameter(names = { "-i", "--in" }, description = "Input file name")
    protected String inputFileName;

    @Parameter(names = { "-j", "--jvmopts" }, description = "JVM command line options")
    protected String jvmOpts;

    @Parameter(names = { "-l", "--seqlength" }, description = "Length of sequences")
    protected Integer seqLength;

    @Parameter(names = { "-n", "--numseqs" }, description = "Number of sequences")
    protected Integer numSeqs;

    @Parameter(names = { "-q", "--qscore" }, description = "Quality score for trim filter")
    protected Integer cutoff;

    @Parameter(names = { "-k", "--kmerlength" }, description = "Kmer length")
    protected Integer kmerLength;

    @Parameter(names = { "-r", "--runs" }, required = true, description = "Run group")
    protected int numRuns = 5;

    @Parameter(names = { "-x", "--libraries" }, variableArity = true)
    protected Set<Library> libraries = new HashSet<>();

    @PersistenceContext
    private EntityManager entityManager;
    /**
     *
     */

    Random random = new Random();

    /**
     * Create a new ExecuteCommand.
     *
     * @param configLocation
     */
    public ExecuteCommand() {
        super("benchmark-context.xml");
    }

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {

        List<TestParameters> caseParams = new ArrayList<>();
        caseParams.add(new DefaultParameters());

        caseParams = addParams(caseParams, getMainParams());
        caseParams = mergeParams(caseParams, ConfigParams.BENCHMARK, TestBuilder.getInputPreparers());

        caseParams = multiplyMergeParams(caseParams, ConfigParams.BENCHMARK, TestBuilder.getTestCases());

        // clear out test cases that we don't need (based on libraries command
        // line setting)
        if (libraries.size() > 0) {
            caseParams = caseParams.stream().filter(k -> libraries.contains(k.get(ConfigParams.LIBRARY)))
                    .collect(Collectors.toList());
        }

        for (int runNum = 0; runNum < numRuns; ++runNum) {
            Collections.shuffle(caseParams);

            for (final TestParameters testParams : caseParams) {

                List<TestParameters> currentTest = Collections.singletonList(new DefaultParameters(testParams));
                currentTest = mergeParams(currentTest, ConfigParams.BENCHMARK, TestBuilder.getOutputPreparers());
                currentTest = mergeParams(currentTest, ConfigParams.LIBRARY, TestBuilder.getCommandLinePreparers());
                currentTest = addParams(currentTest, new LinuxTimeCommandPrep());
                currentTest = addParams(currentTest, new ParamSetter(ConfigParams.RUN_NUMBER, runNum));

                final TestParameters testCase = currentTest.get(0);

                logger.info("Running testCase " + testCase);

                runBenchmark(testCase);

            }
        }
    }

    @Override
    public void validate() {
        if ((inputFileName == null) && (numSeqs == null || seqLength == null)) {
            throw new ParameterException("Must specify input file or numseqs and seqlength");
        }
        if (benchmark == Benchmark.TRIM && cutoff == null) {
            throw new ParameterException("Must specify cutoff for trim benchmark");
        }
        if (benchmark == Benchmark.COUNT_KMERS && kmerLength == null) {
            throw new ParameterException("Must specify kmer length for count kmers benchmark");
        }
    }

    /**
     * @param config
     * @param inputFileName
     * @param builder
     * @throws IOException
     */
    @Transactional
    private void runBenchmark(final TestParameters parameters) {
        final BenchmarkRun benchmarkRun = new BenchmarkRun(runGroup,
                parameters.get(ConfigParams.RUN_NUMBER, Integer.class), parameters.getAsString(ConfigParams.LIBRARY),
                parameters.getAsString(ConfigParams.BENCHMARK_NAME));

        File processOutputFile = null;

        benchmarkRun.setParameters(parameters.entrySet());

        try {
            processOutputFile = File.createTempFile("output", ".txt");

            final List<String> command = (List<String>) parameters.get(ConfigParams.COMMAND_LINE);
            logger.info("Command line: {}", command);

            final ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            processBuilder.redirectOutput(processOutputFile);

            final Process process = processBuilder.start();

            final Thread closer = new Thread() {
                @Override
                public void run() {
                    System.err.println("Running shutdown hook");
                    process.destroy();
                }
            };

            Runtime.getRuntime().addShutdownHook(closer);

            final long startTimeMillis = System.currentTimeMillis();
            benchmarkRun.setStartTime(new Timestamp(startTimeMillis));

            final Collection<LinuxProcStatFile> statFiles = LinuxProcessUtil
                    .getChildStatFiles(LinuxProcessUtil.getPid(process));

            while (process.isAlive()) {
                for (final LinuxProcStatFile statFile : statFiles) {
                    final LinuxProcessInfo info = statFile.getInfo();
                    if (info != null) {
                        benchmarkRun.addProcesInfo(info);
                    }
                }
                // Don't just sleep for SAMPLING_INTERVAL ms
                // We need to account for jitter and delay, so we always
                // try to keep synced up with multiples of the
                // start time
                final long elapsedTime = System.currentTimeMillis() - startTimeMillis;
                final long timeUntilNextSample = (SAMPLING_INTERVAL - elapsedTime % SAMPLING_INTERVAL);
                logger.debug("elapsed time {}, sleeping for {}", elapsedTime, timeUntilNextSample);
                Thread.sleep(timeUntilNextSample);
            }

            benchmarkRun.setExitStatus(process.exitValue());

            benchmarkRun.setEndTime(new Timestamp(System.currentTimeMillis()));

            Runtime.getRuntime().removeShutdownHook(closer);

            benchmarkRun.setStandardOut(new String(Files.readAllBytes(processOutputFile.toPath())));

            final LinuxTimeCommandResult tcr = new LinuxTimeCommandResult();
            tcr.execute(parameters);
            final LinuxTimeCommandInfo timeInfo = (LinuxTimeCommandInfo) parameters
                    .get(ConfigParams.LINUX_TIME_COMMAND_INFO);
            if (timeInfo != null) {
                benchmarkRun.copyInfo(timeInfo);
                parameters.remove(ConfigParams.LINUX_TIME_COMMAND_INFO);
            }

            if (parameters.isPresentAndTrue(ConfigParams.DELETE_OUTPUT_FILE)) {
                final File outputFile = (File) parameters.get(ConfigParams.OUTPUT_FILE);
                outputFile.delete();
            }

        } catch (IOException | SecurityException | InterruptedException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        } finally {
            if (processOutputFile != null) {
                processOutputFile.delete();
            }
        }

        entityManager.persist(benchmarkRun);

    }

    /**
     * @param testCase
     */
    protected Operation getMainParams() {

        final Operation preparer = new ParamSetter(ConfigParams.PROGRAM_BASE_DIR, programBaseDir,
                ConfigParams.OUTPUT_FILE_PREFIX, OUTPUT_FILE_PREFIX, ConfigParams.TESTDATA_FILE_PREFIX,
                TESTDATA_FILE_PREFIX, ConfigParams.DATA_DIR, dataDir, ConfigParams.BENCHMARK_NAME,
                benchmark.name().toLowerCase(), ConfigParams.BENCHMARK, benchmark).addOptional(
                        ConfigParams.SEQUENCE_LENGTH, seqLength, ConfigParams.NUM_SEQUENCES, numSeqs,
                        ConfigParams.INPUT_FILE_NAME, inputFileName, ConfigParams.JVM_OPTS, jvmOpts,
                        ConfigParams.KMER_LENGTH, kmerLength, ConfigParams.CUTOFF, cutoff);

        return preparer;
    }

    protected List<TestParameters> addPrep(final List<TestParameters> parametersList,
            final Collection<Operation> preparers) {
        for (final TestParameters params : parametersList) {
            for (final Operation preparer : preparers) {
                preparer.execute(params);
            }
        }
        return parametersList;
    }

    protected List<TestParameters> addParams(final List<TestParameters> parametersList, final Operation preparer) {
        for (final TestParameters params : parametersList) {
            preparer.execute(params);
        }
        return parametersList;
    }

    protected List<TestParameters> multiplyParams(final List<TestParameters> parametersList,
            final Collection<Operation> preparers) {
        final List<TestParameters> newParms = new ArrayList<>();
        for (final TestParameters params : parametersList) {
            for (final Operation preparer : preparers) {
                final TestParameters newTestParams = new DefaultParameters(params);
                preparer.execute(newTestParams);
                newParms.add(newTestParams);
            }
        }
        return newParms;
    }

    private List<TestParameters> mergeParams(final List<TestParameters> caseParams, final String key,
            final SetMap<? extends Object, Operation> preparersSetMap) {
        for (final TestParameters test : caseParams) {
            final Object caseKey = test.get(key);
            final Set<Operation> preparers = preparersSetMap.get(caseKey);
            if (preparers != null) {
                for (final Operation preparer : preparers) {
                    preparer.execute(test);
                }
            }
        }
        return caseParams;
    }

    private List<TestParameters> multiplyMergeParams(final List<TestParameters> parametersList, final String key,
            final SetMap<? extends Object, Operation> testCases) {

        final List<TestParameters> newParms = new ArrayList<>();

        for (final TestParameters params : parametersList) {
            final Object caseKey = params.get(key);
            final Set<Operation> preparers = testCases.get(caseKey);
            if (preparers != null) {
                for (final Operation preparer : preparers) {
                    final TestParameters newTestParams = new DefaultParameters(params);
                    preparer.execute(newTestParams);
                    newParms.add(newTestParams);
                }
            }
        }
        return newParms;
    }

}
