package org.biomojo.benchmark.framework.tests;

import java.io.File;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class InputFilePreparer extends AbstractPreparer {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(InputFilePreparer.class);

    public InputFilePreparer() {
        setInputParams(ConfigParams.INPUT_FILE_NAME, ConfigParams.DATA_DIR);
        setRequiredAbsent(ConfigParams.NUM_SEQUENCES, ConfigParams.SEQUENCE_LENGTH);
        setOutputParams(ConfigParams.INPUT_FILE);
    }

    @Override
    public void safeExecute(final TestParameters parameters) {
        final String dataDir = parameters.getAsString(ConfigParams.DATA_DIR);
        final String inputFileName = parameters.getAsString(ConfigParams.INPUT_FILE_NAME);

        final File inputFile = new File(dataDir + File.separator + inputFileName);

        logger.info("Using existing input file {}", inputFile);

        parameters.put(ConfigParams.INPUT_FILE, inputFile);
    }
}
