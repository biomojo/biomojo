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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class BlastOutput.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "", propOrder = { "program", "version", "reference", "db", "queryID", "queryDef", "queryLen",
        "querySeq", "parameters", "iterations", "statistics" })
@XmlRootElement(name = "BlastOutput")
@Entity
public class BlastOutput {
    
    /** The id. */
    @TableGenerator(name = "BlastOutputGenerator", allocationSize = 1000)
    @Id
    @GeneratedValue(generator = "BlastOutputGenerator")
    @XmlTransient
    private long id;

    /** The program. */
    @XmlElement(name = "BlastOutput_program", required = true)
    protected String program;

    /** The version. */
    @XmlElement(name = "BlastOutput_version", required = true)
    protected String version;

    /** The reference. */
    @XmlElement(name = "BlastOutput_reference", required = true)
    @Transient
    protected String reference;

    /** The db. */
    @XmlElement(name = "BlastOutput_db", required = true)
    protected String db;

    /** The query id. */
    @XmlElement(name = "BlastOutput_query-ID", required = true)
    protected String queryID;

    /** The query def. */
    @XmlElement(name = "BlastOutput_query-def", required = true)
    protected String queryDef;

    /** The query len. */
    @XmlElement(name = "BlastOutput_query-len", required = true)
    protected int queryLen;

    /** The query seq. */
    @XmlElement(name = "BlastOutput_query-seq")
    @XmlJavaTypeAdapter(StringToByteArrayAdapter.class)
    @Column(length = Integer.MAX_VALUE)
    protected byte[] querySeq;

    /** The parameters. */
    @XmlElement(name = "BlastOutput_param", required = true)
    @XmlJavaTypeAdapter(BlastOutputParamAdapter.class)
    @OneToOne(cascade = CascadeType.ALL)
    protected BlastParameters parameters;

    /** The iterations. */
    @XmlElement(name = "Iteration")
    @XmlElementWrapper(name = "BlastOutput_iterations", required = true)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BlastOutput_id")
    protected List<BlastIteration> iterations;

    /** The statistics. */
    @XmlElement(name = "BlastOutput_mbstat")
    @XmlJavaTypeAdapter(BlastOutputStatAdapter.class)
    @OneToOne(cascade = CascadeType.ALL)
    protected BlastStatistics statistics;

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
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the program.
     *
     * @return the program
     */
    public String getProgram() {
        return program;
    }

    /**
     * Sets the program.
     *
     * @param program the new program
     */
    public void setProgram(String program) {
        this.program = program;
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version the new version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the reference.
     *
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the reference.
     *
     * @param reference the new reference
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Gets the db.
     *
     * @return the db
     */
    public String getDb() {
        return db;
    }

    /**
     * Sets the db.
     *
     * @param db the new db
     */
    public void setDb(String db) {
        this.db = db;
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
     * @param queryID the new query id
     */
    public void setQueryID(String queryID) {
        this.queryID = queryID;
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
     * @param queryDef the new query def
     */
    public void setQueryDef(String queryDef) {
        this.queryDef = queryDef;
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
     * @param queryLen the new query len
     */
    public void setQueryLen(int queryLen) {
        this.queryLen = queryLen;
    }

    /**
     * Query seq.
     *
     * @return the byte[]
     */
    public byte[] querySeq() {
        return querySeq;
    }

    /**
     * Sets the query seq.
     *
     * @param querySeq the new query seq
     */
    public void setQuerySeq(byte[] querySeq) {
        this.querySeq = querySeq;
    }

    /**
     * Gets the parameters.
     *
     * @return the parameters
     */
    public BlastParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the parameters.
     *
     * @param parameters the new parameters
     */
    public void setParameters(BlastParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Gets the iterations.
     *
     * @return the iterations
     */
    public List<BlastIteration> getIterations() {
        return iterations;
    }

    /**
     * Sets the iterations.
     *
     * @param iterations the new iterations
     */
    public void setIterations(List<BlastIteration> iterations) {
        this.iterations = iterations;
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
    public void setStatistics(BlastStatistics statistics) {
        this.statistics = statistics;
    }
}