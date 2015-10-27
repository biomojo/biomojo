package org.biomojo.examples.commands;

import org.java0.cli.AbstractCommand;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "blastdatasetdelete", commandDescription = "Delete Blast Dataset")
public class BlastDataSetDeleteCommand extends AbstractCommand {

    @Parameter(names = { "-n", "--name" }, required = true, description = "Project name")
    private String datasetName;

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
    }
}
