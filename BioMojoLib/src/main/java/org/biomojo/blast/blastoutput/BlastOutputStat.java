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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// TODO: Auto-generated Javadoc
/**
 * The Class BlastOutputStat.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "statistics" })
@XmlRootElement(name = "BlastOutput_mbstat")
public class BlastOutputStat {

    /** The statistics. */
    @XmlElement(name = "Statistics", required = true)
    protected BlastStatistics statistics;

    /**
     * Gets the value of the statistics property.
     * 
     * @return possible object is {@link BlastStatistics }
     * 
     */
    public BlastStatistics getStatistics() {
        return statistics;
    }

    /**
     * Sets the value of the statistics property.
     * 
     * @param value
     *            allowed object is {@link BlastStatistics }
     * 
     */
    public void setStatistics(BlastStatistics value) {
        this.statistics = value;
    }

}
