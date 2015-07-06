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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "hspNum", "bitScore", "score", "evalue",
        "queryFrom", "queryTo", "hitFrom", "hitTo", "patternFrom", "patternTo",
        "queryFrame", "hitFrame", "identity", "positive", "gaps", "alignLen",
        "density", "qseq", "hseq", "midline" })
@XmlRootElement(name = "Hsp")
@Entity
public class BlastHsp {
    @TableGenerator(name = "BlastHspGenerator", allocationSize = 1000)
    @Id
    @GeneratedValue(generator = "BlastHspGenerator")
    @XmlTransient
    private long id;

    @XmlElement(name = "Hsp_num", required = true)
    protected int hspNum;
    @XmlElement(name = "Hsp_bit-score", required = true)
    protected double bitScore;
    @XmlElement(name = "Hsp_score", required = true)
    protected double score;
    @XmlElement(name = "Hsp_evalue", required = true)
    protected double evalue;
    @XmlElement(name = "Hsp_query-from", required = true)
    protected int queryFrom;
    @XmlElement(name = "Hsp_query-to", required = true)
    protected int queryTo;
    @XmlElement(name = "Hsp_hit-from", required = true)
    protected int hitFrom;
    @XmlElement(name = "Hsp_hit-to", required = true)
    protected int hitTo;
    @XmlElement(name = "Hsp_pattern-from")
    protected int patternFrom;
    @XmlElement(name = "Hsp_pattern-to")
    protected int patternTo;
    @XmlElement(name = "Hsp_query-frame")
    protected int queryFrame;
    @XmlElement(name = "Hsp_hit-frame")
    protected int hitFrame;
    @XmlElement(name = "Hsp_identity")
    protected int identity;
    @XmlElement(name = "Hsp_positive")
    protected int positive;
    @XmlElement(name = "Hsp_gaps")
    protected int gaps;
    @XmlElement(name = "Hsp_align-len")
    protected int alignLen;
    @XmlElement(name = "Hsp_density")
    protected int density;

    @XmlElement(name = "Hsp_qseq", required = true)
    @XmlJavaTypeAdapter(StringToByteArrayAdapter.class)
    @Column(length = Integer.MAX_VALUE)
    protected byte[] qseq;

    @XmlElement(name = "Hsp_hseq", required = true)
    @XmlJavaTypeAdapter(StringToByteArrayAdapter.class)
    @Column(length = Integer.MAX_VALUE)
    protected byte[] hseq;

    @XmlElement(name = "Hsp_midline")
    @XmlJavaTypeAdapter(StringToByteArrayAdapter.class)
    @Column(length = Integer.MAX_VALUE)
    protected byte[] midline;

    @XmlTransient
    @ManyToOne
    protected BlastHit blastHit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHspNum() {
        return hspNum;
    }

    public void setHspNum(int hspNum) {
        this.hspNum = hspNum;
    }

    public double getBitScore() {
        return bitScore;
    }

    public void setBitScore(double hspBitScore) {
        this.bitScore = hspBitScore;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double hspScore) {
        this.score = hspScore;
    }

    public double getEvalue() {
        return evalue;
    }

    public void setEvalue(double hspEvalue) {
        this.evalue = hspEvalue;
    }

    public int getQueryFrom() {
        return queryFrom;
    }

    public void setQueryFrom(int hspQueryFrom) {
        this.queryFrom = hspQueryFrom;
    }

    public int getQueryTo() {
        return queryTo;
    }

    public void setQueryTo(int hspQueryTo) {
        this.queryTo = hspQueryTo;
    }

    public int getHitFrom() {
        return hitFrom;
    }

    public void setHitFrom(int hspHitFrom) {
        this.hitFrom = hspHitFrom;
    }

    public int getHitTo() {
        return hitTo;
    }

    public void setHitTo(int hspHitTo) {
        this.hitTo = hspHitTo;
    }

    public int getPatternFrom() {
        return patternFrom;
    }

    public void setPatternFrom(int hspPatternFrom) {
        this.patternFrom = hspPatternFrom;
    }

    public int getPatternTo() {
        return patternTo;
    }

    public void setPatternTo(int hspPatternTo) {
        this.patternTo = hspPatternTo;
    }

    public int getQueryFrame() {
        return queryFrame;
    }

    public void setQueryFrame(int hspQueryFrame) {
        this.queryFrame = hspQueryFrame;
    }

    public int getHitFrame() {
        return hitFrame;
    }

    public void setHitFrame(int hspHitFrame) {
        this.hitFrame = hspHitFrame;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int hspIdentity) {
        this.identity = hspIdentity;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int hspPositive) {
        this.positive = hspPositive;
    }

    public int getGaps() {
        return gaps;
    }

    public void setGaps(int hspGaps) {
        this.gaps = hspGaps;
    }

    public int getAlignLen() {
        return alignLen;
    }

    public void setAlignLen(int hspAlignLen) {
        this.alignLen = hspAlignLen;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int hspDensity) {
        this.density = hspDensity;
    }

    public byte[] getQseq() {
        return qseq;
    }

    public void setQseq(byte[] hspQseq) {
        this.qseq = hspQseq;
    }

    public byte[] getHseq() {
        return hseq;
    }

    public void setHseq(byte[] hspHseq) {
        this.hseq = hspHseq;
    }

    public byte[] getMidline() {
        return midline;
    }

    public void setMidline(byte[] hspMidline) {
        this.midline = hspMidline;
    }

    public BlastHit getHit() {
        return blastHit;
    }

    public void setHit(BlastHit blastHit) {
        this.blastHit = blastHit;
    }

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
