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

package org.biomojo.blast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.biomojo.alphabet.AminoAcidAlphabet;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.SeqList;
import org.biomojo.sequence.SequenceService;
import org.biomojo.util.LockUtil;
import org.biomojo.util.ProcessUtil;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class BlastFileService {
	private final static Logger logger = LoggerFactory
			.getLogger(BlastFileService.class.getName());

	@Inject
	private SequenceService sequenceService;

	private String dataPath;

	private String blastPath;

	private final Map<File, File> fastaFiles = Collections
			.synchronizedMap(new HashMap<File, File>());

	private final Map<File, File> dbFiles = Collections
			.synchronizedMap(new HashMap<File, File>());

	public File getFastaFile(
			final SeqList<? extends ByteSeq<ByteAlphabet>> querySequenceList) {
		logger.info("Entering getFastaFile()");

		File fastaFile = null;

		synchronized (fastaFiles) {
			fastaFile = new File(dataPath + "sequencelist-"
					+ querySequenceList.getId() + ".fa");

			if (!fastaFiles.containsKey(fastaFile)) {
				fastaFiles.put(fastaFile, fastaFile);
			}
		}

		fastaFile = fastaFiles.get(fastaFile);

		synchronized (fastaFile) {
			createFastafile(querySequenceList, fastaFile);
		}

		logger.info("Leaving getFastaFile()");
		return fastaFile;

	}

	public File getBlastDatabase(
			final SeqList<? extends ByteSeq<ByteAlphabet>> sequenceList) {
		logger.info("Entering getBlastDatabase()");

		File dbFile = null;

		logger.info("Looking up dbFile in dbFiles");

		synchronized (dbFiles) {
			dbFile = new File(dataPath + "blastdb-" + sequenceList.getId()
					+ ".fa");

			if (!dbFiles.containsKey(dbFile)) {
				dbFiles.put(dbFile, dbFile);
			}
		}

		logger.info("Retrieving dbFile from dbFiles");
		dbFile = dbFiles.get(dbFile);

		synchronized (dbFile) {
			logger.info("Getting fastaFile for database");
			final File sequenceFile = getFastaFile(sequenceList);
			logger.info("Creating database");
			createDatabase(dbFile, sequenceFile, sequenceList);
		}

		logger.info("Leaving getBlastDatabase()");
		return dbFile;

	}

	private void createFastafile(
			final SeqList<? extends ByteSeq<ByteAlphabet>> querySequenceList,
			final File file) {
		try {
			logger.info("Checking for existance of fasta file: "
					+ file.getCanonicalPath());

			final LockUtil lock = new LockUtil(file);
			lock.lock();

			if (file.exists()) {
				logger.info("Fasta file exists: " + file.getCanonicalPath());
			} else {
				logger.info("Fasta file does not exist, creating: "
						+ file.getCanonicalPath());

				sequenceService.saveFastxFile(file, querySequenceList);

				logger.info("Done creating fasta file: "
						+ file.getCanonicalPath());
			}

			lock.unlock();
		} catch (final FileNotFoundException e) {
			throw new UncheckedException(e);
		} catch (final IOException e) {
			throw new UncheckedException(e);
		}

	}

	public void createDatabase(final File dbFile, final File sequenceFile,
			final SeqList<? extends ByteSeq<ByteAlphabet>> sequenceList) {
		try {
			logger.info("Checking for existance of blast database: "
					+ dbFile.getCanonicalPath());

			final LockUtil lock = new LockUtil(dbFile);
			lock.lock();

			if (dbFile.exists()) {
				logger.info("Blast database exists: "
						+ dbFile.getCanonicalPath());
			} else {
				logger.info("Blast database does not exist, creating: "
						+ dbFile.getCanonicalPath() + " from: "
						+ sequenceFile.getCanonicalPath());

				dbFile.createNewFile();

				// TO DO - Need a better way to represent / determine rawData
				// type for rawData lists
				final boolean protein = sequenceList.get(0).getAlphabet() instanceof AminoAcidAlphabet;

				String dbtype = "nucl";
				if (protein) {
					dbtype = "prot";
				}

				final String program = blastPath + "makeblastdb";

				final String[] params = new String[] { program, "-dbtype",
						dbtype, "-in", sequenceFile.getAbsolutePath(), "-out",
						dbFile.getAbsolutePath() };

				final int exitCode = ProcessUtil
						.executeProcessWithExitCode(params);

				if (exitCode != 0) {
					throw new UncheckedException(
							"Error creating blast database file");
				}

			}

			lock.unlock();

		} catch (final Exception e) {
			throw new UncheckedException("Error creating blast database file",
					e);
		}

	}
}
