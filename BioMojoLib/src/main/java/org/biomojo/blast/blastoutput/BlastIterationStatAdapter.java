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

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BlastIterationStatAdapter extends XmlAdapter<BlastIterationStat, BlastStatistics> {

    @Override
    public BlastStatistics unmarshal(BlastIterationStat v) throws Exception {
        return v.getStatistics();
    }

    @Override
    public BlastIterationStat marshal(BlastStatistics v) throws Exception {
        if (v != null) {
            BlastIterationStat p = new BlastIterationStat();
            p.setStatistics(v);
            return p;
        } else {
            return null;
        }
    }
}
