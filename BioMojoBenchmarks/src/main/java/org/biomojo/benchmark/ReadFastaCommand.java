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
package org.biomojo.benchmark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Supplier;

import org.biomojo.alphabet.IUPACAlphabet;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.sequence.ByteSeq;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "read_fasta")
public class ReadFastaCommand extends BaseCommand {
	private static final Logger logger = LoggerFactory
			.getLogger(ReadFastaCommand.class.getName());

	/**
	 * @see org.java0.cli.Command#run()
	 */
	@Override
	public void run() {
		try {
			logger.info("BioMojo Fasta Read Benchmark");

			// System.in.read();
			final FastaInputStream inputStream = new FastaInputStream(
					new FileInputStream(inputFile), false);

			int recordCount = 0;
			long totalLength = 0;

			Supplier<ByteSeq<IUPACAlphabet>> provider = new ByteSeqProvider();
			if (encode) {
				provider = new EncodedByteSeqProvider();
			}
			final ByteSeq<IUPACAlphabet> sequence = provider.get();

			while (inputStream.read(sequence)) {
				totalLength += sequence.size();
				++recordCount;
			}
			inputStream.close();
			System.gc();
			logger.info("Done loading " + recordCount + " sequences");
			logger.info("Total length is " + totalLength + " bases");

			Thread.sleep(0);

		} catch (final FileNotFoundException e) {
			throw new UncheckedException(e);
		} catch (final IOException e) {
			throw new UncheckedException(e);
		} catch (final InterruptedException e) {
			logger.error("Caught exception in auto-generated catch block", e);
		}

	}
}
