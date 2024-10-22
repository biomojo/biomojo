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
package org.biomojo.codon;

import org.biomojo.GlobalConst;

// TODO: Auto-generated Javadoc
/**
 * The Class CodonTableId.
 *
 * @author Hugh Eaves
 * 
 *         Unique id's for codon tables. Numeric values are from NCBI standard.
 * 
 *         More information available here:
 * 
 *         http://www.ncbi.nlm.nih.gov/Taxonomy/Utils/wprlonggc.cgi
 */
public class CodonTableId {

    /**
     * Instantiates a new codon table id.
     */
    private CodonTableId() {

    }

    public static final long START = GlobalConst.GROUP_ID * 3L;

    /** The Constant UNKNOWN. */
    public static final long UNKNOWN = START + 0;

    /** The Constant STANDARD. */
    public static final long STANDARD = START + 1;

    /** The Constant VERTEBRATE_MITOCHONDRIAL. */
    public static final long VERTEBRATE_MITOCHONDRIAL = START + 2;

    /** The Constant YEAST_MITOCHONDRIAL. */
    public static final long YEAST_MITOCHONDRIAL = START + 3;

    /** The Constant MOLD_MITOCHONDRIAL. */
    public static final long MOLD_MITOCHONDRIAL = START + 4;

    /** The Constant INVERTEBRATE_MITOCHONDRIAL. */
    public static final long INVERTEBRATE_MITOCHONDRIAL = START + 5;

    /** The Constant CILIATE_NUCLEAR. */
    public static final long CILIATE_NUCLEAR = START + 6;

    /** The Constant ECHINODERM_MITOCHONDRIAL. */
    public static final long ECHINODERM_MITOCHONDRIAL = START + 9;

    /** The Constant EUPLOTID_NUCLEAR. */
    public static final long EUPLOTID_NUCLEAR = START + 10;

    /** The Constant BACTERIAL_PLASTID. */
    public static final long BACTERIAL_PLASTID = START + 11;

    /** The Constant ALTERNATIVE_YEAST_NUCLEAR. */
    public static final long ALTERNATIVE_YEAST_NUCLEAR = START + 12;

    /** The Constant ASCIDIAN_MITOCHONDRIAL. */
    public static final long ASCIDIAN_MITOCHONDRIAL = START + 13;

    /** The Constant ALTERNATIVE_FLATWORM_MITOCHONDRIAL. */
    public static final long ALTERNATIVE_FLATWORM_MITOCHONDRIAL = START + 14;

    /** The Constant BLEPHARISMA_MACRONUCLEAR. */
    public static final long BLEPHARISMA_MACRONUCLEAR = START + 15;

    /** The Constant CHLOROPHYCEAN_MITOCHONDRIAL. */
    public static final long CHLOROPHYCEAN_MITOCHONDRIAL = START + 16;

    /** The Constant TREMATODE_MITOCHONDRIAL. */
    public static final long TREMATODE_MITOCHONDRIAL = START + 21;

    /** The Constant SCENEDESMUS_OBLIQUUS_MITOCHONDRIAL. */
    public static final long SCENEDESMUS_OBLIQUUS_MITOCHONDRIAL = START + 22;

    /** The Constant THRAUSTOCHYTRIUM_MITOCHONDRIAL. */
    public static final long THRAUSTOCHYTRIUM_MITOCHONDRIAL = START + 23;

    /** The Constant PTEROBRANCHIA_MITOCHONDRIAL. */
    public static final long PTEROBRANCHIA_MITOCHONDRIAL = START + 24;

    /** The Constant CGRACILIBACTERIA. */
    public static final long CGRACILIBACTERIA = START + 25;
}
