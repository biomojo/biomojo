package org.biomojo.examples.commands;

import java.io.File;

import org.biomojo.cli.AbstractSpringCommand;
import org.biomojo.core.GlobalConst;
import org.biomojo.sequence.SequenceService;
import org.java0.cli.InputFileConverter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "fastxload", commandDescription = "Load FASTX file into database")
public class FastxLoadCommand extends AbstractSpringCommand {

	@Parameter(names = { "-i", "--in" }, required = true, converter = InputFileConverter.class, description = "Input file name")
	private File inputFile;

	@Parameter(names = { "-n", "--name" }, required = true, description = "Name of AbstractMultiSequence")
	private String name;

	@Parameter(names = { "-d", "--description" }, description = "Description of AbstractMultiSequence")
	private String description;

	@Parameter(names = { "-t", "--type" }, required = false, description = "BasicSequence Type")
	protected int sequenceType;

	SequenceService service;

	public FastxLoadCommand() {
		super(GlobalConst.LIB_SPRING_CONTEXT);
	}

	public File getInputFile() {
		return inputFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public int getSequenceType() {
		return sequenceType;
	}

	public void setSequenceType(int sequenceType) {
		this.sequenceType = sequenceType;
	}

	/**
	 * @see org.java0.cli.Command#run()
	 */
	@Override
	public void run() {
		service.loadFastxFile(getInputFile(), getName(), getDescription(),
				getSequenceType());
	}
}
