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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.biomojo.alignment.Aligner;
import org.biomojo.alignment.Alignment;
import org.biomojo.alignment.ByteSubstitutionMatrix;
import org.biomojo.alignment.MatchMismatchByteSubstitutionMatrix;
import org.biomojo.alignment.NeedlemanWunschAligner;
import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.ByteAlphabet;
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
@Parameters(commandNames = "align")
public class AlignCommand extends BaseCommand {
	private static final Logger logger = LoggerFactory
			.getLogger(AlignCommand.class.getName());

	/**
	 * @see org.java0.cli.Command#run()
	 */
	@Override
	public void run() {
		try {
			logger.info("BioMojo alignment benchmark");

			final FastaInputStream inputStream = new FastaInputStream(
					new FileInputStream(inputFile));
			final List<ByteSeq<IUPACAlphabet>> sequences = new ArrayList<>();

			Supplier<ByteSeq<IUPACAlphabet>> provider = new ByteSeqProvider();
			if (encode) {
				provider = new EncodedByteSeqProvider();
			}
			ByteSeq<IUPACAlphabet> sequence = provider.get();
			while (inputStream.read(sequence)) {
				logger.info("Read sequence" + sequence.getDescription());
				sequences.add(sequence);
				sequence = provider.get();
			}
			inputStream.close();

			logger.info("Done loading " + sequences.size() + " sequences");

			logger.info("Running alignments");

			final int numSeqs = sequences.size();

			final ByteSubstitutionMatrix matrix = new MatchMismatchByteSubstitutionMatrix(
					Alphabets.getAlphabet(AlphabetId.NUCLEOTIDE,
							ByteAlphabet.class), 1, -1);

			final Aligner<ByteSeq<IUPACAlphabet>> aligner = new NeedlemanWunschAligner<IUPACAlphabet>(
					matrix, -2);
			final List<ByteSeq<IUPACAlphabet>> seqList = new ArrayList<ByteSeq<IUPACAlphabet>>();
			for (int i = 0; i < numSeqs; ++i) {
				for (int j = 0; j < i; ++j) {
					seqList.clear();
					seqList.add(sequences.get(i));
					seqList.add(sequences.get(j));
					logger.debug("Aligning {} and {}", i, j);
					final Alignment<ByteSeq<IUPACAlphabet>> alignment = aligner
							.align(seqList);
					System.out.print(alignment.getScore() + "\t");
				}
				System.out.println();
			}

			// seqList.add(new DNASeq("AGTC".getBytes()));
			// seqList.add(new DNASeq("AGTC".getBytes()));
			// Alignment alignment = aligner.align(seqList);
			logger.info("Done");

			Thread.sleep(10);
		} catch (final FileNotFoundException e) {
			throw new UncheckedException(e);
		} catch (final IOException e) {
			throw new UncheckedException(e);
		} catch (final InterruptedException e) {
			throw new UncheckedException(e);
		}
	}
}
