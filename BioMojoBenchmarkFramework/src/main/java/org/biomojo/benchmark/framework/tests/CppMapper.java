package org.biomojo.benchmark.framework.tests;

import java.util.ArrayList;
import java.util.List;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class CppMapper extends AbstractCommandLinePreparer {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(CppMapper.class);

    protected CppMapper(final String interpreter, final String programName, final String programSubdir) {
        super(interpreter, programName, programSubdir);
    }

    @Override
    public void safeExecute(final TestParameters params) {
        final List<String> commandLine = new ArrayList<>();

        commandLine.add(params.getAsString(ConfigParams.PROGRAM_BASE_DIR) + "/" + programSubdir + "/" + programName);

        commandLine.add(params.getAsString(ConfigParams.BENCHMARK_NAME));

        if (params.getAsString(ConfigParams.ENCODED) != null) {
            commandLine.add("true");
        } else {
            commandLine.add("false");
        }

        if (params.getAsString(ConfigParams.INPUT_FILE) != null) {
            commandLine.add(params.getAsString(ConfigParams.INPUT_FILE));
        }
        if (params.getAsString(ConfigParams.OUTPUT_FILE) != null) {
            commandLine.add(params.getAsString(ConfigParams.OUTPUT_FILE));
        }
        if (params.getAsString(ConfigParams.CUTOFF) != null) {
            commandLine.add(params.getAsString(ConfigParams.CUTOFF));
        }
        if (params.getAsString(ConfigParams.KMER_LENGTH) != null) {
            commandLine.add(params.getAsString(ConfigParams.KMER_LENGTH));
        }

        params.put(ConfigParams.COMMAND_LINE, commandLine);
    }

}
