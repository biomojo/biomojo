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

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "", propOrder = { "program", "version", "reference", "db", "queryID", "queryDef", "queryLen",
        "querySeq", "parameters", "iterations", "statistics" })
@XmlRootElement(name = "BlastOutput")
@Entity
public class BlastOutput {
    @TableGenerator(name = "BlastOutputGenerator", allocationSize = 1000)
    @Id
    @GeneratedValue(generator = "BlastOutputGenerator")
    @XmlTransient
    private long id;

    @XmlElement(name = "BlastOutput_program", required = true)
    protected String program;

    @XmlElement(name = "BlastOutput_version", required = true)
    protected String version;

    @XmlElement(name = "BlastOutput_reference", required = true)
    @Transient
    protected String reference;

    @XmlElement(name = "BlastOutput_db", required = true)
    protected String db;

    @XmlElement(name = "BlastOutput_query-ID", required = true)
    protected String queryID;

    @XmlElement(name = "BlastOutput_query-def", required = true)
    protected String queryDef;

    @XmlElement(name = "BlastOutput_query-len", required = true)
    protected int queryLen;

    @XmlElement(name = "BlastOutput_query-seq")
    @XmlJavaTypeAdapter(StringToByteArrayAdapter.class)
    @Column(length = Integer.MAX_VALUE)
    protected byte[] querySeq;

    @XmlElement(name = "BlastOutput_param", required = true)
    @XmlJavaTypeAdapter(BlastOutputParamAdapter.class)
    @OneToOne(cascade = CascadeType.ALL)
    protected BlastParameters parameters;

    @XmlElement(name = "Iteration")
    @XmlElementWrapper(name = "BlastOutput_iterations", required = true)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BlastOutput_id")
    protected List<BlastIteration> iterations;

    @XmlElement(name = "BlastOutput_mbstat")
    @XmlJavaTypeAdapter(BlastOutputStatAdapter.class)
    @OneToOne(cascade = CascadeType.ALL)
    protected BlastStatistics statistics;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getQueryID() {
        return queryID;
    }

    public void setQueryID(String queryID) {
        this.queryID = queryID;
    }

    public String getQueryDef() {
        return queryDef;
    }

    public void setQueryDef(String queryDef) {
        this.queryDef = queryDef;
    }

    public int getQueryLen() {
        return queryLen;
    }

    public void setQueryLen(int queryLen) {
        this.queryLen = queryLen;
    }

    public byte[] querySeq() {
        return querySeq;
    }

    public void setQuerySeq(byte[] querySeq) {
        this.querySeq = querySeq;
    }

    public BlastParameters getParameters() {
        return parameters;
    }

    public void setParameters(BlastParameters parameters) {
        this.parameters = parameters;
    }

    public List<BlastIteration> getIterations() {
        return iterations;
    }

    public void setIterations(List<BlastIteration> iterations) {
        this.iterations = iterations;
    }

    public BlastStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(BlastStatistics statistics) {
        this.statistics = statistics;
    }
}