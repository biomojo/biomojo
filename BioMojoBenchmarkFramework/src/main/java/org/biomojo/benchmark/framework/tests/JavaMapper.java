package org.biomojo.benchmark.framework.tests;

import java.util.ArrayList;
import java.util.List;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class JavaMapper extends AbstractCommandLinePreparer {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(JavaMapper.class);

    protected JavaMapper(final String interpreter, final String programName, final String programSubdir) {
        super(interpreter, programName, programSubdir);
    }

    @Override
    public void safeExecute(final TestParameters params) {
        final List<String> commandLine = startCommandLine(params);

        commandLine.add(params.getAsString(ConfigParams.BENCHMARK_NAME));

        if (params.getAsString(ConfigParams.INPUT_FILE) != null) {
            commandLine.add("-i");
            commandLine.add(params.getAsString(ConfigParams.INPUT_FILE));
        }
        if (params.getAsString(ConfigParams.OUTPUT_FILE) != null) {
            commandLine.add("-o");
            commandLine.add(params.getAsString(ConfigParams.OUTPUT_FILE));
        }
        if (params.getAsString(ConfigParams.CUTOFF) != null) {
            commandLine.add("-q");
            commandLine.add(params.getAsString(ConfigParams.CUTOFF));
        }
        if (params.getAsString(ConfigParams.KMER_LENGTH) != null) {
            commandLine.add("-k");
            commandLine.add(params.getAsString(ConfigParams.KMER_LENGTH));
        }
        if (params.getAsString(ConfigParams.ENCODED) != null) {
            commandLine.add("-e");
        }
        params.put(ConfigParams.COMMAND_LINE, commandLine);
    }

    /**
     * @return
     */
    protected List<String> startCommandLine(final TestParameters params) {
        final List<String> commandLine = new ArrayList<>();
        commandLine.add("java");
        if (params.getAsString(ConfigParams.JVM_OPTS) != null) {
            final String jvmOpts = params.getAsString(ConfigParams.JVM_OPTS);
            final String[] opts = jvmOpts.split("\\s+");
            for (final String opt : opts) {
                commandLine.add(opt);
            }
        }
        // if (get(ConfigParams.GC_LOG_FILE) != null) {
        // commandLine.add("-Xloggc:" + get(ConfigParams.GC_LOG_FILE));
        // commandLine.add("-XX:+PrintGCDetails");
        // }
        commandLine.add("-jar");
        commandLine
                .add(params.getAsString(ConfigParams.PROGRAM_BASE_DIR) + "/" + this.programSubdir + "/" + programName);

        return commandLine;
    }

}
