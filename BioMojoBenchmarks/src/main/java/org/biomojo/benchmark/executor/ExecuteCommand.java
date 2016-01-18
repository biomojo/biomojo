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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.biomojo.benchmark.RandomFastaGenerator;
import org.biomojo.benchmark.RandomFastqGenerator;
import org.biomojo.cli.AbstractSpringCommand;
import org.java0.collection.setmap.HashSetMap;
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

    private static final String OUTPUT_FILE_PREFIX = "output_";

    private static final Logger logger = LoggerFactory.getLogger(ExecuteCommand.class.getName());

    @Parameter(names = { "-b", "--benchmark" }, required = true, description = "Benchmark to execute")
    protected Benchmark benchmark;

    @Parameter(names = { "-d", "--datadir" }, description = "Directory for datafiles")
    protected String dataDir = "/data/bio/BioMojo";

    @Parameter(names = { "-e", "--executabledir" }, description = "Base directory for executables")
    protected String programBaseDir = "/home/hugh/code/biobench";

    @Parameter(names = { "-g", "--group" }, required = true, description = "Group id")
    protected String runGroup;

    @Parameter(names = { "-i", "--in" }, description = "Input file name")
    protected File inputFile;

    @Parameter(names = { "-j", "--jvmopts" }, description = "JVM command line options")
    protected String jvmOpts;

    @Parameter(names = { "-l", "--seqlength" }, description = "Length of sequences")
    protected Integer seqLength;

    @Parameter(names = { "-n", "--numseqs" }, description = "Number of sequences")
    protected Integer numSeqs;

    @Parameter(names = { "-q", "--qscore" }, description = "Quality score for trim filter")
    protected int cutoff = 35;

    @Parameter(names = { "-r", "--runs" }, required = true, description = "Run group")
    protected int numRuns = 5;

    @Parameter(names = { "-x", "--libraries" }, variableArity = true)
    protected List<String> libraryNames = new ArrayList<>();

    protected File outputFile;

    protected boolean deleteInput;

    SetMap<Benchmark, TestCase> testCasesMap = new HashSetMap<>();

    @PersistenceContext
    private EntityManager entityManager;
    /**
     *
     */
    private static final String TESTDATA_FILE_PREFIX = "testdata_";

    Random random = new Random();
    File gcLogFile = null;

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
        try {
            final Set<Library> libraries = new HashSet<>();
            if (libraryNames.size() > 0) {
                for (final String libraryName : libraryNames) {
                    libraries.add(Library.valueOf(libraryName));
                }
            } else {
                for (final Library library : Library.values()) {
                    libraries.add(library);
                }
            }
            prepareTestData();
            prepareTestCases();

            final List<TestCase> caseList = new ArrayList<>(testCasesMap.get(benchmark));

            for (int runNumber = 0; runNumber < numRuns; ++runNumber) {

                Collections.shuffle(caseList);

                for (final TestCase testCase : caseList) {
                    if (libraries.contains(testCase.getLibrary())) {
                        logger.info("Running testCase " + testCase);
                        gcLogFile = File.createTempFile("gclog", ".log", new File(dataDir));
                        prepareOutputFile();
                        updateTestCase(testCase);
                        runBenchmark(runNumber, testCase);
                        gcLogFile.delete();
                        outputFile.delete();
                    } else {
                        logger.info("Skipping testCase " + testCase);
                    }
                }
            }

        } catch (final FileNotFoundException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        } finally {
            if (deleteInput) {
                inputFile.delete();
            }
        }
    }

    @Override
    public void validate() {
        if ((inputFile == null) && (numSeqs == null || seqLength == null)) {
            throw new ParameterException("Must specify input file or numseqs and seqlength");
        }
    }

    /**
     * @param benchmark2
     */
    private void prepareTestData() {
        if (inputFile != null) {
            return;
        }

        logger.info("Preparing test data");
        deleteInput = true;
        try {
            switch (benchmark) {
            case READ_FASTQ:
            case LOAD_FASTQ:
                inputFile = File.createTempFile(TESTDATA_FILE_PREFIX, ".fastq", new File(dataDir));
                new RandomFastqGenerator().createFile(inputFile, numSeqs, seqLength);
                break;
            case READ_FASTA:
            case LOAD_FASTA:
                inputFile = File.createTempFile(TESTDATA_FILE_PREFIX, ".fasta", new File(dataDir));
                new RandomFastaGenerator().createFile(inputFile, numSeqs, seqLength);
                break;
            case TRIM:
                inputFile = File.createTempFile(TESTDATA_FILE_PREFIX, ".fastq", new File(dataDir));

                new RandomFastqGenerator().createFile(inputFile, numSeqs, seqLength);
                break;
            default:
                throw new IllegalArgumentException("Can't generate input file for given benchmark");
            }
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }
    }

    private void prepareOutputFile() {
        logger.info("Preparing output file");

        try {
            switch (benchmark) {
            case READ_FASTQ:
            case LOAD_FASTQ:
            case TRIM:
                outputFile = File.createTempFile(OUTPUT_FILE_PREFIX, ".fastq", new File(dataDir));
                break;
            case READ_FASTA:
            case LOAD_FASTA:
            case TRANSLATE:
                outputFile = File.createTempFile(OUTPUT_FILE_PREFIX, ".fasta", new File(dataDir));
                break;
            default:
                outputFile = File.createTempFile(OUTPUT_FILE_PREFIX, ".txt", new File(dataDir));
                break;

            }
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }

    }

    /**
     * @param config
     * @param inputFileName
     * @param builder
     * @throws IOException
     */
    @Transactional
    private void runBenchmark(final int runNumber, final TestCase testCase) {
        final BenchmarkRun benchmarkRun = new BenchmarkRun(runGroup);
        benchmarkRun.runBenchmark(runNumber, testCase);
        entityManager.persist(benchmarkRun);
    }

    private void prepareTestCases() {
        addTestCase(new JavaTestCase(Benchmark.LOAD_FASTA, Library.BIOJAVA));
        addTestCase(new JavaTestCase(Benchmark.LOAD_FASTA, Library.BIOMOJO));
        addTestCase(new JavaTestCase(Benchmark.LOAD_FASTA, Library.BIOMOJO).add(ConfigParams.ENCODED, true));
        addTestCase(new PerlTestCase(Benchmark.LOAD_FASTA, Library.BIOPERL));
        addTestCase(new GenericTestCase(Benchmark.LOAD_FASTA, Library.BIOPYTHON));
        addTestCase(new GenericTestCase(Benchmark.LOAD_FASTA, Library.HTSEQ));
        addTestCase(new JavaTestCase(Benchmark.LOAD_FASTA, Library.HTSJDK));
        addTestCase(new JavaTestCase(Benchmark.LOAD_FASTA, Library.JEBL));
        addTestCase(new CppTestCase(Benchmark.LOAD_FASTA, Library.SEQAN));
        addTestCase(new CppTestCase(Benchmark.LOAD_FASTA, Library.SEQAN).add(ConfigParams.ENCODED, true));

        addTestCase(new JavaTestCase(Benchmark.LOAD_FASTQ, Library.BIOJAVA));
        addTestCase(new JavaTestCase(Benchmark.LOAD_FASTQ, Library.BIOMOJO));
        addTestCase(new JavaTestCase(Benchmark.LOAD_FASTQ, Library.BIOMOJO).add(ConfigParams.ENCODED, true));
        addTestCase(new PerlTestCase(Benchmark.LOAD_FASTQ, Library.BIOPERL));
        addTestCase(new GenericTestCase(Benchmark.LOAD_FASTQ, Library.BIOPYTHON));
        addTestCase(new GenericTestCase(Benchmark.LOAD_FASTQ, Library.HTSEQ));
        addTestCase(new JavaTestCase(Benchmark.LOAD_FASTQ, Library.HTSJDK));
        addTestCase(new CppTestCase(Benchmark.LOAD_FASTQ, Library.SEQAN));
        addTestCase(new CppTestCase(Benchmark.LOAD_FASTQ, Library.SEQAN).add(ConfigParams.ENCODED, true));

        addTestCase(new JavaTestCase(Benchmark.READ_FASTA, Library.BIOJAVA));
        addTestCase(new JavaTestCase(Benchmark.READ_FASTA, Library.BIOMOJO));
        addTestCase(new JavaTestCase(Benchmark.READ_FASTA, Library.BIOMOJO).add(ConfigParams.ENCODED, true));
        addTestCase(new PerlTestCase(Benchmark.READ_FASTA, Library.BIOPERL));
        addTestCase(new GenericTestCase(Benchmark.READ_FASTA, Library.BIOPYTHON));
        addTestCase(new GenericTestCase(Benchmark.READ_FASTA, Library.HTSEQ));
        addTestCase(new JavaTestCase(Benchmark.READ_FASTA, Library.HTSJDK));
        addTestCase(new JavaTestCase(Benchmark.READ_FASTA, Library.JEBL));
        addTestCase(new CppTestCase(Benchmark.READ_FASTA, Library.SEQAN));
        addTestCase(new CppTestCase(Benchmark.READ_FASTA, Library.SEQAN).add(ConfigParams.ENCODED, true));

        addTestCase(new JavaTestCase(Benchmark.READ_FASTQ, Library.BIOJAVA));
        addTestCase(new JavaTestCase(Benchmark.READ_FASTQ, Library.BIOMOJO));
        addTestCase(new JavaTestCase(Benchmark.READ_FASTQ, Library.BIOMOJO).add(ConfigParams.ENCODED, true));
        addTestCase(new PerlTestCase(Benchmark.READ_FASTQ, Library.BIOPERL));
        addTestCase(new GenericTestCase(Benchmark.READ_FASTQ, Library.BIOPYTHON));
        addTestCase(new GenericTestCase(Benchmark.READ_FASTQ, Library.HTSEQ));
        addTestCase(new JavaTestCase(Benchmark.READ_FASTQ, Library.HTSJDK));
        addTestCase(new CppTestCase(Benchmark.READ_FASTQ, Library.SEQAN));
        addTestCase(new CppTestCase(Benchmark.READ_FASTQ, Library.SEQAN).add(ConfigParams.ENCODED, true));

        addTestCase(new JavaTestCase(Benchmark.TRIM, Library.BIOJAVA));
        addTestCase(new JavaTestCase(Benchmark.TRIM, Library.BIOMOJO));
        addTestCase(new JavaTestCase(Benchmark.TRIM, Library.BIOMOJO).add(ConfigParams.ENCODED, true));
        addTestCase(new PerlTestCase(Benchmark.TRIM, Library.BIOPERL));
        addTestCase(new GenericTestCase(Benchmark.TRIM, Library.BIOPYTHON));
        addTestCase(new GenericTestCase(Benchmark.TRIM, Library.HTSEQ));
        addTestCase(new JavaTestCase(Benchmark.TRIM, Library.HTSJDK));
        addTestCase(new CppTestCase(Benchmark.TRIM, Library.SEQAN));
        addTestCase(new CppTestCase(Benchmark.TRIM, Library.SEQAN).add(ConfigParams.ENCODED, true));
        addTestCase(new TrimmomaticTestCase(Benchmark.TRIM, Library.TRIMMOMATIC));

        addTestCase(new JavaTestCase(Benchmark.TRANSLATE, Library.BIOJAVA));
        addTestCase(new JavaTestCase(Benchmark.TRANSLATE, Library.BIOMOJO));
        addTestCase(new JavaTestCase(Benchmark.TRANSLATE, Library.BIOMOJO).add(ConfigParams.ENCODED, true));
        addTestCase(new PerlTestCase(Benchmark.TRANSLATE, Library.BIOPERL));
        addTestCase(new GenericTestCase(Benchmark.TRANSLATE, Library.BIOPYTHON));
        addTestCase(new CppTestCase(Benchmark.TRANSLATE, Library.SEQAN));
        addTestCase(new CppTestCase(Benchmark.TRANSLATE, Library.SEQAN).add(ConfigParams.ENCODED, true));
        addTestCase(new JavaTestCase(Benchmark.TRANSLATE, Library.JEBL));

        addTestCase(new JavaTestCase(Benchmark.ALIGN, Library.BIOJAVA));
        addTestCase(new JavaTestCase(Benchmark.ALIGN, Library.BIOMOJO));
        addTestCase(new JavaTestCase(Benchmark.ALIGN, Library.BIOMOJO).add(ConfigParams.ENCODED, true));
        addTestCase(new GenericTestCase(Benchmark.ALIGN, Library.BIOPYTHON));
        addTestCase(new JavaTestCase(Benchmark.ALIGN, Library.JEBL));
        addTestCase(new CppTestCase(Benchmark.ALIGN, Library.SEQAN));
        addTestCase(new CppTestCase(Benchmark.ALIGN, Library.SEQAN).add(ConfigParams.ENCODED, true));
    }

    private void addTestCase(final TestCase testCase) {
        updateTestCase(testCase);

        testCasesMap.add(testCase.getBenchmark(), testCase);
    }

    /**
     * @param testCase
     */
    protected void updateTestCase(final TestCase testCase) {
        testCase.add(ConfigParams.PROGRAM_BASE_DIR, programBaseDir);

        if (seqLength != null) {
            testCase.add(ConfigParams.SEQUENCE_LENGTH, seqLength);
        }
        if (numSeqs != null) {
            testCase.add(ConfigParams.NUM_SEQUENCES, numSeqs);
        }
        if (inputFile != null) {
            testCase.add(ConfigParams.INPUT_FILE, inputFile.getAbsolutePath());
        }
        if (gcLogFile != null) {
            testCase.add(ConfigParams.GC_LOG_FILE, gcLogFile.getAbsolutePath());
        }
        if (jvmOpts != null) {
            testCase.add(ConfigParams.JVM_OPTS, jvmOpts);
        }

        switch (testCase.getLibrary()) {
        case BIOJAVA:
            testCase.add(ConfigParams.PROGRAM_NAME, "BioJavaBenchmarks.jar");
            testCase.add(ConfigParams.PROGRAM_SUB_DIR, "BioJavaBenchmarks/target");
            break;
        case BIOMOJO:
            testCase.add(ConfigParams.PROGRAM_NAME, "BioMojoBenchmarks.jar");
            testCase.add(ConfigParams.PROGRAM_SUB_DIR, "BioMojoBenchmarks/target");
            break;
        case BIOPERL:
            testCase.add(ConfigParams.INTERPRETER, "perl");
            testCase.add(ConfigParams.PROGRAM_NAME, "main.pl");
            testCase.add(ConfigParams.PROGRAM_SUB_DIR, "BioPerlBenchmarks");
            break;
        case BIOPYTHON:
            testCase.add(ConfigParams.INTERPRETER, "python");
            testCase.add(ConfigParams.PROGRAM_NAME, "main.py");
            testCase.add(ConfigParams.PROGRAM_SUB_DIR, "BioPythonBenchmarks");
            break;
        case HTSEQ:
            testCase.add(ConfigParams.INTERPRETER, "python");
            testCase.add(ConfigParams.PROGRAM_NAME, "main.py");
            testCase.add(ConfigParams.PROGRAM_SUB_DIR, "HTSeqBenchmarks");
            break;
        case HTSJDK:
            testCase.add(ConfigParams.PROGRAM_NAME, "HTSJDKBenchmarks.jar");
            testCase.add(ConfigParams.PROGRAM_SUB_DIR, "HTSJDKBenchmarks/target");
            break;
        case JEBL:
            testCase.add(ConfigParams.PROGRAM_NAME, "JeblBenchmarks.jar");
            testCase.add(ConfigParams.PROGRAM_SUB_DIR, "JeblBenchmarks/target");
            break;
        case SEQAN:
            testCase.add(ConfigParams.PROGRAM_NAME, "SeqAnBenchmarks");
            testCase.add(ConfigParams.PROGRAM_SUB_DIR, "SeqAnBenchmarks/Release");
            break;
        case TRIMMOMATIC:
            testCase.add(ConfigParams.PROGRAM_NAME, "trimmomatic-0.33.jar");
            testCase.add(ConfigParams.PROGRAM_SUB_DIR, "Trimmomatic");
            break;

        default:
            throw new IllegalArgumentException();
        }

        switch (testCase.getBenchmark()) {
        case TRIM:
            testCase.add(ConfigParams.CUTOFF, cutoff);
        case TRANSLATE:
        case ALIGN:
            if (outputFile != null) {
                testCase.add(ConfigParams.OUTPUT_FILE, outputFile.getAbsolutePath());
            }
            break;
        default:

        }
    }

}
