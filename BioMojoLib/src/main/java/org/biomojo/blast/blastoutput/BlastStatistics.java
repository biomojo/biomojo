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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "dbNum", "dbLen", "hspLen", "effSpace",
		"kappa", "lambda", "entropy" })
@XmlRootElement(name = "Statistics")
@Entity
public class BlastStatistics extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Statistics_db-num", required = true)
	protected int dbNum;
	@XmlElement(name = "Statistics_db-len", required = true)
	protected long dbLen;
	@XmlElement(name = "Statistics_hsp-len", required = true)
	protected int hspLen;
	@XmlElement(name = "Statistics_eff-space", required = true)
	protected double effSpace;
	@XmlElement(name = "Statistics_kappa", required = true)
	protected double kappa;
	@XmlElement(name = "Statistics_lambda", required = true)
	protected double lambda;
	@XmlElement(name = "Statistics_entropy", required = true)
	protected double entropy;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public int getDbNum() {
		return dbNum;
	}

	public void setDbNum(int dbNum) {
		this.dbNum = dbNum;
	}

	public long getDbLen() {
		return dbLen;
	}

	public void setDbLen(long dbLen) {
		this.dbLen = dbLen;
	}

	public int getHspLen() {
		return hspLen;
	}

	public void setHspLen(int hspLen) {
		this.hspLen = hspLen;
	}

	public double getEffSpace() {
		return effSpace;
	}

	public void setEffSpace(double effSpace) {
		this.effSpace = effSpace;
	}

	public double getKappa() {
		return kappa;
	}

	public void setKappa(double kappa) {
		this.kappa = kappa;
	}

	public double getLambda() {
		return lambda;
	}

	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

	public double getEntropy() {
		return entropy;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

}
