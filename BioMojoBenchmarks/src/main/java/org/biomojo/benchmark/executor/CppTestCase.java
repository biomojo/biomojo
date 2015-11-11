package org.biomojo.benchmark.executor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CppTestCase extends TestCase {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(CppTestCase.class.getName());

    /**
     * Create a new GenericTestCase.
     *
     * @param benchmark
     * @param library
     */
    public CppTestCase(Benchmark benchmark, Library library) {
        super(benchmark, library);

    }

    /**
     * @see org.biomojo.benchmark.executor.TestCase#getCommandLine(java.util.List)
     */
    @Override
    public List<String> getCommandLine() {
        List<String> commandLine = new ArrayList<String>();
        if (config.get(ConfigParams.INTERPRETER) != null) {
            commandLine.add(config.get(ConfigParams.INTERPRETER));
        }

        commandLine.add(config.get(ConfigParams.PROGRAM_BASE_DIR) + "/" + config.get(ConfigParams.PROGRAM_SUB_DIR) + "/"
                + config.get(ConfigParams.PROGRAM_NAME));

        commandLine.add(benchmark.toString());

        if (config.get(ConfigParams.ENCODED) != null) {
            commandLine.add("true");
        } else {
            commandLine.add("false");
        }

        if (config.get(ConfigParams.INPUT_FILE) != null) {
            commandLine.add(config.get(ConfigParams.INPUT_FILE));
        }
        if (config.get(ConfigParams.OUTPUT_FILE) != null) {
            commandLine.add(config.get(ConfigParams.OUTPUT_FILE));
        }
        if (config.get(ConfigParams.CUTOFF) != null) {
            commandLine.add(config.get(ConfigParams.CUTOFF));
        }
        return commandLine;
    }
}
