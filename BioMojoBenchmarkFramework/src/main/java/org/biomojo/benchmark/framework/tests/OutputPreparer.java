package org.biomojo.benchmark.framework.tests;

import java.io.File;
import java.io.IOException;

import org.java0.core.exception.UncheckedException;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class OutputPreparer extends AbstractPreparer {
    private static final Logger logger = LoggerFactory.getLogger(OutputPreparer.class);

    private final String fileType;

    public OutputPreparer(final String fileType) {
        this.setInputParams(ConfigParams.DATA_DIR, ConfigParams.OUTPUT_FILE_PREFIX);
        this.setOutputParams(ConfigParams.DELETE_OUTPUT_FILE, ConfigParams.OUTPUT_FILE);
        this.fileType = fileType;
    }

    @Override
    public void safeExecute(final TestParameters params) {
        final String dataDir = params.getAsString(ConfigParams.DATA_DIR);
        final String prefix = params.getAsString(ConfigParams.OUTPUT_FILE_PREFIX);

        File outputFile;
        try {
            outputFile = File.createTempFile(prefix, "." + fileType, new File(dataDir));
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
            throw new UncheckedException(e);
        }

        params.put(ConfigParams.DELETE_OUTPUT_FILE, true);
        params.put(ConfigParams.OUTPUT_FILE, outputFile);

    }
}
