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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "matrix", "expect", "include", "matchScore", "mismatchScore", "gapOpen", "gapExtend",
        "filter", "pattern", "entrezQuery" })
@XmlRootElement(name = "Parameters")
@Entity
public class BlastParameters {
    @TableGenerator(name = "BlastParametersGenerator", allocationSize = 1000)
    @Id
    @GeneratedValue(generator = "BlastParametersGenerator")
    @XmlTransient
    private long id;

    @XmlElement(name = "Parameters_matrix")
    protected String matrix;
    @XmlElement(name = "Parameters_expect", required = true)
    protected double expect;
    @XmlElement(name = "Parameters_include")
    protected double include;
    @XmlElement(name = "Parameters_sc-match")
    protected int matchScore;
    @XmlElement(name = "Parameters_sc-mismatch")
    protected int mismatchScore;
    @XmlElement(name = "Parameters_gap-open", required = true)
    protected int gapOpen;
    @XmlElement(name = "Parameters_gap-extend", required = true)
    protected int gapExtend;
    @XmlElement(name = "Parameters_filter")
    protected String filter;
    @XmlElement(name = "Parameters_pattern")
    protected String pattern;
    @XmlElement(name = "Parameters_entrez-query")
    protected String entrezQuery;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMatrix() {
        return matrix;
    }

    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    public double getExpect() {
        return expect;
    }

    public void setExpect(double expect) {
        this.expect = expect;
    }

    public double getInclude() {
        return include;
    }

    public void setInclude(double include) {
        this.include = include;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public int getMismatchScore() {
        return mismatchScore;
    }

    public void setMismatchScore(int mismatchScore) {
        this.mismatchScore = mismatchScore;
    }

    public int getGapOpen() {
        return gapOpen;
    }

    public void setGapOpen(int gapOpen) {
        this.gapOpen = gapOpen;
    }

    public int getGapExtend() {
        return gapExtend;
    }

    public void setGapExtend(int gapExtend) {
        this.gapExtend = gapExtend;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getEntrezQuery() {
        return entrezQuery;
    }

    public void setEntrezQuery(String entrezQuery) {
        this.entrezQuery = entrezQuery;
    }

}
