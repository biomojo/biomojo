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

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class AlphabetTest.
 *
 * @author Hugh Eaves
 */
public class AlphabetTest {
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AlphabetTest.class.getName());

    /** The random. */
    Random random = new Random(0);

    /**
     * Test byte alphabet.
     */
    public void testByteAlphabet() {
        testAlphabet(Byte.class);
    }

    /**
     * Test it.
     */
    public void testIt() {
        final Alphabet<Byte> alphabet = AllByteAlphabet.INSTANCE;
        alphabet.getSymbolForOrdinal(0);

    }

    /**
     * Test alphabet.
     *
     * @param <T> the generic type
     * @param type the type
     */
    public <T> void testAlphabet(final Class<T> type) {
        for (int i = 0; i < AlphabetId.LAST_ALPHABET_ID; ++i) {
            @SuppressWarnings("unchecked")
            final Alphabet<T> alphabet = (Alphabet<T>) Alphabets.getAlphabet(i);
            if (alphabet != null) {
                logger.debug("testing alphabet id {}", alphabet.getId());
                for (int j = 0; j < alphabet.numSymbols(); ++j) {
                    final T symbol = alphabet.getSymbolForOrdinal(j);
                    assertTrue(alphabet.isValid(symbol));
                    assertTrue(alphabet.getOrdinalForSymbol(symbol) == j);
                    assertTrue(alphabet.getOrdinalForSymbol(alphabet.getCanonical(symbol)) <= j);
                    assertTrue(alphabet.isEquivalent(symbol, alphabet.getCanonical(symbol)));
                    @SuppressWarnings("unchecked")
                    final T[] symbols = (T[]) Array.newInstance(type, 1000);
                    for (int k = 0; k < symbols.length; ++k) {
                        symbols[k] = alphabet.getSymbolForOrdinal(random.nextInt(alphabet.numSymbols()));
                    }
                    assertTrue(alphabet.isValid(symbols));
                }
            }
        }
    }
}
