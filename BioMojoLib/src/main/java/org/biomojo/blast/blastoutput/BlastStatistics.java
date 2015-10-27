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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.biomojo.core.AbstractEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class BlastStatistics.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "dbNum", "dbLen", "hspLen", "effSpace", "kappa", "lambda", "entropy" })
@XmlRootElement(name = "Statistics")
@Entity
public class BlastStatistics extends AbstractEntity {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The db num. */
    @XmlElement(name = "Statistics_db-num", required = true)
    protected int dbNum;
    
    /** The db len. */
    @XmlElement(name = "Statistics_db-len", required = true)
    protected long dbLen;
    
    /** The hsp len. */
    @XmlElement(name = "Statistics_hsp-len", required = true)
    protected int hspLen;
    
    /** The eff space. */
    @XmlElement(name = "Statistics_eff-space", required = true)
    protected double effSpace;
    
    /** The kappa. */
    @XmlElement(name = "Statistics_kappa", required = true)
    protected double kappa;
    
    /** The lambda. */
    @XmlElement(name = "Statistics_lambda", required = true)
    protected double lambda;
    
    /** The entropy. */
    @XmlElement(name = "Statistics_entropy", required = true)
    protected double entropy;

    /* (non-Javadoc)
     * @see org.biomojo.core.AbstractEntity#getId()
     */
    @Override
    public long getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see org.biomojo.core.AbstractEntity#setId(long)
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the db num.
     *
     * @return the db num
     */
    public int getDbNum() {
        return dbNum;
    }

    /**
     * Sets the db num.
     *
     * @param dbNum the new db num
     */
    public void setDbNum(int dbNum) {
        this.dbNum = dbNum;
    }

    /**
     * Gets the db len.
     *
     * @return the db len
     */
    public long getDbLen() {
        return dbLen;
    }

    /**
     * Sets the db len.
     *
     * @param dbLen the new db len
     */
    public void setDbLen(long dbLen) {
        this.dbLen = dbLen;
    }

    /**
     * Gets the hsp len.
     *
     * @return the hsp len
     */
    public int getHspLen() {
        return hspLen;
    }

    /**
     * Sets the hsp len.
     *
     * @param hspLen the new hsp len
     */
    public void setHspLen(int hspLen) {
        this.hspLen = hspLen;
    }

    /**
     * Gets the eff space.
     *
     * @return the eff space
     */
    public double getEffSpace() {
        return effSpace;
    }

    /**
     * Sets the eff space.
     *
     * @param effSpace the new eff space
     */
    public void setEffSpace(double effSpace) {
        this.effSpace = effSpace;
    }

    /**
     * Gets the kappa.
     *
     * @return the kappa
     */
    public double getKappa() {
        return kappa;
    }

    /**
     * Sets the kappa.
     *
     * @param kappa the new kappa
     */
    public void setKappa(double kappa) {
        this.kappa = kappa;
    }

    /**
     * Gets the lambda.
     *
     * @return the lambda
     */
    public double getLambda() {
        return lambda;
    }

    /**
     * Sets the lambda.
     *
     * @param lambda the new lambda
     */
    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    /**
     * Gets the entropy.
     *
     * @return the entropy
     */
    public double getEntropy() {
        return entropy;
    }

    /**
     * Sets the entropy.
     *
     * @param entropy the new entropy
     */
    public void setEntropy(double entropy) {
        this.entropy = entropy;
    }

}
