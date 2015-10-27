/*
 * Copyright (C) 2014  Hugh Eaves
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
import org.biomojo.sequence.ByteSeqImpl;
import org.biomojo.sequence.AbstractSeqList;

@Entity
public class BlastTask {

    @TableGenerator(name = "BlastTaskGenerator", allocationSize = 1)
    @Id
    @GeneratedValue(generator = "BlastTaskGenerator")
    private long id;

    @ManyToOne
    private BlastDataSet blastDataSet;

    @ManyToOne(cascade = CascadeType.ALL)
    private AbstractSeqList<ByteSeqImpl<ByteAlphabet>> querySequences;

    @ManyToOne(cascade = CascadeType.ALL)
    private AbstractSeqList<ByteSeqImpl<ByteAlphabet>> databaseSequences;

    @OneToOne(cascade = CascadeType.ALL)
    private BlastOutput blastOutput;

    private Date startTime;

    private Date endTime;

    @Enumerated(EnumType.STRING)
    private BlastTaskStatus status;

    @Version
    private short version;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public BlastDataSet getBlastDataSet() {
        return blastDataSet;
    }

    public void setBlastDataSet(final BlastDataSet blastDataset) {
        this.blastDataSet = blastDataset;
    }

    public BlastOutput getBlastOutput() {
        return blastOutput;
    }

    public void setBlastOutput(final BlastOutput blastOutput) {
        this.blastOutput = blastOutput;
    }

    public AbstractSeqList<ByteSeqImpl<ByteAlphabet>> getQuerySequences() {
        return querySequences;
    }

    public void setQuerySequences(final AbstractSeqList<ByteSeqImpl<ByteAlphabet>> querySequences) {
        this.querySequences = querySequences;
    }

    public AbstractSeqList<ByteSeqImpl<ByteAlphabet>> getDatabaseSequences() {
        return databaseSequences;
    }

    public void setDatabaseSequences(final AbstractSeqList<ByteSeqImpl<ByteAlphabet>> databaseSequences) {
        this.databaseSequences = databaseSequences;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    public BlastTaskStatus getStatus() {
        return status;
    }

    public void setStatus(final BlastTaskStatus status) {
        this.status = status;
    }
}
