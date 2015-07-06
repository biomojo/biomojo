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

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class BlastObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of
     * schema derived classes for package: org.jbiocore.blast.xml.blastoutput
     * 
     */
    public BlastObjectFactory() {
    }

    /**
     * Create an instance of {@link BlastOutput }
     * 
     */
    public BlastOutput createBlastOutput() {
        return new BlastOutput();
    }

    /**
     * Create an instance of {@link BlastHsp }
     * 
     */
    public BlastHsp createHsp() {
        return new BlastHsp();
    }

    /**
     * Create an instance of {@link BlastIteration }
     * 
     */
    public BlastIteration createIteration() {
        return new BlastIteration();
    }

    /**
     * Create an instance of {@link BlastHit }
     * 
     */
    public BlastHit createHit() {
        return new BlastHit();
    }

    /**
     * Create an instance of {@link BlastIterationStat }
     * 
     */
    public BlastIterationStat createIterationStat() {
        return new BlastIterationStat();
    }

    /**
     * Create an instance of {@link BlastParameters }
     * 
     */
    public BlastParameters createParameters() {
        return new BlastParameters();
    }

    /**
     * Create an instance of {@link BlastStatistics }
     * 
     */
    public BlastStatistics createStatistics() {
        return new BlastStatistics();
    }
}
