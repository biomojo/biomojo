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
package org.biomojo.io.fastx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.IUPACAlphabetVariant;
import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.io.fastx.FastqOutputStream;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.BasicFastqSeq;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class FastqParserTest.
 */
public class FastqParserTest extends FastxParserTest<FastqSeq<? extends NucleotideAlphabet>> {
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(FastqParserTest.class.getName());

    /**
     * Instantiates a new fastq parser test.
     */
    public FastqParserTest() {
        super();
    }

    /**
     * Test good data.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testGoodData() throws IOException {
        testCopy(getTestDataFromClasspath("data/short_reads.fastq"), 25000, true);
    }

    /**
     * Gets the input stream.
     *
     * @param testData the test data
     * @param bufSize the buf size
     * @return the input stream
     * @see org.biomojo.io.fastx.FastxParserTest#getInputStream(byte[], int)
     */
    @Override
    protected SequenceInputStream<FastqSeq<? extends NucleotideAlphabet>> getInputStream(final byte[] testData,
            final int bufSize) {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(testData);
        return new FastqInputStream(inputStream, bufSize);
    }

    /**
     * Gets the output stream.
     *
     * @param outputStream the output stream
     * @return the output stream
     * @see org.biomojo.io.fastx.FastxParserTest#getOutputStream(java.io.ByteArrayOutputStream)
     */
    @Override
    protected SequenceOutputStream<FastqSeq<? extends NucleotideAlphabet>> getOutputStream(
            final ByteArrayOutputStream outputStream) {
        return new FastqOutputStream(outputStream);
    }

    /**
     * Gets the sequence.
     *
     * @return the sequence
     * @see org.biomojo.io.fastx.FastxParserTest#getSequence()
     */
    @Override
    protected FastqSeq<NucleotideAlphabet> getSequence() {
        final FastqSeq<NucleotideAlphabet> sequence = new BasicFastqSeq<NucleotideAlphabet>(
                Alphabets.getAlphabet(AlphabetId.NUCLEOTIDE | IUPACAlphabetVariant.WITH_ANY, NucleotideAlphabet.class));
        return sequence;
    }
}
