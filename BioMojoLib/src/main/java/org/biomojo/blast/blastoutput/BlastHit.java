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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "hitNum", "hitId", "hitDef", "hitAccession", "hitLen", "hsps" })
@XmlRootElement(name = "Hit")
@Entity
public class BlastHit {
    @TableGenerator(name = "BlastHitGenerator", allocationSize = 1000)
    @Id
    @GeneratedValue(generator = "BlastHitGenerator")
    @XmlTransient
    private long id;

    @XmlElement(name = "Hit_num", required = true)
    protected int hitNum;
    @XmlElement(name = "Hit_id", required = true)
    protected String hitId;
    @XmlElement(name = "Hit_def", required = true)
    protected String hitDef;
    @XmlElement(name = "Hit_accession", required = true)
    protected String hitAccession;
    @XmlElement(name = "Hit_len", required = true)
    protected int hitLen;

    @XmlElement(name = "Hsp")
    @XmlElementWrapper(name = "Hit_hsps")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BlastHit_id")
    protected List<BlastHsp> hsps;

    @XmlTransient
    @OneToOne
    protected ByteSeqImpl<ByteAlphabet> hitSequence;

    @XmlTransient
    @ManyToOne
    protected BlastIteration blastIteration;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public int getHitNum() {
        return hitNum;
    }

    public void setHitNum(final int hitNum) {
        this.hitNum = hitNum;
    }

    public String getHitId() {
        return hitId;
    }

    public void setHitId(final String hitId) {
        this.hitId = hitId;
    }

    public String getHitDef() {
        return hitDef;
    }

    public void setHitDef(final String hitDef) {
        this.hitDef = hitDef;
    }

    public String getHitAccession() {
        return hitAccession;
    }

    public void setHitAccession(final String hitAccession) {
        this.hitAccession = hitAccession;
    }

    public int getHitLen() {
        return hitLen;
    }

    public void setHitLen(final int len) {
        this.hitLen = len;
    }

    public List<BlastHsp> getHsps() {
        return hsps;
    }

    public void setHsps(final List<BlastHsp> hsps) {
        this.hsps = hsps;
    }

    public BlastIteration getIteration() {
        return blastIteration;
    }

    public void setIteration(final BlastIteration blastIteration) {
        this.blastIteration = blastIteration;
    }

    public ByteSeqImpl<ByteAlphabet> getHitSequence() {
        return hitSequence;
    }

    public void setHitSequence(final ByteSeqImpl<ByteAlphabet> hitSequence) {
        this.hitSequence = hitSequence;
    }

    public BlastIteration getBlastIteration() {
        return blastIteration;
    }

    public void setBlastIteration(final BlastIteration blastIteration) {
        this.blastIteration = blastIteration;
    }

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
