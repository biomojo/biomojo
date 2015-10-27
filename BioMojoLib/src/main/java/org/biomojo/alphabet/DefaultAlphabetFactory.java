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

package org.biomojo.alphabet;

import org.biomojo.core.AbstractIdBasedFactory;
import org.java0.factory.Config;
import org.java0.factory.ConfiguredObjectProvider;
import org.java0.factory.FactoryException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating DefaultAlphabet objects.
 */
public class DefaultAlphabetFactory extends AbstractIdBasedFactory<Alphabet<?>> {

    /**
     * Instantiates a new default alphabet factory.
     */
    public DefaultAlphabetFactory() {
        super(new Alphabet<?>[0]);

        register(AllByteAlphabet.INSTANCE);

        for (int i = 0; i < IUPACAlphabetVariant.NUM_VARIANTS; ++i) {
            registerProvider(AlphabetId.DNA + i, new ConfiguredObjectProvider<Alphabet<?>>() {
                @Override
                public Alphabet<?> getObject(final Config<Alphabet<?>> config) throws FactoryException {
                    return new DNAAlphabetImpl((int) config.values()[0]);
                }
            }, true);
            registerProvider(AlphabetId.RNA + i, new ConfiguredObjectProvider<Alphabet<?>>() {
                @Override
                public Alphabet<?> getObject(final Config<Alphabet<?>> config) throws FactoryException {
                    return new RNAAlphabetImpl((int) config.values()[0]);
                }
            }, true);
            registerProvider(AlphabetId.NUCLEOTIDE + i, new ConfiguredObjectProvider<Alphabet<?>>() {
                @Override
                public Alphabet<?> getObject(final Config<Alphabet<?>> config) throws FactoryException {
                    return new NucleotideAlphabetImpl((int) config.values()[0]);
                }
            }, true);

            registerProvider(AlphabetId.AMINO_ACID + i, new ConfiguredObjectProvider<Alphabet<?>>() {
                @Override
                public Alphabet<?> getObject(final Config<Alphabet<?>> config) throws FactoryException {
                    return new AminoAcidAlphabetImpl((int) config.values()[0]);
                }
            }, true);
        }

        registerProvider(AlphabetId.ASCII, new ConfiguredObjectProvider<Alphabet<?>>() {
            @Override
            public Alphabet<?> getObject(final Config<Alphabet<?>> config) throws FactoryException {
                return new ASCIIAlphabet((int) config.values()[0]);
            }
        }, true);

        register(new SangerQualityScoreAlphabet());
        register(new Illumina10QualityScoreAlphabet());
        register(new Illumina13QualityScoreAlphabet());
    }
}
