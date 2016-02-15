package org.biomojo.benchmark.framework.tests;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public abstract class AbstractCommandLinePreparer extends AbstractPreparer {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AbstractCommandLinePreparer.class);

    protected final String interpreter;
    protected final String programName;
    protected final String programSubdir;

    protected AbstractCommandLinePreparer(final String interpreter, final String programName,
            final String programSubdir) {
        this.interpreter = interpreter;
        this.programName = programName;
        this.programSubdir = programSubdir;
        this.setInputParams(ConfigParams.PROGRAM_BASE_DIR, ConfigParams.INPUT_FILE);
        this.setOutputParams(ConfigParams.COMMAND_LINE);
    }
}
