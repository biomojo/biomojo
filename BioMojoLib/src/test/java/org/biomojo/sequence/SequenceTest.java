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
package org.biomojo.sequence;

import org.biomojo.BioMojo;
import org.biomojo.alphabet.DNA;
import org.biomojo.alphabet.InvalidSymbolException;
import org.biomojo.codec.TwoBitByteCodec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public class SequenceTest {

    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(SequenceTest.class.getName());

    public SequenceTest() {
        BioMojo.init();
    }

    @Test(expected = InvalidSymbolException.class)
    public void testValidation() {
        final EncodedByteSeq<DNA> seq = new EncodedByteSeq<>(BioMojo.getObject(DNA.class),
                BioMojo.getObject(TwoBitByteCodec.class));
        seq.setAll("N".getBytes());
    }

}
