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
package org.biomojo.alphabet;

import org.biomojo.symbols.Nucleotides;

// TODO: Auto-generated Javadoc
/**
 * The Class RNAAlphabetImpl.
 *
 * @author Hugh Eaves
 */
public class BasicRNA extends AbstractNucleotide<RNA> implements RNA {

    /**
     * Create a new DNAAlphabetImpl.
     *
     * @param id
     *            the id
     */
    protected BasicRNA(final long id) {
        super(id, Nucleotides.RNA_SYMBOLS);
    }
}
