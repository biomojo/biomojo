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
package org.biomojo.sequence.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.biomojo.io.ParseException;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public abstract class FastxParserTest<T extends ByteSeq<?>> {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(FastxParserTest.class.getName());

	protected static final int bufferTestSizes[] = { 1, 2, 3, 7, 9, 255, 1000,
			1024, 1024 * 1024, 1024 * 1024 * 128 };

	protected static final String[] invalidData = { " ", "    ", "\n", "\n\n",
			"\n \n \n" };

	@Test
	public void testEmptyFile() throws IOException {
		testCopy("".getBytes(), 0, true);
	}

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

	public void testCopy(final byte[] testData, final int expectedNumRecords,
			final boolean matchExpected) throws IOException {
		for (final int bufSize : bufferTestSizes) {
			final byte[] result = copyRecords(testData, bufSize,
					expectedNumRecords);
			if (matchExpected) {
				assertArrayEquals(testData, result);
			}
		}
	}

	protected byte[] copyRecords(final byte[] testData, final int bufSize,
			final int expectedReadCount) throws IOException {

		final SequenceInputStream<T> sequenceInputStream = getInputStream(
				testData, bufSize);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final SequenceOutputStream<T> sequenceOutputStream = getOutputStream(outputStream);

		final T sequence = getSequence();

		int numReads = 0;
		while (sequenceInputStream.read(sequence)) {
			sequenceOutputStream.write(sequence);
			++numReads;
		}

		sequenceInputStream.close();
		sequenceOutputStream.close();

		assertEquals(expectedReadCount, numReads);

		return outputStream.toByteArray();

	}

	protected byte[] getTestDataFromClasspath(final String path)
			throws IOException {
		final InputStream testDataStream = this.getClass().getClassLoader()
				.getResourceAsStream(path);

		final ByteArrayOutputStream testDataBuf = new ByteArrayOutputStream();
		int b;
		while ((b = testDataStream.read()) >= 0) {
			testDataBuf.write(b);
		}

		testDataStream.close();
		testDataBuf.close();

		return testDataBuf.toByteArray();
	}

	protected abstract SequenceInputStream<T> getInputStream(byte[] testData,
			int bufSize);

	protected abstract SequenceOutputStream<T> getOutputStream(
			ByteArrayOutputStream outputStream);

	protected abstract T getSequence();
}
