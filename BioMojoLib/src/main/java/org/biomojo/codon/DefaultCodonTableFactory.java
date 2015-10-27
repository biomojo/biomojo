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

import org.biomojo.core.AbstractIdBasedFactory;
import org.java0.factory.Config;
import org.java0.factory.ConfiguredObjectProvider;
import org.java0.factory.FactoryException;

// TODO: Auto-generated Javadoc
/**
 *
 * Codon tables derived from Version 4.0 of NCBI published tables here:
 * ftp://ftp.ncbi.nih.gov/entrez/misc/data/gc.prt
 *
 * @author Hugh Eaves
 *
 */
public class DefaultCodonTableFactory extends AbstractIdBasedFactory<CodonTable> {
    
    /**
     * Instantiates a new default codon table factory.
     */
    DefaultCodonTableFactory() {
        super(new CodonTable[0]);

        registerProvider(CodonTableId.STANDARD, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "---M---------------M---------------M----------------------------");
            }
        }, true);

        registerProvider(CodonTableId.VERTEBRATE_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSS**VVVVAAAADDEEGGGG",
                        "--------------------------------MMMM---------------M------------");
            }
        }, true);
        registerProvider(CodonTableId.YEAST_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CCWWTTTTPPPPHHQQRRRRIIMMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "----------------------------------MM----------------------------");
            }
        }, true);
        registerProvider(CodonTableId.MOLD_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "--MM---------------M------------MMMM---------------M------------");
            }
        }, true);
        registerProvider(CodonTableId.INVERTEBRATE_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSSSSVVVVAAAADDEEGGGG",
                        "---M----------------------------MMMM---------------M------------");
            }
        }, true);
        registerProvider(CodonTableId.CILIATE_NUCLEAR, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYYQQCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "-----------------------------------M----------------------------");
            }
        }, true);
        registerProvider(CodonTableId.ECHINODERM_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNNKSSSSVVVVAAAADDEEGGGG",
                        "-----------------------------------M---------------M------------");
            }
        }, true);
        registerProvider(CodonTableId.EUPLOTID_NUCLEAR, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CCCWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "-----------------------------------M----------------------------");
            }
        }, true);
        registerProvider(CodonTableId.BACTERIAL_PLASTID, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "---M---------------M------------MMMM---------------M------------");
            }
        }, true);
        registerProvider(CodonTableId.ALTERNATIVE_YEAST_NUCLEAR, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CC*WLLLSPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "-------------------M---------------M----------------------------");
            }
        }, true);
        registerProvider(CodonTableId.ASCIDIAN_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSSGGVVVVAAAADDEEGGGG",
                        "---M------------------------------MM---------------M------------");
            }
        }, true);
        registerProvider(CodonTableId.ALTERNATIVE_FLATWORM_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYYY*CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNNKSSSSVVVVAAAADDEEGGGG",
                        "-----------------------------------M----------------------------");
            }
        }, true);
        registerProvider(CodonTableId.BLEPHARISMA_MACRONUCLEAR, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY*QCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "-----------------------------------M----------------------------");
            }
        }, true);
        registerProvider(CodonTableId.CHLOROPHYCEAN_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY*LCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "-----------------------------------M----------------------------");
            }
        }, true);
        registerProvider(CodonTableId.TREMATODE_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNNKSSSSVVVVAAAADDEEGGGG",
                        "-----------------------------------M---------------M------------");
            }
        }, true);
        registerProvider(CodonTableId.SCENEDESMUS_OBLIQUUS_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSS*SYY*LCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "-----------------------------------M----------------------------");
            }
        }, true);
        registerProvider(CodonTableId.THRAUSTOCHYTRIUM_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FF*LSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "--------------------------------M--M---------------M------------");
            }
        }, true);
        registerProvider(CodonTableId.PTEROBRANCHIA_MITOCHONDRIAL, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSSKVVVVAAAADDEEGGGG",
                        "---M---------------M---------------M---------------M------------");
            }
        }, true);
        registerProvider(CodonTableId.CGRACILIBACTERIA, new ConfiguredObjectProvider<CodonTable>() {
            @Override
            public CodonTable getObject(Config<CodonTable> config) throws FactoryException {
                return new CodonTableImpl((int) config.values()[0],
                        "FFLLSSSSYY**CCGWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
                        "---M-------------------------------M---------------M------------");
            }
        }, true);

    }
}
