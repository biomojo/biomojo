package org.biomojo.examples.commands;

import java.io.InputStream;

import org.java0.cli.AbstractCommand;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "taxonomyload", commandDescription = "Load NCBI Taxonomy Files")
public class TaxonomyLoadCommand extends AbstractCommand {
	@Parameter(names = { "-i", "--input" }, required = true, description = "Input File Name")
	private InputStream inputStream;

	public enum TYPE {
		DIVISION, NODES, NAMES, GENCODE
	};

	@Parameter(names = { "-t", "--type" }, required = true, description = "Input File Type (division, nodes, names, gencode)")
	private TYPE type;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	/**
	 * @see org.java0.cli.Command#run()
	 */
	@Override
	public void run() {
	}

}
