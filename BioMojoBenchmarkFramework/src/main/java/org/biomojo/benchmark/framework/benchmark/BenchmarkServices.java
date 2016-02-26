package org.biomojo.benchmark.framework.benchmark;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.biomojo.GlobalConst;
import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.DNA;
import org.biomojo.alphabet.SangerQuality;
import org.biomojo.benchmark.framework.procutil.LinuxProcStatFile;
import org.biomojo.benchmark.framework.procutil.LinuxProcessInfo;
import org.biomojo.benchmark.framework.procutil.LinuxProcessUtil;
import org.biomojo.benchmark.framework.procutil.LinuxTimeCommandResult;
import org.biomojo.benchmark.framework.procutil.LinuxTimeCommandUtil;
import org.biomojo.io.FixedWidthSeqIdHeaderBuilder;
import org.biomojo.io.SeqOutput;
import org.biomojo.io.fastx.FastaOutput;
import org.biomojo.io.fastx.FastqOutput;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.util.DbUtil;
import org.java0.collection.tuple.TwoTuple;
import org.java0.core.exception.UncheckedException;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

@Named
public class BenchmarkServices {
    private static final Logger logger = LoggerFactory.getLogger(BenchmarkServices.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    DbUtil dbUtil;

    private static final int SAMPLING_INTERVAL = 200;

    @SuppressWarnings("unchecked")
    @Transactional
    private Map<String, Object> executeBenchmark(final Map<String, Object> parameters) {
        int exitStatus = 0;

        final BenchmarkRun benchmarkRun = new BenchmarkRun(parameters.get(Params.RUN_GROUP).toString(),
                (Long) parameters.get(Params.RUN_NUMBER), parameters.get(Params.LIBRARY).toString(),
                parameters.get(Params.BENCHMARK).toString());

        File processOutputFile = null;

        benchmarkRun.setParameters(parameters.entrySet());

        try {
            processOutputFile = File.createTempFile("output", ".txt");

            // convert all command line parameters to Strings
            final List<String> commandLine = ((List<Object>) parameters.get(Params.COMMAND_LINE)).stream()
                    .map(s -> s.toString()).collect(Collectors.toList());

            final TwoTuple<List<String>, String> timeCommand = LinuxTimeCommandUtil.prepareCommandLine(commandLine);

            logger.info("Command line: {}", timeCommand.get0());

            final ProcessBuilder processBuilder = new ProcessBuilder(timeCommand.get0());
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

            statFiles.forEach(f -> {
                try {
                    f.close();
                } catch (final Exception e) {
                    logger.error("Caught exception in auto-generated catch block", e);
                }

            });

            exitStatus = process.exitValue();

            benchmarkRun.setEndTime(new Timestamp(System.currentTimeMillis()));

            Runtime.getRuntime().removeShutdownHook(closer);

            final String standardOutput = new String(Files.readAllBytes(processOutputFile.toPath()));

            benchmarkRun.setStandardOut(standardOutput);

            final LinuxTimeCommandResult tcr = LinuxTimeCommandUtil.parseResult(timeCommand.get1());

            if (tcr != null) {
                benchmarkRun.copyInfo(tcr);
                // Are these ever different that process exit status?
                exitStatus = tcr.getExitStatus();

            } else {
                logger.error("Couldn't parse time command result, process output: [{}]",
                        standardOutput.substring(0, standardOutput.length() > 1000 ? 1000 : standardOutput.length()));
            }

            // This catches the case where the Java process blows up before we
            // can even catch any OutOfMemory exceptions
            if (standardOutput
                    .startsWith("\nException: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler")) {
                logger.info("Deletectet uncaught out of memory error. changing exit code from {} to {}", exitStatus,
                        GlobalConst.OUT_OF_MEMORY_EXIT_CODE);
                exitStatus = GlobalConst.OUT_OF_MEMORY_EXIT_CODE;
            }

            if (exitStatus != 0 && exitStatus != GlobalConst.OUT_OF_MEMORY_EXIT_CODE) {
                logger.info("Received exit status {}, process output: [{}]", exitStatus,
                        standardOutput.substring(0, standardOutput.length() > 1000 ? 1000 : standardOutput.length()));
            }

        } catch (IOException | SecurityException | InterruptedException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        } finally {
            if (processOutputFile != null) {
                processOutputFile.delete();
            }
        }

        benchmarkRun.setExitStatus(exitStatus);

        entityManager.persist(benchmarkRun);

        final Long runGroupId = (Long) parameters.get(Params.RUN_GROUP_ID);
        if (runGroupId != null) {
            final BenchmarkRunGroup benchmarkRunGroup = entityManager.find(BenchmarkRunGroup.class, runGroupId);
            benchmarkRunGroup.add(benchmarkRun);
            entityManager.persist(benchmarkRunGroup);
        }

        final Map<String, Object> results = new HashMap<>();
        results.put(Params.RESIDENT_BYTES, benchmarkRun.residentBytes);
        results.put(Params.EXIT_STATUS, exitStatus);

        logger.info("Returning results to script");

        return results;

    }

    protected <A extends ByteAlphabet, T extends ByteSeq<A>> long createOutputFile(final Supplier<T> seqSupplier,
            final SeqOutput<T> outputStream, final int numSequences) throws IOException {

        long totalLength = 0;

        // Start seqNum at 1 because BioPerl blows up with a sequence id of
        // zero!
        for (int i = 1; i <= numSequences; ++i) {
            final T seq = seqSupplier.get();
            totalLength += seq.sizeL();
            seq.setId(i);
            outputStream.write(seq);
        }

        return totalLength;

    }

    public long createRandomSequenceFileWithRandomLengthSeqs(final String fileName, final int numSeqs,
            final int averageLength) {
        final Random random = new Random();
        final Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return random.nextInt(averageLength) + 1;
            }

            @Override
            public String toString() {
                return "random sequence length supplier, average length = " + averageLength;
            }
        };
        return createRandomSequenceFile(fileName, numSeqs, supplier);

    }

    public long createRandomSequenceFileWithFixedLengthSeqs(final String fileName, final int numSeqs,
            final int length) {

        final Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return length;
            }

            @Override
            public String toString() {
                return "fixed sequence length supplier, length = " + length;
            }
        };
        return createRandomSequenceFile(fileName, numSeqs, supplier);
    }

    public long createRandomSequenceFile(final String fileName, final int numSeqs,
            final Supplier<Integer> lengthGenerator) {

        logger.info("Creating random file {}, numSeqs {}, lengthGenerator [{}]", fileName, numSeqs, lengthGenerator);

        final String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
        long totalLength = 0;

        try {
            if (fileType.equals("fasta")) {

                final FastaOutput<DNA> output = new FastaOutput<>(
                        new BufferedOutputStream(new FileOutputStream(fileName)), new FixedWidthSeqIdHeaderBuilder(8),
                        Integer.MAX_VALUE);

                totalLength = createOutputFile(new RandomFastaSeqSupplier<DNA>(AlphabetId.DNA, lengthGenerator),

                        output, numSeqs);
                output.close();

            } else if (fileType.equals("fastq")) {

                final FastqOutput<DNA, SangerQuality> output = new FastqOutput<>(
                        new BufferedOutputStream(new FileOutputStream(fileName)), new FixedWidthSeqIdHeaderBuilder(8),
                        Integer.MAX_VALUE);

                totalLength = createOutputFile(new RandomFastqSeqSupplier<DNA, SangerQuality>(AlphabetId.DNA,
                        AlphabetId.QUALITY_SANGER, lengthGenerator),

                        output, numSeqs);
                output.close();
            } else {
                throw new UncheckedException("Invalid filetype: " + fileType);
            }

            logger.info("Created file {}", fileName);
            return totalLength;
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
            throw new UncheckedIOException(e);
        }
    }

    public void deleteFile(final String fileName) {
        new File(fileName).delete();
    }

    public String createTempFile(final String directory, final String prefix, final String fileExtension) {
        File outputFile;
        try {
            logger.info("Attempting to create temp file, prefix = {}, fileExtension = {}, directory = {}", prefix,
                    fileExtension, directory);
            outputFile = File.createTempFile(prefix, "." + fileExtension, new File(directory));
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
            throw new UncheckedException(e);
        }
        return outputFile.getAbsolutePath().toString();
    }

    @Transactional
    public long newBenchmarkRunGroup(final String runGroup) {
        final BenchmarkRunGroup group = new BenchmarkRunGroup(runGroup);
        entityManager.persist(group);
        entityManager.flush();
        return group.getId();
    }

    public Map<String, Object> runBenchmark(final Object object) {
        Map<String, Object> benchmarkParams;
        final Object converted = JavaScriptUtil.convertValue(object, false);
        if (converted instanceof Map) {
            benchmarkParams = (Map<String, Object>) converted;
        } else {
            throw new UncheckedException("runBenchmark not called with a Map");
        }

        logger.info("Executing benchmark, benchmarkParams = {}", benchmarkParams);
        return executeBenchmark(benchmarkParams);
    }

    public void log(final String message) {
        logger.info("SCRIPT LOGGING: {}", message);
    }

}
