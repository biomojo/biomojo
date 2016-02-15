package org.biomojo.benchmark.framework.tests;

import java.io.File;
import java.io.IOException;

import org.java0.core.exception.UncheckedException;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public abstract class SequencesPreparer extends AbstractPreparer {
    private static final Logger logger = LoggerFactory.getLogger(SequencesPreparer.class);

    protected SequencesPreparer() {
        setInputParams(ConfigParams.DATA_DIR, ConfigParams.TESTDATA_FILE_PREFIX, ConfigParams.NUM_SEQUENCES,
                ConfigParams.SEQUENCE_LENGTH);
        setRequiredAbsent(ConfigParams.INPUT_FILE_NAME);
        setOutputParams(ConfigParams.DELETE_INPUT_FILE, ConfigParams.INPUT_FILE);
    }

    @Override
    public void safeExecute(final TestParameters params) {
        final String dataDir = params.getAsString(ConfigParams.DATA_DIR);
        final String prefix = params.getAsString(ConfigParams.TESTDATA_FILE_PREFIX);
        final Integer numSeqs = (Integer) params.get(ConfigParams.NUM_SEQUENCES);
        final Integer seqLength = (Integer) params.get(ConfigParams.SEQUENCE_LENGTH);

        File inputFile;
        try {
            inputFile = File.createTempFile(prefix, ".fasta", new File(dataDir));
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
            throw new UncheckedException(e);
        }

        createFile(numSeqs, seqLength, inputFile);

        params.put(ConfigParams.DELETE_INPUT_FILE, true);
        params.put(ConfigParams.INPUT_FILE, inputFile);
    }

    protected abstract void createFile(final Integer numSeqs, final Integer seqLength, File inputFile);
}
