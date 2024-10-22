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

package org.biomojo.blast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.blast.blastoutput.BlastOutput;
import org.biomojo.core.CommonProperties;
import org.biomojo.project.Project;
import org.biomojo.property.LongProperty;
import org.biomojo.property.StringProperty;
import org.biomojo.sequence.AbstractSeqList;
import org.biomojo.sequence.BasicByteSeq;
import org.biomojo.sequence.SeqSubList;
import org.biomojo.util.DbUtil;
import org.biomojo.util.ProcessUtil;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class BlastService.
 */
@Named
public class BlastService {

    /** The Constant logger. */
    private final static Logger logger = LoggerFactory.getLogger(BlastService.class.getName());

    /** The db util. */
    @Inject
    private DbUtil dbUtil;

    /** The entity manager. */
    @PersistenceContext
    private EntityManager entityManager;

    /** The blast file service. */
    @Inject
    private BlastFileService blastFileService;

    /** The blast output parser. */
    @Inject
    private BlastOutputParser blastOutputParser;

    /** The sequence resolver. */
    @Inject
    private QueryIdSequenceResolver sequenceResolver;

    /** The blast path. */
    private String blastPath;

    /** The datafiles path. */
    private String datafilesPath;

    /**
     * The Class BlastExecution.
     */
    private class BlastExecution {

        /** The params. */
        private final String[] params;

        /** The result file. */
        private final File resultFile;

        /**
         * Instantiates a new blast execution.
         *
         * @param params
         *            the params
         * @param resultFile
         *            the result file
         */
        public BlastExecution(final String[] params, final File resultFile) {
            this.params = params;
            this.resultFile = resultFile;
        }

        /**
         * Gets the params.
         *
         * @return the params
         */
        public String[] getParams() {
            return params;
        }

        /**
         * Gets the result file.
         *
         * @return the result file
         */
        public File getResultFile() {
            return resultFile;
        }

    }

    /**
     * Creates the dataset.
     *
     * @param projectName
     *            the project name
     * @param datasetName
     *            the dataset name
     * @param programName
     *            the program name
     * @param querySets
     *            the query sets
     * @param databaseSets
     *            the database sets
     * @param maxTargetSeqs
     *            the max target seqs
     * @param taskSize
     *            the task size
     * @return the blast data set
     */
    @Transactional
    public BlastDataSet createDataset(final String projectName, String datasetName, final String programName,
            final List<String> querySets, final List<String> databaseSets, final int maxTargetSeqs,
            final int taskSize) {

        final Project project = dbUtil.findByAttribute(Project.class, CommonProperties.NAME, projectName);

        if (project == null) {
            throw new UncheckedException("Project not found");
        }

        if (datasetName == null) {
            datasetName = project.getProp(CommonProperties.NAME, StringProperty.class).getString() + "-"
                    + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        }

        BlastDataSet blastDataset = dbUtil.findByAttribute(BlastDataSet.class, CommonProperties.NAME, datasetName);

        if (blastDataset != null) {
            throw new UncheckedException("Duplicate name for blast dataset");
        } else {
            blastDataset = new BlastDataSet(datasetName);
        }

        final BlastProgram program = BlastProgram.valueOf(programName.toUpperCase());
        blastDataset.setProgram(program);

        for (final String querySet : querySets) {
            final AbstractSeqList<BasicByteSeq<ByteAlphabet>> sequenceList = dbUtil
                    .findByAttribute(AbstractSeqList.class, CommonProperties.NAME, querySet);
            if (sequenceList == null) {
                throw new UncheckedException("AbstractMultiSequence " + querySet + " not found");
            }
            blastDataset.getQuerySequenceLists().add(sequenceList);
        }

        for (final String databaseSet : databaseSets) {
            final AbstractSeqList<BasicByteSeq<ByteAlphabet>> sequenceList = dbUtil
                    .findByAttribute(AbstractSeqList.class, CommonProperties.NAME, databaseSet);
            if (sequenceList == null) {
                throw new UncheckedException("AbstractMultiSequence " + databaseSet + " not found");
            }
            blastDataset.getDatabaseSequenceLists().add(sequenceList);
        }

        blastDataset.setProp(BlastProperties.MAX_TARGET_SEQS, new LongProperty(maxTargetSeqs));

        entityManager.persist(blastDataset);

        createBlastTasks(blastDataset, taskSize);

        return blastDataset;
    }

