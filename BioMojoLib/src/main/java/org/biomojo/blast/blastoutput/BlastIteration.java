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
package org.biomojo.blast.blastoutput;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.sequence.ByteSeqImpl;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "iterNum", "queryID", "queryDef", "queryLen", "hits", "message", "statistics" })
@XmlRootElement(name = "Iteration")
@Entity
public class BlastIteration {
    @TableGenerator(name = "BlastIterationGenerator", allocationSize = 1000)
    @Id
    @GeneratedValue(generator = "BlastIterationGenerator")
    @XmlTransient
    private long id;

    @XmlElement(name = "Iteration_iter-num", required = true)
    protected int iterNum;

    @XmlElement(name = "Iteration_query-ID")
    protected String queryID;

    @XmlElement(name = "Iteration_query-def")
    protected String queryDef;

    @XmlElement(name = "Iteration_query-len")
    protected int queryLen;

    @XmlElement(name = "Hit")
    @XmlElementWrapper(name = "Iteration_hits")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BlastIteration_id")
    protected List<BlastHit> hits;

    @XmlElement(name = "Iteration_stat")
    @XmlJavaTypeAdapter(BlastIterationStatAdapter.class)
    @OneToOne(cascade = CascadeType.ALL)
    protected BlastStatistics statistics;

    @XmlElement(name = "Iteration_message")
    protected String message;

    @XmlTransient
    @ManyToOne(cascade = CascadeType.ALL)
    protected ByteSeqImpl<ByteAlphabet> querySequence;

    @XmlTransient
    @ManyToOne
    protected BlastOutput blastOutput;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public int getIterNum() {
        return iterNum;
    }

    public void setIterNum(final int iterNum) {
        this.iterNum = iterNum;
    }

    public String getQueryID() {
        return queryID;
    }

    public void setQueryID(final String iterationQueryID) {
        this.queryID = iterationQueryID;
    }

    public String getQueryDef() {
        return queryDef;
    }

    public void setQueryDef(final String iterationQueryDef) {
        this.queryDef = iterationQueryDef;
    }

    public int getQueryLen() {
        return queryLen;
    }

    public void setQueryLen(final int iterationQueryLen) {
        this.queryLen = iterationQueryLen;
    }

    public List<BlastHit> getHits() {
        return hits;
    }

    public void setHits(final List<BlastHit> hit) {
        this.hits = hit;
    }

    public BlastStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(final BlastStatistics statistics) {
        this.statistics = statistics;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public BlastOutput getBlastOutput() {
        return blastOutput;
    }

    public void setBlastOutput(final BlastOutput blastOutput) {
        this.blastOutput = blastOutput;
    }

    public ByteSeqImpl<ByteAlphabet> getQuerySequence() {
        return querySequence;
    }

    public void setQuerySequence(final ByteSeqImpl<ByteAlphabet> querySequence) {
        this.querySequence = querySequence;
    }

    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Blast Iteration:\n");

        buf.append("id: " + id + "\n");
        buf.append("iterationIterNum: " + iterNum + "\n");
        buf.append("iterationQueryID: " + queryID + "\n");
        buf.append("iterationQueryDef: " + queryDef + "\n");
        buf.append("iterationQueryLen: " + queryLen + "\n");

        return buf.toString();
    }

}
