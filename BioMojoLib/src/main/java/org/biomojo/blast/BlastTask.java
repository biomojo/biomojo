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

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.blast.blastoutput.BlastOutput;
import org.biomojo.sequence.AbstractSeqList;
import org.biomojo.sequence.BasicByteSeq;

// TODO: Auto-generated Javadoc
/**
 * The Class BlastTask.
 */
@Entity
public class BlastTask {

    /** The id. */
    @TableGenerator(name = "BlastTaskGenerator", allocationSize = 1)
    @Id
    @GeneratedValue(generator = "BlastTaskGenerator")
    private long id;

    /** The blast data set. */
    @ManyToOne
    private BlastDataSet blastDataSet;

    /** The query sequences. */
    @ManyToOne(cascade = CascadeType.ALL)
    private AbstractSeqList<BasicByteSeq<ByteAlphabet>> querySequences;

    /** The database sequences. */
    @ManyToOne(cascade = CascadeType.ALL)
    private AbstractSeqList<BasicByteSeq<ByteAlphabet>> databaseSequences;

    /** The blast output. */
    @OneToOne(cascade = CascadeType.ALL)
    private BlastOutput blastOutput;

    /** The start time. */
    private Date startTime;

    /** The end time. */
    private Date endTime;

    /** The status. */
    @Enumerated(EnumType.STRING)
    private BlastTaskStatus status;

    /** The version. */
    @Version
    private short version;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Gets the blast data set.
     *
     * @return the blast data set
     */
    public BlastDataSet getBlastDataSet() {
        return blastDataSet;
    }

    /**
     * Sets the blast data set.
     *
     * @param blastDataset the new blast data set
     */
    public void setBlastDataSet(final BlastDataSet blastDataset) {
        this.blastDataSet = blastDataset;
    }

    /**
     * Gets the blast output.
     *
     * @return the blast output
     */
    public BlastOutput getBlastOutput() {
        return blastOutput;
    }

    /**
     * Sets the blast output.
     *
     * @param blastOutput the new blast output
     */
    public void setBlastOutput(final BlastOutput blastOutput) {
        this.blastOutput = blastOutput;
    }

    /**
     * Gets the query sequences.
     *
     * @return the query sequences
     */
    public AbstractSeqList<BasicByteSeq<ByteAlphabet>> getQuerySequences() {
        return querySequences;
    }

    /**
     * Sets the query sequences.
     *
     * @param querySequences the new query sequences
     */
    public void setQuerySequences(final AbstractSeqList<BasicByteSeq<ByteAlphabet>> querySequences) {
        this.querySequences = querySequences;
    }

    /**
     * Gets the database sequences.
     *
     * @return the database sequences
     */
    public AbstractSeqList<BasicByteSeq<ByteAlphabet>> getDatabaseSequences() {
        return databaseSequences;
    }

    /**
     * Sets the database sequences.
     *
     * @param databaseSequences the new database sequences
     */
    public void setDatabaseSequences(final AbstractSeqList<BasicByteSeq<ByteAlphabet>> databaseSequences) {
        this.databaseSequences = databaseSequences;
    }

    /**
     * Gets the start time.
     *
     * @return the start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     *
     * @param startTime the new start time
     */
    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time.
     *
     * @return the end time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     *
     * @param endTime the new end time
     */
    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public BlastTaskStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(final BlastTaskStatus status) {
        this.status = status;
    }
}
