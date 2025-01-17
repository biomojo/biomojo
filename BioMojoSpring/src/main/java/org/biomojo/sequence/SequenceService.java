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
import org.biomojo.io.SeqIdHeaderBuilder;
import org.biomojo.io.fastx.FastaInput;
import org.biomojo.io.fastx.FastaOutput;
import org.biomojo.util.DbUtil;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class SequenceService.
 */
@Named
public class SequenceService {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(SequenceService.class.getName());

    /** The Constant BATCH_SIZE. */
    public static final int BATCH_SIZE = 50000;

    /** The entity manager. */
    @PersistenceContext
    private EntityManager entityManager;

    /** The db util. */
    @Inject
    private DbUtil dbUtil;

    /**
     * Load fastx file.
     *
     * @param fastxFile
     *            the fastx file
     * @param name
     *            the name
     * @param description
     *            the description
     * @param alphabetId
     *            the alphabet id
     * @return the seq list
     */
    @Transactional
    public SeqList<ByteSeq<ByteAlphabet>> loadFastxFile(final File fastxFile, final String name, String description,
            final int alphabetId) {

        FastaInput<ByteAlphabet> fastxInputStream = null;
        final ByteSeq<ByteAlphabet> sequence = null;

        try {
            fastxInputStream = new FastaInput<>(new BufferedInputStream(new FileInputStream(fastxFile)),
                    ByteAlphabet.class);
            fastxInputStream.read(sequence);
        } catch (final FileNotFoundException e) {
            throw new UncheckedException(e);
        }

        if (description == null) {
            description = name;
        }

        final AbstractSeqList<BasicByteSeq<ByteAlphabet>> sequenceList = new SeqArrayList<BasicByteSeq<ByteAlphabet>>(
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

    /**
     * Save fastx file.
     *
     * @param fastxFile
     *            the fastx file
     * @param sequenceList
     *            the sequence list
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public void saveFastxFile(final File fastxFile, SeqList<? extends ByteSeq<ByteAlphabet>> sequenceList)
            throws IOException {
        final FastaOutput<ByteAlphabet> output = new FastaOutput<>(
                new BufferedOutputStream(new FileOutputStream(fastxFile)), new SeqIdHeaderBuilder());

        // Write Fasta-formatted queryFile using the database ID as the
        // header
        int recordCount = 0;

        int startPos = 0;
        long endPos = 0;
        if (sequenceList instanceof SeqSubList) {
            startPos = ((SeqSubList<BasicByteSeq<ByteAlphabet>>) sequenceList).getFromIndex();
            endPos = ((SeqSubList<BasicByteSeq<ByteAlphabet>>) sequenceList).getToIndex();
            sequenceList = ((SeqSubList<BasicByteSeq<ByteAlphabet>>) sequenceList).getTargetList();
        } else {
            endPos = sequenceList.size();
        }

        final long id = sequenceList.getId();
        for (long i = startPos; i < endPos; i = i + BATCH_SIZE) {
            long lastPos = i + BATCH_SIZE;
            if (lastPos > endPos) {
                lastPos = endPos;
            }

            logger.info("Retrieving sequences, start = " + i + ", end = " + lastPos);
            final List<BasicByteSeq<ByteAlphabet>> sequences = getSequences(id, i, lastPos);

            for (final BasicByteSeq<ByteAlphabet> sequence : sequences) {
                ++recordCount;
                if (recordCount % 10000 == 1) {
                    logger.info("Writing record #" + recordCount + " to " + fastxFile.getCanonicalPath());
                }
                output.write(sequence);
            }
        }

        output.close();

    }

    /**
     * Gets the sequences.
     *
     * @param sequenceListId
     *            the sequence list id
     * @param first
     *            the first
     * @param last
     *            the last
     * @return the sequences
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public List<BasicByteSeq<ByteAlphabet>> getSequences(final long sequenceListId, final long first, final long last) {
        final Query query = entityManager.createQuery(
                "select sequences from " + "AbstractMultiSequence MultiSeq " + "join MultiSeq.sequences sequences "
                        + "where MultiSeq.id = :MultiSeqId " + "order by index(sequences)",
                BasicByteSeq.class);
        query.setParameter("MultiSeqId", sequenceListId);
        query.setFirstResult((int) first);
        query.setMaxResults((int) (last - first));
        return query.getResultList();
    }
}
