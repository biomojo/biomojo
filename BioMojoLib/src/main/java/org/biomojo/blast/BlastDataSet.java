/*
 * Copyright (C) 2015  Hugh Eaves
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.biomojo.blast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.core.AbstractPropertiedEntity;
import org.biomojo.core.CommonProperties;
import org.biomojo.sequence.AbstractSeqList;
import org.biomojo.sequence.BasicByteSeq;

// TODO: Auto-generated Javadoc
/**
 * The Class BlastDataSet.
 */
@Entity
public class BlastDataSet extends AbstractPropertiedEntity {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The program. */
    @Enumerated(EnumType.STRING)
    private BlastProgram program;

    /** The blast tasks. */
    @OneToMany(mappedBy = "blastDataSet", cascade = CascadeType.ALL)
    private List<BlastTask> blastTasks = new ArrayList<BlastTask>();

    /** The query sequence lists. */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "BlastDataSet_QuerySequenceLists")
    private List<AbstractSeqList<BasicByteSeq<ByteAlphabet>>> querySequenceLists = new ArrayList<AbstractSeqList<BasicByteSeq<ByteAlphabet>>>();

    /** The database sequence lists. */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "BlastDataSet_DatabaseSequenceLists")
    private List<AbstractSeqList<BasicByteSeq<ByteAlphabet>>> databaseSequenceLists = new ArrayList<AbstractSeqList<BasicByteSeq<ByteAlphabet>>>();

    /** The date created. */
    private Date dateCreated;

    /**
     * Instantiates a new blast data set.
     */
    public BlastDataSet() {
    }

    /**
     * Create a new BlastDataSet.
     *
     * @param datasetName the dataset name
     */
    public BlastDataSet(final String datasetName) {
        setProp(CommonProperties.NAME, datasetName);
    }

    /**
     * Gets the blast tasks.
     *
     * @return the blast tasks
     */
    public List<BlastTask> getBlastTasks() {
        return blastTasks;
    }

    /**
     * Sets the blast tasks.
     *
     * @param blastTasks the new blast tasks
     */
    public void setBlastTasks(final List<BlastTask> blastTasks) {
        this.blastTasks = blastTasks;
    }

    /**
     * Gets the query sequence lists.
     *
     * @return the query sequence lists
     */
    public List<AbstractSeqList<BasicByteSeq<ByteAlphabet>>> getQuerySequenceLists() {
        return (List<AbstractSeqList<BasicByteSeq<ByteAlphabet>>>) querySequenceLists;
    }

    /**
     * Sets the query sequence lists.
     *
     * @param querySequenceLists the new query sequence lists
     */
    public void setQuerySequenceLists(final List<AbstractSeqList<BasicByteSeq<ByteAlphabet>>> querySequenceLists) {
        this.querySequenceLists = (List<AbstractSeqList<BasicByteSeq<ByteAlphabet>>>) querySequenceLists;
    }

    /**
     * Gets the database sequence lists.
     *
     * @return the database sequence lists
     */
    public List<AbstractSeqList<BasicByteSeq<ByteAlphabet>>> getDatabaseSequenceLists() {
        return (List<AbstractSeqList<BasicByteSeq<ByteAlphabet>>>) databaseSequenceLists;
    }

    /**
     * Sets the database sequence lists.
     *
     * @param databaseSequenceLists the new database sequence lists
     */
    public void setDatabaseSequenceLists(final List<AbstractSeqList<BasicByteSeq<ByteAlphabet>>> databaseSequenceLists) {
        this.databaseSequenceLists = databaseSequenceLists;
    }

    /**
     * Gets the date created.
     *
     * @return the date created
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the date created.
     *
     * @param dateCreated the new date created
     */
    public void setDateCreated(final Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Gets the program.
     *
     * @return the program
     */
    public BlastProgram getProgram() {
        return program;
    }

    /**
     * Sets the program.
     *
     * @param program the new program
     */
    public void setProgram(final BlastProgram program) {
        this.program = program;
    }

    /**
     * Adds the.
     *
     * @param blastTask the blast task
     */
    public void add(final BlastTask blastTask) {
        getBlastTasks().add(blastTask);
        blastTask.setBlastDataSet(this);
    }
}
