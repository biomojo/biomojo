package org.biomojo.examples.commands;

import java.util.List;

import org.java0.cli.AbstractCommand;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "blastdatasetcreate", commandDescription = "Create Blast Dataset")
public class BlastDataSetCreateCommand extends AbstractCommand {

    @Parameter(names = { "-p", "--project" }, required = true, description = "Project name")
    private String projectName;

    @Parameter(names = { "-n", "--name" }, required = false, description = "Blast dataset name")
    private String datasetName;

    @Parameter(names = { "-r", "--program" }, required = true, description = "Blast program name")
    private String programName;

    @Parameter(names = { "-q", "--queries" }, required = true, description = "Name(s) of query rawData sets")
    private List<String> querySequenceLists;

    @Parameter(names = { "-d", "--databases" }, required = true, description = "Name(s) of database rawData sets")
    private List<String> databaseSequenceLists;

    @Parameter(names = { "-m", "--maxhits" }, required = true, description = "Blast parameter max_target_seqs")
    private int maxHits;

    @Parameter(names = { "-s", "--tasksize" }, required = true, description = "Number of query sequences per task")
    private int taskSize = 200;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<String> getQuerySequenceLists() {
        return querySequenceLists;
    }

    public void setQuerySequenceLists(List<String> querySequenceLists) {
        this.querySequenceLists = querySequenceLists;
    }

    public List<String> getDatabaseSequenceLists() {
        return databaseSequenceLists;
    }

    public void setDatabaseSequenceLists(List<String> databaseSequenceLists) {
        this.databaseSequenceLists = databaseSequenceLists;
    }

    public int getMaxHits() {
        return maxHits;
    }

    public void setMaxHits(int maxHits) {
        this.maxHits = maxHits;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public int getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(int taskSize) {
        this.taskSize = taskSize;
    }

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
    }

}
