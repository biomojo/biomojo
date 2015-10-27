package org.biomojo.examples.commands;

import java.io.File;

import org.java0.cli.AbstractCommand;
import org.java0.cli.InputFileConverter;
import org.java0.cli.OutputFileConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "fastxsort", commandDescription = "Sort a fast(a/q) file by rawData length")
public class FastxSortCommand extends AbstractCommand {
    private static Logger logger = LoggerFactory.getLogger(FastxSortCommand.class.getName());

    @Parameter(names = { "-i",
            "--in" }, required = true, converter = InputFileConverter.class, description = "Input file name")
    private File inputFile;

    @Parameter(names = { "-o",
            "--out" }, required = true, converter = OutputFileConverter.class, description = "Output file name")
    private File outputFile;

    public static void setLogger(Logger logger) {
        FastxSortCommand.logger = logger;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
    }
}
