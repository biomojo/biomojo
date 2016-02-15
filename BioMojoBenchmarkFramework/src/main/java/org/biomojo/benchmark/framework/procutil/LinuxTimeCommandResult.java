package org.biomojo.benchmark.framework.procutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.biomojo.benchmark.framework.tests.ConfigParams;
import org.biomojo.benchmark.framework.tests.TestParameters;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class LinuxTimeCommandResult extends AbstractLinuxTimeCommand {

    private static final Logger logger = LoggerFactory.getLogger(LinuxTimeCommandResult.class);

    public LinuxTimeCommandResult() {
        setInputParams(ConfigParams.TIME_OUTPUT_FILE);
    }

    @Override
    public void safeExecute(final TestParameters parameters) {

        final File timeOutputFile = (File) parameters.get(ConfigParams.TIME_OUTPUT_FILE);
        parameters.remove(ConfigParams.TIME_OUTPUT_FILE);
        try {
            final List<String> lines = Files.readAllLines(timeOutputFile.toPath());
            timeOutputFile.delete();

            if (lines.size() > 0) {

                final String[] parsed = lines.get(0).split("\\^");

                if (parsed.length == TIME_FIELDS.length) {
                    parameters.put(ConfigParams.LINUX_TIME_COMMAND_INFO, new LinuxTimeCommandInfo(parsed));

                } else {
                    logger.error("Unable to parse time command output: [{}]", lines.get(0));
                }
            } else {
                logger.error("Zero line file");
            }

        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }
    }
}
