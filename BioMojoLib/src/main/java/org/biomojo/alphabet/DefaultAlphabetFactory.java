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

        register(new AllByte());

        for (int i = 0; i < IUPACVariant.NUM_VARIANTS; ++i) {
            registerProvider(AlphabetId.DNA + i, new ConfiguredObjectProvider<Alphabet<?>>() {
                @Override
                public Alphabet<?> getObject(final Config<Alphabet<?>> config) throws FactoryException {
                    return new BasicDNA((int) config.values()[0]);
                }
            }, true);
            registerProvider(AlphabetId.RNA + i, new ConfiguredObjectProvider<Alphabet<?>>() {
                @Override
                public Alphabet<?> getObject(final Config<Alphabet<?>> config) throws FactoryException {
                    return new BasicRNA((int) config.values()[0]);
                }
            }, true);
            registerProvider(AlphabetId.NUCLEOTIDE + i, new ConfiguredObjectProvider<Alphabet<?>>() {
                @Override
                public Alphabet<?> getObject(final Config<Alphabet<?>> config) throws FactoryException {
                    return new BasicNucleotide<>((int) config.values()[0]);
                }
            }, true);

            registerProvider(AlphabetId.AMINO_ACID + i, new ConfiguredObjectProvider<Alphabet<?>>() {
                @Override
                public Alphabet<?> getObject(final Config<Alphabet<?>> config) throws FactoryException {
                    return new BasicAminoAcid((int) config.values()[0]);
                }
            }, true);
        }

        register(new ASCII());

        register(new Letters<Letters<?>>());
        register(new UppercaseLetters());

        register(new SangerQuality());
        register(new Illumina10Quality());
        register(new Illumina13Quality());
    }
}
