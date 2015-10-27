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
package org.biomojo.codon;

/**
 * @author Hugh Eaves
 *
 *         Unique id's for codon tables. Numeric values are from NCBI standard.
 *
 *         More information available here:
 *
 *         http://www.ncbi.nlm.nih.gov/Taxonomy/Utils/wprintgc.cgi
 *
 */
public class CodonTableId {
    private CodonTableId() {

    }

    public static final int UNKNOWN = 0;
    public static final int STANDARD = 1;
    public static final int VERTEBRATE_MITOCHONDRIAL = 2;
    public static final int YEAST_MITOCHONDRIAL = 3;
    public static final int MOLD_MITOCHONDRIAL = 4;
    public static final int INVERTEBRATE_MITOCHONDRIAL = 5;
    public static final int CILIATE_NUCLEAR = 6;
    public static final int ECHINODERM_MITOCHONDRIAL = 9;
    public static final int EUPLOTID_NUCLEAR = 10;
    public static final int BACTERIAL_PLASTID = 11;
    public static final int ALTERNATIVE_YEAST_NUCLEAR = 12;
    public static final int ASCIDIAN_MITOCHONDRIAL = 13;
    public static final int ALTERNATIVE_FLATWORM_MITOCHONDRIAL = 14;
    public static final int BLEPHARISMA_MACRONUCLEAR = 15;
    public static final int CHLOROPHYCEAN_MITOCHONDRIAL = 16;
    public static final int TREMATODE_MITOCHONDRIAL = 21;
    public static final int SCENEDESMUS_OBLIQUUS_MITOCHONDRIAL = 22;
    public static final int THRAUSTOCHYTRIUM_MITOCHONDRIAL = 23;
    public static final int PTEROBRANCHIA_MITOCHONDRIAL = 24;
    public static final int CGRACILIBACTERIA = 25;
}
