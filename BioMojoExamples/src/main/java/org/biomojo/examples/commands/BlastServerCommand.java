package org.biomojo.examples.commands;

import org.java0.cli.AbstractCommand;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "blastserver", commandDescription = "Run blast server")
public class BlastServerCommand extends AbstractCommand {
	@Parameter(names = { "-n", "--numthreads" }, required = true, description = "Number of blast execution threads")
	private int numThreads;

	public int getNumThreads() {
		return numThreads;
	}

	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	/**
	 * @see org.java0.cli.Command#run()
	 */
	@Override
	public void run() {
	}
}
