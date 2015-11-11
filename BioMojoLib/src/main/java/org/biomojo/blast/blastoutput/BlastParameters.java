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

// TODO: Auto-generated Javadoc
/**
 * The Class BlastParameters.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "matrix", "expect", "include", "matchScore", "mismatchScore", "gapOpen", "gapExtend",
        "filter", "pattern", "entrezQuery" })
@XmlRootElement(name = "Parameters")
@Entity
public class BlastParameters {
    
    /** The id. */
    @TableGenerator(name = "BlastParametersGenerator", allocationSize = 1000)
    @Id
    @GeneratedValue(generator = "BlastParametersGenerator")
    @XmlTransient
    private long id;

    /** The matrix. */
    @XmlElement(name = "Parameters_matrix")
    protected String matrix;
    
    /** The expect. */
    @XmlElement(name = "Parameters_expect", required = true)
    protected double expect;
    
    /** The include. */
    @XmlElement(name = "Parameters_include")
    protected double include;
    
    /** The match score. */
    @XmlElement(name = "Parameters_sc-match")
    protected int matchScore;
    
    /** The mismatch score. */
    @XmlElement(name = "Parameters_sc-mismatch")
    protected int mismatchScore;
    
    /** The gap open. */
    @XmlElement(name = "Parameters_gap-open", required = true)
    protected int gapOpen;
    
    /** The gap extend. */
    @XmlElement(name = "Parameters_gap-extend", required = true)
    protected int gapExtend;
    
    /** The filter. */
    @XmlElement(name = "Parameters_filter")
    protected String filter;
    
    /** The pattern. */
    @XmlElement(name = "Parameters_pattern")
    protected String pattern;
    
    /** The entrez query. */
    @XmlElement(name = "Parameters_entrez-query")
    protected String entrezQuery;

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
     * Gets the matrix.
     *
     * @return the matrix
     */
    public String getMatrix() {
        return matrix;
    }

    /**
     * Sets the matrix.
     *
     * @param matrix the new matrix
     */
    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    /**
     * Gets the expect.
     *
     * @return the expect
     */
    public double getExpect() {
        return expect;
    }

    /**
     * Sets the expect.
     *
     * @param expect the new expect
     */
    public void setExpect(double expect) {
        this.expect = expect;
    }

    /**
     * Gets the include.
     *
     * @return the include
     */
    public double getInclude() {
        return include;
    }

    /**
     * Sets the include.
     *
     * @param include the new include
     */
    public void setInclude(double include) {
        this.include = include;
    }

    /**
     * Gets the match score.
     *
     * @return the match score
     */
    public int getMatchScore() {
        return matchScore;
    }

    /**
     * Sets the match score.
     *
     * @param matchScore the new match score
     */
    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    /**
     * Gets the mismatch score.
     *
     * @return the mismatch score
     */
    public int getMismatchScore() {
        return mismatchScore;
    }

    /**
     * Sets the mismatch score.
     *
     * @param mismatchScore the new mismatch score
     */
    public void setMismatchScore(int mismatchScore) {
        this.mismatchScore = mismatchScore;
    }

    /**
     * Gets the gap open.
     *
     * @return the gap open
     */
    public int getGapOpen() {
        return gapOpen;
    }

    /**
     * Sets the gap open.
     *
     * @param gapOpen the new gap open
     */
    public void setGapOpen(int gapOpen) {
        this.gapOpen = gapOpen;
    }

    /**
     * Gets the gap extend.
     *
     * @return the gap extend
     */
    public int getGapExtend() {
        return gapExtend;
    }

    /**
     * Sets the gap extend.
     *
     * @param gapExtend the new gap extend
     */
    public void setGapExtend(int gapExtend) {
        this.gapExtend = gapExtend;
    }

    /**
     * Gets the filter.
     *
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Sets the filter.
     *
     * @param filter the new filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Gets the pattern.
     *
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the pattern.
     *
     * @param pattern the new pattern
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Gets the entrez query.
     *
     * @return the entrez query
     */
    public String getEntrezQuery() {
        return entrezQuery;
    }

    /**
     * Sets the entrez query.
     *
     * @param entrezQuery the new entrez query
     */
    public void setEntrezQuery(String entrezQuery) {
        this.entrezQuery = entrezQuery;
    }

}
