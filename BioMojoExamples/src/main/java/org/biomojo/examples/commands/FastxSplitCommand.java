package org.biomojo.examples.commands;

import java.io.InputStream;

import org.java0.cli.AbstractCommand;
import org.java0.cli.InputFileConverter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "fastxsplit", commandDescription = "Split fasta/fastq file into parts based on number of records / number of files")
public class FastxSplitCommand extends AbstractCommand {

	@Parameter(names = { "-i", "--in" }, converter = InputFileConverter.class, description = "Input file name")
	private InputStream inputStream;

	@Parameter(names = { "-p", "--prefix" }, description = "Output file(s) prefix")
	private String outputPrefix;

	@Parameter(names = { "-s", "--suffix" }, description = "Output file(s) suffix")
	private String outputSuffix;

	@Parameter(names = { "-n", "--numrecords" }, description = "Split the input file with numrecords in each file")
	private Integer numRecords;

	@Parameter(names = { "-f", "--numfiles" }, description = "Split the input file into numfiles parts")
	private Integer numFiles;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getOutputPrefix() {
		return outputPrefix;
	}

	public void setOutputPrefix(String outputPrefix) {
		this.outputPrefix = outputPrefix;
	}

	public Integer getNumRecords() {
		return numRecords;
	}

	public void setNumRecords(Integer numRecords) {
		this.numRecords = numRecords;
	}

	public Integer getNumFiles() {
		return numFiles;
	}

	public void setNumFiles(Integer numFiles) {
		this.numFiles = numFiles;
	}

	@Override
	public void validate() throws ParameterException {
		if (getNumRecords() == null && getNumFiles() == null) {
			throw new ParameterException(
					"Either number of records or number of files is required");
		}
	}

	public String getOutputSuffix() {
		return outputSuffix;
	}

	public void setOutputSuffix(String outputSuffix) {
		this.outputSuffix = outputSuffix;
	}

	/**
	 * @see org.java0.cli.Command#run()
	 */
	@Override
	public void run() {
	}
}
