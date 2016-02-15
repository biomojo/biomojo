package org.biomojo.benchmark.framework.tests;

import java.util.List;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class TrimmomaticMapper extends JavaMapper {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(TrimmomaticMapper.class);

    protected TrimmomaticMapper(final String interpreter, final String programName, final String programSubdir) {
        super(interpreter, programName, programSubdir);
    }

    @Override
    public void safeExecute(final TestParameters params) {
        final List<String> commandLine = startCommandLine(params);

        commandLine.add("SE");
        commandLine.add("-phred33");
        commandLine.add(params.getAsString(ConfigParams.INPUT_FILE));
        commandLine.add(params.getAsString(ConfigParams.OUTPUT_FILE));
        commandLine.add("TRAILING:" + params.getAsString(ConfigParams.CUTOFF));

        params.put(ConfigParams.COMMAND_LINE, commandLine);
    }
}
