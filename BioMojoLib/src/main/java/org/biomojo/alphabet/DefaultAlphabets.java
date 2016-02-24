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

import java.util.function.Supplier;

import org.java0.core.type.LongIdentified;
import org.java0.factory.FactoryException;
import org.java0.factory.LongSelectorFactory;

public class DefaultAlphabets {

    public static void registerWithFactory(final LongSelectorFactory factory) {

        factory.register(new AllByte());

        for (int i = 0; i < IUPACVariant.NUM_VARIANTS; ++i) {

            final int id = i;
            factory.registerProvider(AlphabetId.DNA + id, new Supplier<LongIdentified>() {
                @Override
                public Alphabet<?> get() throws FactoryException {
                    return new BasicDNA(AlphabetId.DNA + id);
                }
            }, true);

            factory.registerProvider(AlphabetId.RNA + id, new Supplier<LongIdentified>() {
                @Override
                public Alphabet<?> get() throws FactoryException {
                    return new BasicRNA(AlphabetId.RNA + id);
                }
            }, true);

            factory.registerProvider(AlphabetId.NUCLEOTIDE + id, new Supplier<LongIdentified>() {
                @Override
                public Alphabet<?> get() throws FactoryException {
                    return new BasicNucleotide<>(AlphabetId.NUCLEOTIDE + id);
                }
            }, true);

            factory.registerProvider(AlphabetId.AMINO_ACID + id, new Supplier<LongIdentified>() {
                @Override
                public Alphabet<?> get() {
                    return new BasicAminoAcid(AlphabetId.AMINO_ACID + id);
                }
            }, true);
        }

        factory.setDefaultType(DNA.class, AlphabetId.DNA);
        factory.setDefaultType(RNA.class, AlphabetId.RNA);
        factory.setDefaultType(Nucleotide.class, AlphabetId.NUCLEOTIDE);
        factory.setDefaultType(AminoAcid.class, AlphabetId.AMINO_ACID);

        factory.register(new ASCII());

        factory.register(new Letters());
        factory.register(new UppercaseLetters());

        factory.register(new PhredQuality());
        factory.register(new SangerQuality());
        factory.register(new Illumina10Quality());
        factory.register(new Illumina13Quality());
    }
}
