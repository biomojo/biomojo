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
package org.biomojo.examples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.InvalidSymbolException;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.io.fastx.FastaOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.ByteSeqImpl;
import org.java0.cli.InputOutputCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "fastahead", commandDescription = "Copy n records from the head of a FASTA file")
public class FastaHeadCommand extends InputOutputCommand {
	private static final Logger logger = LoggerFactory
			.getLogger(FastaHeadCommand.class.getName());

	@Parameter(names = { "-n", "--numrecs" }, required = true, description = "Number of records to copy")
	protected int numRecords = 0;

	@Parameter(names = { "-c", "--canonicalize" }, description = "Canonicalize characters")
	protected boolean canonicalize;

	/**
	 * @see org.java0.cli.Command#run()
	 */
	@Override
	public void run() {

		try {
			final FastaInputStream input = new FastaInputStream(
					new FileInputStream(inputFile));
			final FastaOutputStream output = new FastaOutputStream(
					new FileOutputStream(outputFile));
			ByteSeq<ByteAlphabet> record = new ByteSeqImpl<ByteAlphabet>(
					Alphabets.getAlphabet(AlphabetId.DNA, ByteAlphabet.class));
			int i = 0;
			while (i < numRecords) {
				try {
					if (!input.read(record)) {
						break;
					}
					if (canonicalize) {
						record.canonicalize();
					}
					output.write(record);
					++i;
				} catch (final InvalidSymbolException e) {
					logger.info("Skipping record ", e);
				}

			}
			record = null;
			input.close();
			output.close();
		} catch (final FileNotFoundException e) {
			logger.error("Caught exception in auto-generated catch block", e);
		} catch (final IOException e) {
			logger.error("Caught exception in auto-generated catch block", e);
		}
	}
}