    /**
     * Delete dataset.
     *
     * @param datasetName
     *            the dataset name
     */
    @Transactional
    public void deleteDataset(final String datasetName) {
        final BlastDataSet blastDataset = dbUtil.findByAttribute(BlastDataSet.class, CommonProperties.NAME,
                datasetName);

        if (blastDataset == null) {
            throw new UncheckedException("Blast dataset not found");
        }

        entityManager.remove(blastDataset);
    }

    /**
     * Creates the blast tasks.
     *
     * @param blastDataset
     *            the blast dataset
     * @param taskSize
     *            the task size
     * @return the blast data set
     */
    @Transactional
    public BlastDataSet createBlastTasks(final BlastDataSet blastDataset, final int taskSize) {

        logger.info("Creating blast tasks for blast dataset " + blastDataset.getId());

        for (final AbstractSeqList<BasicByteSeq<ByteAlphabet>> databaseSequenceList : blastDataset
                .getDatabaseSequenceLists()) {

            for (final AbstractSeqList<BasicByteSeq<ByteAlphabet>> querySequenceList : blastDataset
                    .getQuerySequenceLists()) {

                logger.info("Database BasicSequence Set: " + databaseSequenceList.getProp(CommonProperties.NAME));
                logger.info("Query BasicSequence Set: " + querySequenceList.getProp(CommonProperties.NAME));

                BlastTask blastTask = null;

                final int sequenceListSize = querySequenceList.size();
                for (int i = 0; i < sequenceListSize; i += taskSize) {

                    blastTask = new BlastTask();
                    blastTask.setStatus(BlastTaskStatus.READY);
                    blastTask.setDatabaseSequences(databaseSequenceList);

                    int endPos = sequenceListSize;
                    if (i + taskSize < sequenceListSize) {
                        endPos = i + taskSize;
                    }

                    final AbstractSeqList<BasicByteSeq<ByteAlphabet>> querySublist = new SeqSubList<>(querySequenceList,
                            i, endPos);

                    blastTask.setQuerySequences(querySublist);

                    blastDataset.add(blastTask);

                    // blastTask.getQuerySequenceList().addSequence(rawData);

                }
            }
        }

        entityManager.persist(blastDataset);

        return blastDataset;

    }

    /**
     * Gets the next blast task.
     *
     * @return the next blast task
     */
    @Transactional
    public BlastTask getNextBlastTask() {
        try {
            final TypedQuery<BlastTask> query = entityManager.createQuery("select blastTask from "
                    + BlastTask.class.getName() + " as blastTask where blastTask.status = :status", BlastTask.class);
            query.setParameter("status", BlastTaskStatus.READY);
            query.setMaxResults(1);

            final BlastTask blastTask = query.getSingleResult();

            logger.info("Found blast run: " + blastTask.getId());

            blastTask.setStatus(BlastTaskStatus.RUNNING);

            return blastTask;
        } catch (final NoResultException e) {
            logger.info("No more blast runs available");
            return null;
        }
    }

    /**
     * Update blast task status.
     *
     * @param blastTaskId
     *            the blast task id
     * @param newStatus
     *            the new status
     */
    @Transactional
    public void updateBlastTaskStatus(final long blastTaskId, final BlastTaskStatus newStatus) {
        final BlastTask blastTask = entityManager.find(BlastTask.class, blastTaskId);
        blastTask.setStatus(newStatus);
    }

