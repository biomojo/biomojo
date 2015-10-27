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
package org.biomojo.io.fastx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.IUPACAlphabetVariant;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.ByteSeqImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public class FastaParserTest extends FastxParserTest<ByteSeq<? extends ByteAlphabet>> {
    private static final Logger logger = LoggerFactory.getLogger(FastaParserTest.class.getName());

    public FastaParserTest() {
        super();
    }

    @Test
    public void testGoodData() throws IOException {
        testCopy(getTestDataFromClasspath("data/dna_sequences.fasta"), 1770, true);
    }

    /**
     * @see org.biomojo.io.fastx.FastxParserTest#getInputStream(byte[], int)
     */
    @Override
    protected SequenceInputStream<ByteSeq<? extends ByteAlphabet>> getInputStream(final byte[] testData,
            final int bufSize) {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(testData);
        return new FastaInputStream(inputStream, bufSize);
    }

    /**
     * @see org.biomojo.io.fastx.FastxParserTest#getOutputStream(java.io.ByteArrayOutputStream)
     */
    @Override
    protected SequenceOutputStream<ByteSeq<? extends ByteAlphabet>> getOutputStream(
            final ByteArrayOutputStream outputStream) {
        return new FastaOutputStream(outputStream);
    }

    /**
     * @see org.biomojo.io.fastx.FastxParserTest#getSequence()
     */
    @Override
    protected ByteSeq<? extends ByteAlphabet> getSequence() {
        return new ByteSeqImpl<ByteAlphabet>(Alphabets.getAlphabet(
                AlphabetId.DNA | IUPACAlphabetVariant.WITH_ANY | IUPACAlphabetVariant.WITH_NON_CANONICAL,
                ByteAlphabet.class));
    }
}
