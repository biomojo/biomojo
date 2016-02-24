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

import java.util.function.Supplier;

import org.java0.core.type.LongIdentified;
import org.java0.factory.LongSelectorFactory;

/**
 *
 * Codon tables derived from Version 4.0 of NCBI published tables here:
 * ftp://ftp.ncbi.nih.gov/entrez/misc/data/gc.prt
 *
 * @author Hugh Eaves
 *
 */
public class DefaultCodonTables {

    /**
     * Instantiates a new default codon table factory.
     */
    public static void registerWithFactory(final LongSelectorFactory factory) {

        register(factory, CodonTableId.STANDARD, "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "---M---------------M---------------M----------------------------");

        register(factory, CodonTableId.VERTEBRATE_MITOCHONDRIAL,

                "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSS**VVVVAAAADDEEGGGG",
                "--------------------------------MMMM---------------M------------");
        register(factory, CodonTableId.YEAST_MITOCHONDRIAL,

                "FFLLSSSSYY**CCWWTTTTPPPPHHQQRRRRIIMMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "----------------------------------MM----------------------------");
        register(factory, CodonTableId.MOLD_MITOCHONDRIAL,

                "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "--MM---------------M------------MMMM---------------M------------");
        register(factory, CodonTableId.INVERTEBRATE_MITOCHONDRIAL,

                "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSSSSVVVVAAAADDEEGGGG",
                "---M----------------------------MMMM---------------M------------");
        register(factory, CodonTableId.CILIATE_NUCLEAR,

                "FFLLSSSSYYQQCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "-----------------------------------M----------------------------");
        register(factory, CodonTableId.ECHINODERM_MITOCHONDRIAL,

                "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNNKSSSSVVVVAAAADDEEGGGG",
                "-----------------------------------M---------------M------------");
        register(factory, CodonTableId.EUPLOTID_NUCLEAR,

                "FFLLSSSSYY**CCCWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "-----------------------------------M----------------------------");
        register(factory, CodonTableId.BACTERIAL_PLASTID,

                "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "---M---------------M------------MMMM---------------M------------");
        register(factory, CodonTableId.ALTERNATIVE_YEAST_NUCLEAR,

                "FFLLSSSSYY**CC*WLLLSPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "-------------------M---------------M----------------------------");
        register(factory, CodonTableId.ASCIDIAN_MITOCHONDRIAL,

                "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSSGGVVVVAAAADDEEGGGG",
                "---M------------------------------MM---------------M------------");
        register(factory, CodonTableId.ALTERNATIVE_FLATWORM_MITOCHONDRIAL,

                "FFLLSSSSYYY*CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNNKSSSSVVVVAAAADDEEGGGG",
                "-----------------------------------M----------------------------");
        register(factory, CodonTableId.BLEPHARISMA_MACRONUCLEAR,

                "FFLLSSSSYY*QCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "-----------------------------------M----------------------------");
        register(factory, CodonTableId.CHLOROPHYCEAN_MITOCHONDRIAL,

                "FFLLSSSSYY*LCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "-----------------------------------M----------------------------");
        register(factory, CodonTableId.TREMATODE_MITOCHONDRIAL,

                "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNNKSSSSVVVVAAAADDEEGGGG",
                "-----------------------------------M---------------M------------");
        register(factory, CodonTableId.SCENEDESMUS_OBLIQUUS_MITOCHONDRIAL,

                "FFLLSS*SYY*LCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "-----------------------------------M----------------------------");
        register(factory, CodonTableId.THRAUSTOCHYTRIUM_MITOCHONDRIAL,

                "FF*LSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "--------------------------------M--M---------------M------------");
        register(factory, CodonTableId.PTEROBRANCHIA_MITOCHONDRIAL,

                "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSSKVVVVAAAADDEEGGGG",
                "---M---------------M---------------M---------------M------------");
        register(factory, CodonTableId.CGRACILIBACTERIA,

                "FFLLSSSSYY**CCGWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                "---M-------------------------------M---------------M------------");

        factory.setDefaultType(CodonTable.class, CodonTableId.STANDARD);
    }

    private static void register(final LongSelectorFactory factory, final long id, final String aminoAcids,
            final String startCodons) {
        factory.registerProvider(id, new Supplier<LongIdentified>() {

            @Override
            public LongIdentified get() {
                return new DefaultCodonTable(id, aminoAcids, startCodons);
            }

        }, true);

    }
}