    /**
     * Run blast.
     *
     * @param blastTaskId
     *            the blast task id
     */
    public void runBlast(final long blastTaskId) {

        logger.info("Running blast for blastTaskId = " + blastTaskId);
        try {

            logger.info("Preparing Blast Execution for blastTaskId = " + blastTaskId);
            final BlastExecution blastExecution = prepareBlastExecution(blastTaskId);

            logger.info("Executing blast for blastTaskId = " + blastTaskId);
            executeBlast(blastExecution);

            logger.info("Parsing results for blastTaskId = " + blastTaskId);
            final InputStream resultStream = new FileInputStream(blastExecution.getResultFile());
            // Read and Parse results
            parseResults(blastTaskId, resultStream);
            resultStream.close();

            logger.info("Updating status for blastTaskId = " + blastTaskId);
            updateBlastTaskStatus(blastTaskId, BlastTaskStatus.DONE);

            logger.info("Finished blast for blastTaskId = " + blastTaskId);
        } catch (final IOException e) {
            throw new UncheckedException("IOException", e);
        }
    }

    /**
     * Prepare blast execution.
     *
     * @param blastTaskId
     *            the blast task id
     * @return the blast execution
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Transactional
    public BlastExecution prepareBlastExecution(final long blastTaskId) throws IOException {
        logger.info("Entering prepareBlastExecution(), blastTaskId = " + blastTaskId);

        final BlastTask blastTask = entityManager.find(BlastTask.class, blastTaskId);

        // Get the sequences that we're going to use for the blast
        final AbstractSeqList<BasicByteSeq<ByteAlphabet>> querySequenceList = blastTask.getQuerySequences();
        final AbstractSeqList<BasicByteSeq<ByteAlphabet>> dbSequenceList = blastTask.getDatabaseSequences();

        // Write query sequences out to File
        final File queryFile = blastFileService.getFastaFile(querySequenceList);
        // Create database from database sequences
        final File dbFile = blastFileService.getBlastDatabase(dbSequenceList);

        final File outputFile = File.createTempFile("blastoutput-" + querySequenceList.getId() + "-", ".xml",
                new File(datafilesPath));

        final String program = blastPath + blastTask.getBlastDataSet().getProgram().toString().toLowerCase();

        final Long numHits = blastTask.getBlastDataSet().getProp(BlastProperties.MAX_TARGET_SEQS, LongProperty.class)
                .getLong();

        final String[] params = new String[] { program, "-outfmt", "5", "-db",
                // dbFile.getAbsolutePath(),
                "nr", "-query", queryFile.getAbsolutePath(), "-max_target_seqs", numHits.toString(), "-out",
                outputFile.getAbsolutePath() };

        logger.info("Leaving prepareBlastExecution(), blastTaskId = " + blastTaskId);
        return new BlastExecution(params, outputFile);
    }

    /**
     * Execute blast.
     *
     * @param blastExecution
     *            the blast execution
     */
    public void executeBlast(final BlastExecution blastExecution) {
        logger.info("Entering executeBlast()");
        final int exitCode = ProcessUtil.executeProcessWithExitCode(blastExecution.getParams());

        if (exitCode != 0) {
            throw new UncheckedException("Blast run failed");
        }
        logger.info("Leaving executeBlast()");
    }

    /**
     * Parses the results.
     *
     * @param blastTaskId
     *            the blast task id
     * @param blastOutputStream
     *            the blast output stream
     */
    @Transactional
    public void parseResults(final long blastTaskId, final InputStream blastOutputStream) {

        logger.info("Entering parseResults(), blastTaskId = " + blastTaskId);

        logger.info("Parsing blast results for blastTask " + blastTaskId);

        final BlastTask blastTask = entityManager.find(BlastTask.class, blastTaskId);

        final BlastOutput blastOutput = blastOutputParser.parseResults(blastOutputStream, sequenceResolver);

        entityManager.persist(blastOutput);

        blastTask.setBlastOutput(blastOutput);

        logger.info("Leaving parseResults(), blastTaskId = " + blastTaskId);
    }
}
