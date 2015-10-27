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

public class BlastOutputStatAdapter extends XmlAdapter<BlastOutputStat, BlastStatistics> {

    @Override
    public BlastStatistics unmarshal(BlastOutputStat v) throws Exception {
        return v.getStatistics();
    }

    @Override
    public BlastOutputStat marshal(BlastStatistics v) throws Exception {
        if (v != null) {
            BlastOutputStat p = new BlastOutputStat();
            p.setStatistics(v);
            return p;
        } else {
            return null;
        }
    }
}
