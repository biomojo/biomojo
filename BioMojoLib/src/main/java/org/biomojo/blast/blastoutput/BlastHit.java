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

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.sequence.ByteSeqImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class BlastHit.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "hitNum", "hitId", "hitDef", "hitAccession", "hitLen", "hsps" })
@XmlRootElement(name = "Hit")
@Entity
public class BlastHit {
    
    /** The id. */
    @TableGenerator(name = "BlastHitGenerator", allocationSize = 1000)
    @Id
    @GeneratedValue(generator = "BlastHitGenerator")
    @XmlTransient
    private long id;

    /** The hit num. */
    @XmlElement(name = "Hit_num", required = true)
    protected int hitNum;
    
    /** The hit id. */
    @XmlElement(name = "Hit_id", required = true)
    protected String hitId;
    
    /** The hit def. */
    @XmlElement(name = "Hit_def", required = true)
    protected String hitDef;
    
    /** The hit accession. */
    @XmlElement(name = "Hit_accession", required = true)
    protected String hitAccession;
    
    /** The hit len. */
    @XmlElement(name = "Hit_len", required = true)
    protected int hitLen;

    /** The hsps. */
    @XmlElement(name = "Hsp")
    @XmlElementWrapper(name = "Hit_hsps")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BlastHit_id")
    protected List<BlastHsp> hsps;

    /** The hit sequence. */
    @XmlTransient
    @OneToOne
    protected ByteSeqImpl<ByteAlphabet> hitSequence;

    /** The blast iteration. */
    @XmlTransient
    @ManyToOne
    protected BlastIteration blastIteration;

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
     * Gets the hit num.
     *
     * @return the hit num
     */
    public int getHitNum() {
        return hitNum;
    }

    /**
     * Sets the hit num.
     *
     * @param hitNum the new hit num
     */
    public void setHitNum(final int hitNum) {
        this.hitNum = hitNum;
    }

    /**
     * Gets the hit id.
     *
     * @return the hit id
     */
    public String getHitId() {
        return hitId;
    }

    /**
     * Sets the hit id.
     *
     * @param hitId the new hit id
     */
    public void setHitId(final String hitId) {
        this.hitId = hitId;
    }

    /**
     * Gets the hit def.
     *
     * @return the hit def
     */
    public String getHitDef() {
        return hitDef;
    }

    /**
     * Sets the hit def.
     *
     * @param hitDef the new hit def
     */
    public void setHitDef(final String hitDef) {
        this.hitDef = hitDef;
    }

    /**
     * Gets the hit accession.
     *
     * @return the hit accession
     */
    public String getHitAccession() {
        return hitAccession;
    }

    /**
     * Sets the hit accession.
     *
     * @param hitAccession the new hit accession
     */
    public void setHitAccession(final String hitAccession) {
        this.hitAccession = hitAccession;
    }

    /**
     * Gets the hit len.
     *
     * @return the hit len
     */
    public int getHitLen() {
        return hitLen;
    }

    /**
     * Sets the hit len.
     *
     * @param len the new hit len
     */
    public void setHitLen(final int len) {
        this.hitLen = len;
    }

    /**
     * Gets the hsps.
     *
     * @return the hsps
     */
    public List<BlastHsp> getHsps() {
        return hsps;
    }

    /**
     * Sets the hsps.
     *
     * @param hsps the new hsps
     */
    public void setHsps(final List<BlastHsp> hsps) {
        this.hsps = hsps;
    }

    /**
     * Gets the iteration.
     *
     * @return the iteration
     */
    public BlastIteration getIteration() {
        return blastIteration;
    }

    /**
     * Sets the iteration.
     *
     * @param blastIteration the new iteration
     */
    public void setIteration(final BlastIteration blastIteration) {
        this.blastIteration = blastIteration;
    }

    /**
     * Gets the hit sequence.
     *
     * @return the hit sequence
     */
    public ByteSeqImpl<ByteAlphabet> getHitSequence() {
        return hitSequence;
    }

    /**
     * Sets the hit sequence.
     *
     * @param hitSequence the new hit sequence
     */
    public void setHitSequence(final ByteSeqImpl<ByteAlphabet> hitSequence) {
        this.hitSequence = hitSequence;
    }

    /**
     * Gets the blast iteration.
     *
     * @return the blast iteration
     */
    public BlastIteration getBlastIteration() {
        return blastIteration;
    }

    /**
     * Sets the blast iteration.
     *
     * @param blastIteration the new blast iteration
     */
    public void setBlastIteration(final BlastIteration blastIteration) {
        this.blastIteration = blastIteration;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Blast Hit:\n");

        buf.append("id: " + id + "\n");
        buf.append("hitNum: " + hitNum + "\n");
        buf.append("hitId: " + hitId + "\n");
        buf.append("hitDef: " + hitDef + "\n");
        buf.append("hitAccession: " + hitAccession + "\n");
        buf.append("hitLen: " + hitLen + "\n");

        return buf.toString();
    }

}
