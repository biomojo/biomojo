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

package org.biomojo.sequence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.SequenceIdHeaderBuilder;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.io.fastx.FastaOutputStream;
import org.biomojo.util.DbUtil;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class SequenceService {
	private static final Logger logger = LoggerFactory
			.getLogger(SequenceService.class.getName());

	public static final int BATCH_SIZE = 50000;

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private DbUtil dbUtil;

	@Transactional
	public SeqList<ByteSeq<ByteAlphabet>> loadFastxFile(final File fastxFile,
			final String name, String description, final int alphabetId) {

		FastaInputStream fastxInputStream = null;
		final ByteSeq<ByteAlphabet> sequence = null;

		try {
			fastxInputStream = new FastaInputStream(new BufferedInputStream(
					new FileInputStream(fastxFile)));
			fastxInputStream.read(sequence);
		} catch (final FileNotFoundException e) {
			throw new UncheckedException(e);
		}

		if (description == null) {
			description = name;
		}

		final AbstractSeqList<ByteSeqImpl<ByteAlphabet>> sequenceList = new SeqArrayList<ByteSeqImpl<ByteAlphabet>>(
				name);

		int recordCount = 1;

		final Collection<Object> clearList = new ArrayList<Object>();

		do {
			// sequenceList.add(sequence);
			entityManager.persist(sequence);
			clearList.add(sequence);

			if (recordCount % 1000 == 0) {
				logger.info("Processed record # " + recordCount);
			}

			if (recordCount % 5000 == 0) {
				dbUtil.clearCache(clearList);
				clearList.clear();
			}

			++recordCount;
		} while (fastxInputStream.read(sequence));

		try {
			fastxInputStream.close();
		} catch (final IOException e) {
		}

		entityManager.persist(sequenceList);

		logger.info("Finished loading records");
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public void saveFastxFile(final File fastxFile,
			SeqList<? extends ByteSeq<ByteAlphabet>> sequenceList)
			throws IOException {
		final FastaOutputStream output = new FastaOutputStream(
				new BufferedOutputStream(new FileOutputStream(fastxFile)),
				new SequenceIdHeaderBuilder());

		// Write Fasta-formatted queryFile using the database ID as the
		// header
		int recordCount = 0;

		int startPos = 0;
		long endPos = 0;
		if (sequenceList instanceof SeqSubList) {
			startPos = ((SeqSubList<ByteSeqImpl<ByteAlphabet>>) sequenceList)
					.getFromIndex();
			endPos = ((SeqSubList<ByteSeqImpl<ByteAlphabet>>) sequenceList)
					.getToIndex();
			sequenceList = ((SeqSubList<ByteSeqImpl<ByteAlphabet>>) sequenceList)
					.getTargetList();
		} else {
			endPos = sequenceList.size();
		}

		final long id = sequenceList.getId();
		for (long i = startPos; i < endPos; i = i + BATCH_SIZE) {
			long lastPos = i + BATCH_SIZE;
			if (lastPos > endPos) {
				lastPos = endPos;
			}

			logger.info("Retrieving sequences, start = " + i + ", end = "
					+ lastPos);
			final List<ByteSeqImpl<ByteAlphabet>> sequences = getSequences(id, i,
					lastPos);

			for (final ByteSeqImpl<ByteAlphabet> sequence : sequences) {
				++recordCount;
				if (recordCount % 10000 == 1) {
					logger.info("Writing record #" + recordCount + " to "
							+ fastxFile.getCanonicalPath());
				}
				output.write(sequence);
			}
		}

		output.close();

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ByteSeqImpl<ByteAlphabet>> getSequences(final long sequenceListId,
			final long first, final long last) {
		final Query query = entityManager.createQuery("select sequences from "
				+ "AbstractMultiSequence MultiSeq "
				+ "join MultiSeq.sequences sequences "
				+ "where MultiSeq.id = :MultiSeqId "
				+ "order by index(sequences)", ByteSeqImpl.class);
		query.setParameter("MultiSeqId", sequenceListId);
		query.setFirstResult((int) first);
		query.setMaxResults((int) (last - first));
		return query.getResultList();
	}
}
