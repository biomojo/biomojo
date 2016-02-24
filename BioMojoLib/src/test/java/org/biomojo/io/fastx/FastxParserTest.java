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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.biomojo.BioMojo;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.SeqInput;
import org.biomojo.io.SeqOutput;
import org.biomojo.sequence.ByteSeq;
import org.java0.core.exception.ParseException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FastxParserTest.
 *
 * @author Hugh Eaves
 * @param <T>
 *            the generic type
 */
public abstract class FastxParserTest<A extends ByteAlphabet, T extends ByteSeq<A>> {

    private static final Logger logger = LoggerFactory.getLogger(FastxParserTest.class.getName());

    /** The Constant bufferTestSizes. */
    protected static final int bufferTestSizes[] = { 1, 2, 3, 7, 9, 255, 1000, 1024, 1024 * 1024, 1024 * 1024 * 128 };

    /** The Constant invalidData. */
    protected static final String[] invalidData = { " ", "    ", "\n", "\n\n", "\n \n \n" };

    public FastxParserTest() {
        BioMojo.init();
    }

    /**
     * Test empty file.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testEmptyFile() throws IOException {
        testCopy("".getBytes(), 0, true);
    }

    /**
     * Test invalid files.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testInvalidFiles() throws IOException {
        for (final String testData : invalidData) {
            try {
                testCopy(testData.getBytes(), 0, true);
            } catch (final ParseException e) {
                return;
            }
            org.junit.Assert.fail();
        }
    }

    /**
     * Test copy.
     *
     * @param testData
     *            the test data
     * @param expectedNumRecords
     *            the expected num records
     * @param matchExpected
     *            the match expected
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void testCopy(final byte[] testData, final int expectedNumRecords, final boolean matchExpected)
            throws IOException {
        for (final int bufSize : bufferTestSizes) {
            final byte[] result = copyRecords(testData, bufSize, expectedNumRecords);
            if (matchExpected) {
                assertArrayEquals(testData, result);
            }
        }
    }

    /**
     * Copy records.
     *
     * @param testData
     *            the test data
     * @param bufSize
     *            the buf size
     * @param expectedReadCount
     *            the expected read count
     * @return the byte[]
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected byte[] copyRecords(final byte[] testData, final int bufSize, final int expectedReadCount)
            throws IOException {

        final SeqInput<T> sequenceInputStream = getInputStream(testData, bufSize);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final SeqOutput<T> sequenceOutputStream = getOutputStream(outputStream);

        int numReads = 0;
        for (final T seq : sequenceInputStream) {
            logger.debug("record # " + numReads);
            sequenceOutputStream.write(seq);
            ++numReads;
        }

        sequenceInputStream.close();
        sequenceOutputStream.close();

        assertEquals(expectedReadCount, numReads);

        return outputStream.toByteArray();

    }

    /**
     * Gets the test data from classpath.
     *
     * @param path
     *            the path
     * @return the test data from classpath
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected byte[] getTestDataFromClasspath(final String path) throws IOException {
        final InputStream testDataStream = this.getClass().getClassLoader().getResourceAsStream(path);

        final ByteArrayOutputStream testDataBuf = new ByteArrayOutputStream();
        int b;
        while ((b = testDataStream.read()) >= 0) {
            testDataBuf.write(b);
        }

        testDataStream.close();
        testDataBuf.close();

        return testDataBuf.toByteArray();
    }

    /**
     * Gets the input stream.
     *
     * @param testData
     *            the test data
     * @param bufSize
     *            the buf size
     * @return the input stream
     */
    protected abstract SeqInput<T> getInputStream(byte[] testData, int bufSize);

    /**
     * Gets the output stream.
     *
     * @param outputStream
     *            the output stream
     * @return the output stream
     */
    protected abstract SeqOutput<T> getOutputStream(ByteArrayOutputStream outputStream);
}
