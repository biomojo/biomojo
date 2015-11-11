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

// TODO: Auto-generated Javadoc
/**
 * The Class BlastIteration.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "iterNum", "queryID", "queryDef", "queryLen", "hits", "message", "statistics" })
@XmlRootElement(name = "Iteration")
@Entity
public class BlastIteration {
    
    /** The id. */
    @TableGenerator(name = "BlastIterationGenerator", allocationSize = 1000)
    @Id
    @GeneratedValue(generator = "BlastIterationGenerator")
    @XmlTransient
    private long id;

    /** The iter num. */
    @XmlElement(name = "Iteration_iter-num", required = true)
    protected int iterNum;

    /** The query id. */
    @XmlElement(name = "Iteration_query-ID")
    protected String queryID;

    /** The query def. */
    @XmlElement(name = "Iteration_query-def")
    protected String queryDef;

    /** The query len. */
    @XmlElement(name = "Iteration_query-len")
    protected int queryLen;

    /** The hits. */
    @XmlElement(name = "Hit")
    @XmlElementWrapper(name = "Iteration_hits")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BlastIteration_id")
    protected List<BlastHit> hits;

    /** The statistics. */
    @XmlElement(name = "Iteration_stat")
    @XmlJavaTypeAdapter(BlastIterationStatAdapter.class)
    @OneToOne(cascade = CascadeType.ALL)
    protected BlastStatistics statistics;

    /** The message. */
    @XmlElement(name = "Iteration_message")
    protected String message;

    /** The query sequence. */
    @XmlTransient
    @ManyToOne(cascade = CascadeType.ALL)
    protected ByteSeqImpl<ByteAlphabet> querySequence;

    /** The blast output. */
    @XmlTransient
    @ManyToOne
    protected BlastOutput blastOutput;

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
     * Gets the iter num.
     *
     * @return the iter num
     */
    public int getIterNum() {
        return iterNum;
    }

    /**
     * Sets the iter num.
     *
     * @param iterNum the new iter num
     */
    public void setIterNum(final int iterNum) {
        this.iterNum = iterNum;
    }

    /**
     * Gets the query id.
     *
     * @return the query id
     */
    public String getQueryID() {
        return queryID;
    }

    /**
     * Sets the query id.
     *
     * @param iterationQueryID the new query id
     */
    public void setQueryID(final String iterationQueryID) {
        this.queryID = iterationQueryID;
    }

    /**
     * Gets the query def.
     *
     * @return the query def
     */
    public String getQueryDef() {
        return queryDef;
    }

    /**
     * Sets the query def.
     *
     * @param iterationQueryDef the new query def
     */
    public void setQueryDef(final String iterationQueryDef) {
        this.queryDef = iterationQueryDef;
    }

    /**
     * Gets the query len.
     *
     * @return the query len
     */
    public int getQueryLen() {
        return queryLen;
    }

    /**
     * Sets the query len.
     *
     * @param iterationQueryLen the new query len
     */
    public void setQueryLen(final int iterationQueryLen) {
        this.queryLen = iterationQueryLen;
    }

    /**
     * Gets the hits.
     *
     * @return the hits
     */
    public List<BlastHit> getHits() {
        return hits;
    }

    /**
     * Sets the hits.
     *
     * @param hit the new hits
     */
    public void setHits(final List<BlastHit> hit) {
        this.hits = hit;
    }

    /**
     * Gets the statistics.
     *
     * @return the statistics
     */
    public BlastStatistics getStatistics() {
        return statistics;
    }

    /**
     * Sets the statistics.
     *
     * @param statistics the new statistics
     */
    public void setStatistics(final BlastStatistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(final String message) {
        this.message = message;
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
     * Gets the query sequence.
     *
     * @return the query sequence
     */
    public ByteSeqImpl<ByteAlphabet> getQuerySequence() {
        return querySequence;
    }

    /**
     * Sets the query sequence.
     *
     * @param querySequence the new query sequence
     */
    public void setQuerySequence(final ByteSeqImpl<ByteAlphabet> querySequence) {
        this.querySequence = querySequence;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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
