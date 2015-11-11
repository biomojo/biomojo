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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class BlastHsp.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "hspNum", "bitScore", "score", "evalue", "queryFrom", "queryTo", "hitFrom", "hitTo",
        "patternFrom", "patternTo", "queryFrame", "hitFrame", "identity", "positive", "gaps", "alignLen", "density",
        "qseq", "hseq", "midline" })
@XmlRootElement(name = "Hsp")
@Entity
public class BlastHsp {
    
    /** The id. */
    @TableGenerator(name = "BlastHspGenerator", allocationSize = 1000)
    @Id
    @GeneratedValue(generator = "BlastHspGenerator")
    @XmlTransient
    private long id;

    /** The hsp num. */
    @XmlElement(name = "Hsp_num", required = true)
    protected int hspNum;
    
    /** The bit score. */
    @XmlElement(name = "Hsp_bit-score", required = true)
    protected double bitScore;
    
    /** The score. */
    @XmlElement(name = "Hsp_score", required = true)
    protected double score;
    
    /** The evalue. */
    @XmlElement(name = "Hsp_evalue", required = true)
    protected double evalue;
    
    /** The query from. */
    @XmlElement(name = "Hsp_query-from", required = true)
    protected int queryFrom;
    
    /** The query to. */
    @XmlElement(name = "Hsp_query-to", required = true)
    protected int queryTo;
    
    /** The hit from. */
    @XmlElement(name = "Hsp_hit-from", required = true)
    protected int hitFrom;
    
    /** The hit to. */
    @XmlElement(name = "Hsp_hit-to", required = true)
    protected int hitTo;
    
    /** The pattern from. */
    @XmlElement(name = "Hsp_pattern-from")
    protected int patternFrom;
    
    /** The pattern to. */
    @XmlElement(name = "Hsp_pattern-to")
    protected int patternTo;
    
    /** The query frame. */
    @XmlElement(name = "Hsp_query-frame")
    protected int queryFrame;
    
    /** The hit frame. */
    @XmlElement(name = "Hsp_hit-frame")
    protected int hitFrame;
    
    /** The identity. */
    @XmlElement(name = "Hsp_identity")
    protected int identity;
    
    /** The positive. */
    @XmlElement(name = "Hsp_positive")
    protected int positive;
    
    /** The gaps. */
    @XmlElement(name = "Hsp_gaps")
    protected int gaps;
    
    /** The align len. */
    @XmlElement(name = "Hsp_align-len")
    protected int alignLen;
    
    /** The density. */
    @XmlElement(name = "Hsp_density")
    protected int density;

    /** The qseq. */
    @XmlElement(name = "Hsp_qseq", required = true)
    @XmlJavaTypeAdapter(StringToByteArrayAdapter.class)
    @Column(length = Integer.MAX_VALUE)
    protected byte[] qseq;

    /** The hseq. */
    @XmlElement(name = "Hsp_hseq", required = true)
    @XmlJavaTypeAdapter(StringToByteArrayAdapter.class)
    @Column(length = Integer.MAX_VALUE)
    protected byte[] hseq;

    /** The midline. */
    @XmlElement(name = "Hsp_midline")
    @XmlJavaTypeAdapter(StringToByteArrayAdapter.class)
    @Column(length = Integer.MAX_VALUE)
    protected byte[] midline;

    /** The blast hit. */
    @XmlTransient
    @ManyToOne
    protected BlastHit blastHit;

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
     * Gets the hsp num.
     *
     * @return the hsp num
     */
    public int getHspNum() {
        return hspNum;
    }

    /**
     * Sets the hsp num.
     *
     * @param hspNum the new hsp num
     */
    public void setHspNum(int hspNum) {
        this.hspNum = hspNum;
    }

    /**
     * Gets the bit score.
     *
     * @return the bit score
     */
    public double getBitScore() {
        return bitScore;
    }

