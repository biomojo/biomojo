package org.biomojo.examples.commands;

import org.java0.cli.AbstractCommand;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "projectcreate", commandDescription = "Create Project")
public class ProjectCreateCommand extends AbstractCommand {

	@Parameter(names = { "-p", "--project" }, required = true, description = "Project name")
	private String projectName;

	@Parameter(names = { "-d", "--description" }, required = true, description = "Project description")
	private String projectDescription;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	/**
	 * @see org.java0.cli.Command#run()
	 */
	@Override
	public void run() {
	}

}
