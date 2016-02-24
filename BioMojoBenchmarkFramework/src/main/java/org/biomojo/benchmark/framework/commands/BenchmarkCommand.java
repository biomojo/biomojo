package org.biomojo.benchmark.framework.commands;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.biomojo.benchmark.framework.benchmark.BenchmarkServices;
import org.biomojo.cli.AbstractSpringCommand;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "benchmark")
@Named
public class BenchmarkCommand extends AbstractSpringCommand {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(BenchmarkCommand.class);

    @Parameter(names = { "-c",
            "--config" }, description = "Benchmark config file", required = true, variableArity = true)
    protected List<String> configFiles;

    @Inject
    protected BenchmarkServices services;

    public BenchmarkCommand() {
        super("benchmark-context.xml");
    }

    @Override
    public void run() {
        final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            for (final String configFile : configFiles) {

                engine.eval(new FileReader(configFile));
            }
            final Invocable invocable = (Invocable) engine;
            invocable.invokeFunction("startBenchmark", services);
        } catch (FileNotFoundException | ScriptException | NoSuchMethodException e) {
            logger.error("Caught exception in auto-generated catch block", e);
            return;
        }
        // final BenchmarkExecutor executor = new BenchmarkExecutor();
        // executor.loadConfig(configFile);
    }

}