    /**
     * Sets the bit score.
     *
     * @param hspBitScore the new bit score
     */
    public void setBitScore(double hspBitScore) {
        this.bitScore = hspBitScore;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param hspScore the new score
     */
    public void setScore(double hspScore) {
        this.score = hspScore;
    }

    /**
     * Gets the evalue.
     *
     * @return the evalue
     */
    public double getEvalue() {
        return evalue;
    }

    /**
     * Sets the evalue.
     *
     * @param hspEvalue the new evalue
     */
    public void setEvalue(double hspEvalue) {
        this.evalue = hspEvalue;
    }

    /**
     * Gets the query from.
     *
     * @return the query from
     */
    public int getQueryFrom() {
        return queryFrom;
    }

    /**
     * Sets the query from.
     *
     * @param hspQueryFrom the new query from
     */
    public void setQueryFrom(int hspQueryFrom) {
        this.queryFrom = hspQueryFrom;
    }

    /**
     * Gets the query to.
     *
     * @return the query to
     */
    public int getQueryTo() {
        return queryTo;
    }

    /**
     * Sets the query to.
     *
     * @param hspQueryTo the new query to
     */
    public void setQueryTo(int hspQueryTo) {
        this.queryTo = hspQueryTo;
    }

    /**
     * Gets the hit from.
     *
     * @return the hit from
     */
    public int getHitFrom() {
        return hitFrom;
    }

    /**
     * Sets the hit from.
     *
     * @param hspHitFrom the new hit from
     */
    public void setHitFrom(int hspHitFrom) {
        this.hitFrom = hspHitFrom;
    }

    /**
     * Gets the hit to.
     *
     * @return the hit to
     */
    public int getHitTo() {
        return hitTo;
    }

    /**
     * Sets the hit to.
     *
     * @param hspHitTo the new hit to
     */
    public void setHitTo(int hspHitTo) {
        this.hitTo = hspHitTo;
    }

    /**
     * Gets the pattern from.
     *
     * @return the pattern from
     */
    public int getPatternFrom() {
        return patternFrom;
    }

    /**
     * Sets the pattern from.
     *
     * @param hspPatternFrom the new pattern from
     */
    public void setPatternFrom(int hspPatternFrom) {
        this.patternFrom = hspPatternFrom;
    }

    /**
     * Gets the pattern to.
     *
     * @return the pattern to
     */
    public int getPatternTo() {
        return patternTo;
    }

    /**
     * Sets the pattern to.
     *
     * @param hspPatternTo the new pattern to
     */
    public void setPatternTo(int hspPatternTo) {
        this.patternTo = hspPatternTo;
    }

    /**
     * Gets the query frame.
     *
     * @return the query frame
     */
    public int getQueryFrame() {
        return queryFrame;
    }

    /**
     * Sets the query frame.
     *
     * @param hspQueryFrame the new query frame
     */
    public void setQueryFrame(int hspQueryFrame) {
        this.queryFrame = hspQueryFrame;
    }

    /**
     * Gets the hit frame.
     *
     * @return the hit frame
     */
    public int getHitFrame() {
        return hitFrame;
    }

    /**
     * Sets the hit frame.
     *
     * @param hspHitFrame the new hit frame
     */
    public void setHitFrame(int hspHitFrame) {
        this.hitFrame = hspHitFrame;
    }

    /**
     * Gets the identity.
     *
     * @return the identity
     */
    public int getIdentity() {
        return identity;
    }

    /**
     * Sets the identity.
     *
     * @param hspIdentity the new identity
     */
    public void setIdentity(int hspIdentity) {
        this.identity = hspIdentity;
    }

    /**
     * Gets the positive.
     *
     * @return the positive
     */
    public int getPositive() {
        return positive;
    }

    /**
     * Sets the positive.
     *
     * @param hspPositive the new positive
     */
    public void setPositive(int hspPositive) {
        this.positive = hspPositive;
    }

    /**
     * Gets the gaps.
     *
     * @return the gaps
     */
    public int getGaps() {
        return gaps;
    }

    /**
     * Sets the gaps.
     *
     * @param hspGaps the new gaps
     */
    public void setGaps(int hspGaps) {
        this.gaps = hspGaps;
    }

    /**
     * Gets the align len.
     *
     * @return the align len
     */
    public int getAlignLen() {
        return alignLen;
    }

    /**
     * Sets the align len.
     *
     * @param hspAlignLen the new align len
     */
    public void setAlignLen(int hspAlignLen) {
        this.alignLen = hspAlignLen;
    }

    /**
     * Gets the density.
     *
     * @return the density
     */
    public int getDensity() {
        return density;
    }

    /**
     * Sets the density.
     *
     * @param hspDensity the new density
     */
    public void setDensity(int hspDensity) {
        this.density = hspDensity;
    }

    /**
     * Gets the qseq.
     *
     * @return the qseq
     */
    public byte[] getQseq() {
        return qseq;
    }

    /**
     * Sets the qseq.
     *
     * @param hspQseq the new qseq
     */
    public void setQseq(byte[] hspQseq) {
        this.qseq = hspQseq;
    }

    /**
     * Gets the hseq.
     *
     * @return the hseq
     */
    public byte[] getHseq() {
        return hseq;
    }

    /**
     * Sets the hseq.
     *
     * @param hspHseq the new hseq
     */
    public void setHseq(byte[] hspHseq) {
        this.hseq = hspHseq;
    }

    /**
     * Gets the midline.
     *
     * @return the midline
     */
    public byte[] getMidline() {
        return midline;
    }

    /**
     * Sets the midline.
     *
     * @param hspMidline the new midline
     */
    public void setMidline(byte[] hspMidline) {
        this.midline = hspMidline;
    }

    /**
     * Gets the hit.
     *
     * @return the hit
     */
    public BlastHit getHit() {
        return blastHit;
    }

    /**
     * Sets the hit.
     *
     * @param blastHit the new hit
     */
    public void setHit(BlastHit blastHit) {
        this.blastHit = blastHit;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("Blast HSP:\n");

        buf.append("id: " + id + "\n");
        buf.append("hspNum: " + hspNum + "\n");
        buf.append("hspBitScore: " + bitScore + "\n");
        buf.append("hspScore: " + score + "\n");
        buf.append("hspEvalue: " + evalue + "\n");
        buf.append("hspQueryFrom: " + queryFrom + "\n");
        buf.append("hspQueryTo: " + queryTo + "\n");
        buf.append("hspHitFrom: " + hitFrom + "\n");
        buf.append("hspHitTo: " + hitTo + "\n");
        buf.append("hspPatternFrom: " + patternFrom + "\n");
        buf.append("hspPatternTo: " + patternTo + "\n");
        buf.append("hspQueryFrame: " + queryFrame + "\n");
        buf.append("hspHitFrame: " + hitFrame + "\n");
        buf.append("hspIdentity: " + identity + "\n");
        buf.append("hspPositive: " + positive + "\n");
        buf.append("hspHitTo: " + hitTo + "\n");
        buf.append("hspGaps: " + gaps + "\n");
        buf.append("hspAlignLen: " + alignLen + "\n");
        buf.append("hspDensity: " + density + "\n");
        buf.append("hspQseq:    [" + new String(qseq) + "]\n");
        buf.append("hspMidline: [" + new String(midline) + "]\n");
        buf.append("hspHseq:    [" + new String(hseq) + "]\n");

        return buf.toString();
    }

}
