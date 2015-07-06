package org.biomojo.examples;

import javax.inject.Named;

@Named
public class AssemblyStats {
	//
	// private final static Logger logger =
	// LoggerFactory.getLogger(AssemblyStats.class
	// .getName());
	//
	// @Inject
	// private DbUtil dbUtil;
	//
	// @Inject
	// private SequenceService sequenceService;
	//
	// @PersistenceContext
	// private EntityManager entityManager;
	//
	// public void calcAssemblyStats(String[] datasetNames,
	// OutputStream outputStream, String type) {
	// PrintStream printStream = null;
	//
	// printStream = new PrintStream(outputStream);
	//
	// for (String datasetName : datasetNames) {
	// if ("basic".equals(type)) {
	// calcBasicStats(datasetName, printStream);
	// } else if ("longest".equals(type)) {
	// calcLongestGenesMapping(datasetName, printStream);
	// } else if ("identity".equals(type)) {
	// calcPercentIdentity(datasetName, printStream);
	// } else if ("coverage80".equals(type)) {
	// listGenesWithCoverage(datasetName, printStream);
	// } else if ("reciprocal".equals(type)) {
	// calcReciprocals(datasetName, printStream);
	// } else if ("blastreport".equals(type)) {
	// blastReport(datasetName, printStream);
	// }
	// }
	//
	// // printStream.close();
	// }
	//
	// @Transactional
	// public void calcBasicStats(String sequenceListName, PrintStream
	// outputStream) {
	// IntList contigLengths = new IntArrayList();
	// long contigLengthsTotal = 0;
	//
	// IntList scaffoldLengths = new IntArrayList();
	// long scaffoldLengthsTotal = 0;
	//
	// logger.info("Retrieving rawData list " + sequenceListName);
	//
	// SeqListI sequenceList = dbUtil.findByAttribute(SeqListI.class,
	// GenericProperties.NAME, sequenceListName);
	//
	// if (sequenceList == null) {
	// throw new CoreException("BasicSequence list " + sequenceListName
	// + " not found");
	// }
	//
	// outputStream.println("\"" + sequenceListName + "\"");
	//
	// // List<BasicSequence> sequences = sequenceList.getSequences(); b bn
	// logger.info("Loading sequences...");
	//
	// // List<Seq> sequences = sequenceService.getSequences(
	// // sequenceList.getId(), 0, sequenceList.size());
	// List<BasicSeqI> sequences = new ArrayList<BasicSeqI>();
	//
	// for (int i = 0; i < sequences.size(); ++i) {
	// BasicSeqI sequence = sequences.get(i);
	//
	// String scaffold = new String(sequence.getAll());
	//
	// scaffoldLengths.add(scaffold.length());
	// scaffoldLengthsTotal += scaffold.length();
	//
	// // if (scaffold.size() < 1000) {
	// // outputStream.println("Scaffold: " + scaffold);
	// // }
	//
	// // break scaffold back into contigs
	// StringTokenizer stringtokenizer = new StringTokenizer(scaffold, "N");
	// while (stringtokenizer.hasMoreElements()) {
	// String contig = stringtokenizer.nextToken();
	// // if (scaffold.size() < 1000) {
	// // outputStream.println("Contig: " + contig);
	// // }
	// if (contig.length() > 0) {
	// contigLengths.add(contig.length());
	// contigLengthsTotal += contig.length();
	// }
	// }
	// }
	//
	// calcN50("Scaffold", scaffoldLengths, scaffoldLengthsTotal, outputStream);
	// calcN50("Contig", contigLengths, contigLengthsTotal, outputStream);
	//
	// outputStream.close();
	// }
	//
	// private void calcN50(String type, IntList lengths, long totalLength,
	// PrintStream outputStream) {
	// int[] sortedLengths = lengths.toIntArray();
	// IntArrays.radixSort(sortedLengths);
	//
	// outputStream.println("");
	// outputStream.println("\"" + type + " Stats\"");
	// outputStream.println("");
	//
	// int n50length = 0;
	// long n50sum = 0;
	// for (int length : sortedLengths) {
	// n50sum += length;
	// if (n50sum > ((float) totalLength / (float) 2.0)) {
	// n50length = length;
	// break;
	// }
	// }
	//
	// outputStream.println("\"Num " + type + "s\", " + sortedLengths.length);
	// outputStream.println("\"Longest " + type + "\", "
	// + sortedLengths[sortedLengths.length - 1]);
	// outputStream.println("\"Shortest " + type + "\", " + sortedLengths[0]);
	// outputStream.println("\"" + type + " N50\", " + n50length);
	// outputStream.println("\"Total length of all " + type + "s\", "
	// + totalLength);
	//
	// int buckets = 1 + sortedLengths[sortedLengths.length - 1] / 1000;
	// int counts[] = new int[buckets];
	//
	// for (int length : sortedLengths) {
	// counts[length / 1000]++;
	// }
	//
	// outputStream.println("");
	// outputStream.println("");
	// outputStream.println("\"" + type + " Length Distribution\"");
	// outputStream.println("");
	//
	// outputStream.println("\"Min. " + type + " Length\", \"Max " + type
	// + " Length\", \"Num. " + type + "s\"");
	//
	// for (int i = 0; i < counts.length; ++i) {
	// outputStream.println(i * 1000 + "," + (((i + 1) * 1000) - 1) + ","
	// + counts[i]);
	//
	// }
	//
	// outputStream.println("");
	// outputStream.println("");
	// outputStream.println("");
	// outputStream.println("");
	// outputStream.println("");
	// outputStream.println("");
	// outputStream.println("");
	//
	// }
	//
	// @Transactional
	// public void calcLongestGenesMapping(String datasetName,
	// PrintStream outputStream) {
	//
	// logger.info("Retrieving blast dataset");
	//
	// BlastDataSet bds = dbUtil.findByAttribute(BlastDataSet.class,
	// GenericProperties.NAME, datasetName);
	//
	// if (bds == null) {
	// logger.error("Dataset " + datasetName + " not found");
	// return;
	// }
	//
	// long sequenceListId = bds.getDatabaseSequenceLists().iterator().next()
	// .getId();
	//
	// logger.info("Retrieving longest sequences for rawData list "
	// + sequenceListId);
	//
	// Query query = entityManager.createQuery("select sequences from "
	// + "AbstractMultiSequence MultiSeq "
	// + "join MultiSeq.sequences sequences "
	// + "where MultiSeq.id = :MultiSeqId "
	// + "order by sequences.sequenceLength desc, "
	// + SeqI.class.getName());
	// query.setParameter("MultiSeqId", sequenceListId);
	// query.setFirstResult(0);
	// query.setMaxResults(25);
	//
	// @SuppressWarnings("unchecked")
	// List<SeqI> longestSequences = query.getResultList();
	//
	// Comparator<Integer> reverseIntComparator = new Comparator<Integer>() {
	// @Override
	// public int compare(Integer o1, Integer o2) {
	// return -o1.compareTo(o2);
	// }
	// };
	//
	// Map<Integer, SeqI> sequencesByLength = new TreeMap<Integer, SeqI>(
	// reverseIntComparator);
	// Map<Long, Map<Long, Set<BlastHsp>>> hspsByDatabaseId = new HashMap<Long,
	// Map<Long, Set<BlastHsp>>>();
	//
	// // for (SequenceRecord sequence : longestSequences) {
	// // logger.info("BasicSequence id = " + sequence.getId()
	// // + ", length = " + sequence.size());
	// // sequencesByLength.put((int) sequence.size(), sequence);
	// // hspsByDatabaseId.put(sequence.getId(),
	// // new HashMap<Long, Set<BlastHsp>>());
	// // }
	//
	// outputStream.println();
	//
	// logger.info("Processing blast tasks");
	// int taskCount = 0;
	// int dbIdSum = 0;
	//
	// for (BlastTask bt : bds.getBlastTasks()) {
	// taskCount++;
	//
	// // if (taskCount > 3) {
	// // break;
	// // }
	//
	// logger.info("Processing blast task #" + taskCount + ", id = "
	// + bt.getId());
	// BlastOutput blastOutput = bt.getBlastOutput();
	//
	// if (blastOutput != null) {
	//
	// for (BlastIteration bi : blastOutput.getIterations()) {
	//
	// for (BlastHit bh : bi.getHits()) {
	//
	// long databaseId = Long.parseLong(bh.getHitDef());
	//
	// // logger.info("databaseId = " + databaseId);
	// dbIdSum += databaseId;
	//
	// Map<Long, Set<BlastHsp>> hspsByQueryId = hspsByDatabaseId
	// .get(databaseId);
	//
	// if (hspsByQueryId != null) {
	//
	// long queryId = Long.parseLong(bh.getIteration()
	// .getQueryDef());
	//
	// logger.info("Found Hsps for database id "
	// + databaseId + ", queryId " + queryId
	// + ", # Hsps = " + bh.getHsps().size());
	//
	// Set<BlastHsp> hsps = hspsByQueryId.get(queryId);
	//
	// if (hsps == null) {
	// hsps = new HashSet<BlastHsp>();
	// hspsByQueryId.put(queryId, hsps);
	// }
	//
	// hsps.addAll(bh.getHsps());
	// }
	// }
	// }
	// } else {
	// logger.info("No blast output for Task " + bt.getId());
	// }
	// }
	//
	// logger.info("dbIdSum = " + dbIdSum);
	//
	// Comparator<BlastHsp> hspComparator = new Comparator<BlastHsp>() {
	// @Override
	// public int compare(BlastHsp o1, BlastHsp o2) {
	// if (o1.getHitFrom() > o2.getHitFrom()) {
	// return 1;
	// } else if (o1.getHitFrom() < o2.getHitFrom()) {
	// return -1;
	// }
	// if (o1.getHitTo() > o2.getHitTo()) {
	// return 1;
	// } else if (o1.getHitTo() < o2.getHitTo()) {
	// return -1;
	// }
	// if (o1.getQueryFrom() > o2.getQueryFrom()) {
	// return 1;
	// } else if (o1.getQueryFrom() < o2.getQueryFrom()) {
	// return -1;
	// }
	// if (o1.getQueryTo() > o2.getQueryTo()) {
	// return 1;
	// } else if (o1.getQueryTo() < o2.getQueryTo()) {
	// return -1;
	// }
	// return 0;
	// }
	// };
	//
	// for (SeqI sequence : sequencesByLength.values()) {
	// outputStream.println("******************");
	// outputStream.println("Hit: (length = " + sequence.size() + "): "
	// + new String(sequence.getDescription()));
	//
	// Map<Long, Set<BlastHsp>> hspsByQueryId = null;
	// // Map<Long, Set<BlastHsp>> hspsByQueryId = hspsByDatabaseId
	// // .get(sequence.getId());
	// Map<Integer, BasicSeqI> queriesByLength = new TreeMap<Integer,
	// BasicSeqI>(
	// reverseIntComparator);
	//
	// for (long queryId : hspsByQueryId.keySet()) {
	// BasicSeqI querySequence = entityManager.find(BasicSeqI.class,
	// queryId);
	// queriesByLength.put((int) querySequence.size(), querySequence);
	// }
	//
	// for (BasicSeqI querySequence : queriesByLength.values()) {
	// Set<BlastHsp> hsps = hspsByQueryId.get(querySequence.getId());
	//
	// // outputStream.println("  Query: (length = "
	// // + querySequence.size() + "): "
	// // + new String(querySequence.getDescription()));
	// //
	// Set<BlastHsp> sortedHsps = new TreeSet<BlastHsp>(hspComparator);
	// sortedHsps.addAll(hsps);
	//
	// for (BlastHsp hsp : sortedHsps) {
	// outputStream.println("    HSP: Hit location (from-to): "
	// + hsp.getHitFrom() + "-" + hsp.getHitTo()
	// + " , Query location (from-to): "
	// + hsp.getQueryFrom() + "-" + hsp.getQueryTo()
	// + ", Alignment Length: " + hsp.getAlignLen()
	// + ", Identities: " + hsp.getIdentity()
	// + ", Positives: " + hsp.getPositive() + ", Gaps: "
	// + hsp.getGaps());
	// }
	// }
	// }
	// }
	//
	// @Transactional
	// public void calcCoverageStatsForDataset(String datasetName,
	// PrintStream outputStream) {
	//
	// Map<Long, BlastHsp> hspsByQueryId = new HashMap<Long, BlastHsp>();
	//
	// BlastDataSet bds = dbUtil.findByAttribute(BlastDataSet.class,
	// GenericProperties.NAME, datasetName);
	// bds.getDatabaseSequenceLists().iterator().next();
	// bds.getQuerySequenceLists().iterator().next();
	//
	// outputStream.print("\"" + "Database" + "\", ");
	// outputStream.print("\"" + "Queries" + "\", ");
	// outputStream.print("\"" + "Parsed Gene Description (1)" + "\", ");
	// outputStream.print("\"" + "Parsed Gene Description (2)" + "\", ");
	// outputStream.print("\"" + "Parsed Gene Description (3)" + "\", ");
	// outputStream.print("\"" + "Gene Description" + "\", ");
	//
	// outputStream.print("\"" + "Contig Description" + "\", " + "\""
	// + "Coverage" + "\", " + "\"" + "Match Length" + "\", ");
	// outputStream.print("\"" + "Query Length" + "\"");
	//
	// outputStream.println();
	//
	// int taskCount = 0;
	// logger.info("Processing blast tasks");
	// for (BlastTask bt : bds.getBlastTasks()) {
	// ++taskCount;
	//
	// if (taskCount > 2) {
	// break;
	// }
	// logger.info("Processing blast task #" + taskCount + " (id="
	// + bt.getId() + ")");
	// for (BlastIteration bi : bt.getBlastOutput().getIterations()) {
	// long queryId = Long.parseLong(bi.getQueryDef());
	// for (BlastHit bh : bi.getHits()) {
	// BlastHsp bestHsp = hspsByQueryId.get(queryId);
	//
	// for (BlastHsp bhsp : bh.getHsps()) {
	// if (bestHsp == null
	// || bestHsp.getBitScore() < bhsp.getBitScore()) {
	// bestHsp = bhsp;
	// }
	// }
	//
	// hspsByQueryId.put(queryId, bestHsp);
	// }
	// }
	// }
	//
	// // sortedHsps.get(sortedHsps.lastKey());
	//
	// Map<Double, BlastHsp> sortedHsps = new TreeMap<Double, BlastHsp>();
	//
	// DoubleList coverages = new DoubleArrayList();
	//
	// logger.info("Calculating coverages");
	// for (Entry<Long, BlastHsp> entry : hspsByQueryId.entrySet()) {
	// BlastHsp bestHsp = entry.getValue();
	// long queryId = Long.parseLong(bestHsp.getHit().getIteration()
	// .getQueryDef());
	// SeqI querySequence = entityManager.find(SeqI.class, queryId);
	// double coverage = ((double) bestHsp.getQueryTo() - (double) bestHsp
	// .getQueryFrom()) / (double) querySequence.size();
	//
	// sortedHsps.put(coverage, bestHsp);
	// coverages.add(coverage);
	// }
	//
	// for (Entry<Double, BlastHsp> entry : sortedHsps.entrySet()) {
	// BlastHsp bestHsp = entry.getValue();
	// Double coverage = entry.getKey();
	// long queryId = Long.parseLong(bestHsp.getHit().getIteration()
	// .getQueryDef());
	// long databaseId = Long.parseLong(bestHsp.getHit().getHitDef());
	// BasicSeqI querySequence = entityManager.find(BasicSeqI.class,
	// queryId);
	// BasicSeqI dbSequence = entityManager.find(BasicSeqI.class,
	// databaseId);
	//
	// String queryDesc = new String(querySequence.getDescription());
	// String dbDesc = new String(dbSequence.getDescription());
	//
	// outputStream.print("\"" + dbDesc + "\", ");
	// outputStream.print("\"" + queryDesc + "\", ");
	//
	// String[] parts = queryDesc.split("\\s+");
	// List<String> partsList = new ArrayList<String>();
	// partsList.addAll(Arrays.asList(parts));
	// partsList.add("");
	// partsList.add("");
	// partsList.add("");
	// outputStream.print("\"" + partsList.get(0) + "\", ");
	// outputStream.print("\"" + partsList.get(1) + "\", ");
	// outputStream.print("\"" + partsList.get(2) + "\", ");
	//
	// outputStream.print("\"" + queryDesc + "\", \"" + dbDesc + "\", "
	// + coverage + ", "
	// + (bestHsp.getQueryTo() - bestHsp.getQueryFrom()) + ", ");
	// outputStream.print(querySequence.getAll().length);
	// outputStream.println();
	// }
	// double[] sortedCoverages = coverages.toDoubleArray();
	// DoubleArrays.radixSort(sortedCoverages);
	//
	// int counts[] = new int[10];
	//
	// for (double coverage : sortedCoverages) {
	// counts[(int) (coverage * 10)]++;
	// }
	//
	// for (int count : counts) {
	// outputStream.println(count);
	// }
	// outputStream.close();
	// }
	//
	// @Transactional
	// public void listGenesWithCoverage(String datasetName,
	// PrintStream outputStream) {
	// BlastDataSet bds = dbUtil.findByAttribute(BlastDataSet.class,
	// GenericProperties.NAME, datasetName);
	//
	// bds.getDatabaseSequenceLists().iterator().next();
	// bds.getQuerySequenceLists().iterator().next();
	//
	// printCSV(outputStream, "query length", "subject length",
	// "query coverage %", "alignment length", "query from",
	// "query to", "hit from", "hit to", "evalue", "bitscore",
	// "query", "subject", "alignment");
	// outputStream.println();
	//
	// int taskCount = 0;
	// logger.info("Processing blast tasks");
	// for (BlastTask bt : bds.getBlastTasks()) {
	// ++taskCount;
	//
	// // if (taskCount > 2) {
	// // break;
	// // }
	// logger.info("Processing blast task #" + taskCount + " (id="
	// + bt.getId() + ")");
	// for (BlastIteration bi : bt.getBlastOutput().getIterations()) {
	// long queryId = Long.parseLong(bi.getQueryDef());
	// SeqI querySequence = entityManager.find(SeqI.class, queryId);
	// for (BlastHit bh : bi.getHits()) {
	// long databaseId = Long.parseLong(bh.getHitDef());
	// SeqI databaseSequence = entityManager.find(SeqI.class,
	// databaseId);
	// for (BlastHsp bhsp : bh.getHsps()) {
	// double coverage = ((double) bhsp.getQueryTo() - (double) bhsp
	// .getQueryFrom())
	// / (double) querySequence.size();
	// if (coverage >= 0.8) {
	// String alignment = new String(bhsp.getQseq())
	// + "\n" + new String(bhsp.getMidline())
	// + "\n" + new String(bhsp.getHseq()) + "\n";
	// printCSV(
	// outputStream,
	// querySequence.size(),
	// databaseSequence.size(),
	// coverage,
	// bhsp.getAlignLen(),
	// bhsp.getQueryFrom(),
	// bhsp.getQueryTo(),
	// bhsp.getHitFrom(),
	// bhsp.getHitTo(),
	// bhsp.getEvalue(),
	// bhsp.getBitScore(),
	// new String(querySequence.getDescription()),
	// new String(databaseSequence
	// .getDescription()), alignment);
	// outputStream.println();
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// @Transactional
	// public void calcPercentIdentity(String datasetName, PrintStream
	// outputStream) {
	// BlastDataSet bds = dbUtil.findByAttribute(BlastDataSet.class,
	// GenericProperties.NAME, datasetName);
	//
	// SeqListI<BasicSeqI> database = bds.getDatabaseSequenceLists()
	// .iterator().next();
	// bds.getQuerySequenceLists().iterator().next();
	//
	// logger.info("Retrieving sequences");
	//
	// List<BasicSeqI> sequences = sequenceService.getSequences(
	// database.getId(), 0, database.size());
	//
	// Map<Long, boolean[]> mappedBySequenceId = new HashMap<Long, boolean[]>();
	//
	// for (BasicSeqI sequence : sequences) {
	// mappedBySequenceId.put(sequence.getId(),
	// new boolean[(int) sequence.size()]);
	// }
	//
	// int taskCount = 0;
	// logger.info("Processing blast tasks");
	// for (BlastTask bt : bds.getBlastTasks()) {
	// ++taskCount;
	//
	// // if (taskCount > 2) {
	// // break;
	// // }
	// logger.info("Processing blast task #" + taskCount + " (id="
	// + bt.getId() + ")");
	// for (BlastIteration bi : bt.getBlastOutput().getIterations()) {
	// Long.parseLong(bi.getQueryDef());
	// // BasicSequence querySequence =
	// // entityManager.find(BasicSequence.class,
	// // queryId);
	// for (BlastHit bh : bi.getHits()) {
	// long databaseId = Long.parseLong(bh.getHitDef());
	// // BasicSequence databaseSequence = entityManager.find(
	// // BasicSequence.class, databaseId);
	//
	// for (BlastHsp bhsp : bh.getHsps()) {
	// // String dbqseq = new
	// // String(querySequence.getSequence(),bhsp.getQueryFrom()
	// // - 1,bhsp.getQueryTo()-bhsp.getQueryFrom()+1);
	// // String dbhseq = new
	// // String(databaseSequence.getSequence(),bhsp.getHitFrom()
	// // - 1,bhsp.getHitTo()-bhsp.getHitFrom()+1);
	// boolean[] mapped = mappedBySequenceId.get(databaseId);
	// // if (mapped == null) {
	// // mapped = new
	// // boolean[databaseSequence.qetSeq().size()];
	// // mappedBySequenceId.put(databaseId, mapped);
	// // }
	//
	// int mapPos = bhsp.getHitFrom() - 1;
	// int sign = 1;
	// if (bhsp.getHitFrame() < 0) {
	// mapPos = bhsp.getHitTo() - 1;
	// sign = -1;
	// }
	//
	// byte[] hseq = bhsp.getHseq();
	// byte[] midline = bhsp.getMidline();
	//
	// for (int i = 0; i < hseq.length; ++i) {
	// if (hseq[i] != '-') {
	// if (midline[i] != ' ' && midline[i] != '+') {
	// mapped[mapPos] = true;
	// mapped[mapPos + (1 * sign)] = true;
	// mapped[mapPos + (2 * sign)] = true;
	// }
	// mapPos += (3 * sign);
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// int mappedCount = 0;
	// int totalCount = 0;
	// for (boolean[] mapped : mappedBySequenceId.values()) {
	// int count = 0;
	// totalCount += mapped.length;
	// for (boolean val : mapped) {
	// if (val) {
	// ++count;
	// }
	// }
	// // System.out.println((double)count/(double)mapped.length);
	// mappedCount += count;
	// }
	//
	// System.out.println((double) mappedCount / (double) totalCount);
	//
	// }
	//
	// @Transactional
	// public void calcReciprocals(String datasetName, PrintStream outputStream)
	// {
	// BlastDataSet bds = dbUtil.findByAttribute(BlastDataSet.class,
	// GenericProperties.NAME, datasetName);
	//
	// if (bds == null) {
	// logger.error("Blastdataset not found");
	// return;
	// }
	//
	// Long2IntMap numGenesByContig = new Long2IntOpenHashMap();
	// Long2IntMap numContigsByGene = new Long2IntOpenHashMap();
	// Long2IntMap totalGenesByGene = new Long2IntOpenHashMap();
	//
	// int taskCount = 0;
	// logger.info("Processing blast tasks");
	// for (BlastTask bt : bds.getBlastTasks()) {
	// ++taskCount;
	// logger.info("Pass 1: Processing blast task #" + taskCount + " (id="
	// + bt.getId() + ")");
	// for (BlastIteration bi : bt.getBlastOutput().getIterations()) {
	// long queryId = Long.parseLong(bi.getQueryDef());
	// SeqI querySequence = entityManager.find(SeqI.class, queryId);
	// for (BlastHit bh : bi.getHits()) {
	// long databaseId = Long.parseLong(bh.getHitDef());
	// for (BlastHsp bhsp : bh.getHsps()) {
	// double coverage = ((double) bhsp.getQueryTo() - (double) bhsp
	// .getQueryFrom())
	// / (double) querySequence.size();
	// if (coverage > 0.8) {
	// numGenesByContig.put(databaseId,
	// numGenesByContig.get(databaseId) + 1);
	// numContigsByGene.put(queryId,
	// numContigsByGene.get(queryId) + 1);
	// }
	// }
	// }
	// }
	// }
	//
	// taskCount = 0;
	// logger.info("Pass 2: Processing blast tasks");
	// for (BlastTask bt : bds.getBlastTasks()) {
	// ++taskCount;
	// logger.info("Processing blast task #" + taskCount + " (id="
	// + bt.getId() + ")");
	// for (BlastIteration bi : bt.getBlastOutput().getIterations()) {
	// long queryId = Long.parseLong(bi.getQueryDef());
	// SeqI querySequence = entityManager.find(SeqI.class, queryId);
	// for (BlastHit bh : bi.getHits()) {
	// long databaseId = Long.parseLong(bh.getHitDef());
	// for (BlastHsp bhsp : bh.getHsps()) {
	// double coverage = ((double) bhsp.getQueryTo() - (double) bhsp
	// .getQueryFrom())
	// / (double) querySequence.size();
	// if (coverage > 0.8) {
	// totalGenesByGene.put(queryId,
	// totalGenesByGene.get(queryId)
	// + numGenesByContig.get(databaseId));
	// }
	// }
	// }
	// }
	// }
	//
	// printCSV(outputStream, "num contigs for gene",
	// "total genes for all contigs", "avg num genes per contig",
	// "avg num genes per contig / num contigs", "gene description");
	//
	// for (Long2IntMap.Entry entry : totalGenesByGene.long2IntEntrySet()) {
	// long queryId = entry.getLongKey();
	// int numContigs = numContigsByGene.get(queryId);
	// int totalGenes = entry.getIntValue();
	// SeqI querySequence = entityManager.find(SeqI.class, queryId);
	// printCSV(outputStream, numContigs, totalGenes,
	// ((double) totalGenes / (double) numContigs),
	// ((double) totalGenes / (double) numContigs) / numContigs,
	// new String(querySequence.getDescription()));
	// outputStream.println();
	// }
	//
	// }
	//
	// @Transactional
	// public void blastReport(String datasetName, PrintStream outputStream) {
	// BlastDataSet bds = dbUtil.findByAttribute(BlastDataSet.class,
	// GenericProperties.NAME, datasetName);
	// bds.getDatabaseSequenceLists().iterator().next();
	// bds.getQuerySequenceLists().iterator().next();
	//
	// Long2LongMap giMap = new Long2LongOpenHashMap();
	//
	// try {
	// BufferedReader input = new BufferedReader(new FileReader(new File(
	// "/home/hugh/tax/filtered.dmp")));
	//
	// for (String line; (line = input.readLine()) != null;) {
	// String[] parsedLine = line.split("\t");
	// long gi = Long.parseLong(parsedLine[0]);
	// long taxId = Long.parseLong(parsedLine[1]);
	// giMap.put(gi, taxId);
	// }
	//
	// input.close();
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// String[] classes = { "superkingdom", "kingdom", "phylum", "superclass",
	// "class", "infraclass", "order", "suborder", "infraorder",
	// "superfamily", "family", "tribe", "subtribe", "genus",
	// "subgenus", "species group", "species subgroup", "species",
	// "subspecies" };
	//
	// for (String cls : classes) {
	// printCSV(outputStream, cls);
	// }
	// printCSV(outputStream, "Hit Accession", "Hit Id", "Species",
	// "Hit Number", "Hsp Number", "Bit Score", "E Value", "Score",
	// "Alignment Length", "Num Identities", "% Identity", "Query",
	// "Hit", "Alignment");
	// outputStream.println();
	//
	// LongSet giList = new LongOpenHashSet();
	//
	// int taskCount = 0;
	// logger.info("Processing blast tasks");
	// for (BlastTask bt : bds.getBlastTasks()) {
	// ++taskCount;
	//
	// // if (taskCount > 2) {
	// // break;
	// // }
	// logger.info("Processing blast task #" + taskCount + " (id="
	// + bt.getId() + ")");
	// for (BlastIteration bi : bt.getBlastOutput().getIterations()) {
	// Long.parseLong(bi.getQueryDef());
	// for (BlastHit bh : bi.getHits()) {
	// for (BlastHsp bhsp : bh.getHsps()) {
	// String alignment = new String(bhsp.getQseq()) + "\r\n"
	// + new String(bhsp.getMidline()) + "\r\n"
	// + new String(bhsp.getHseq()) + "\r\n";
	//
	// int start = bh.getHitDef().indexOf('[');
	// int end = bh.getHitDef().indexOf(']');
	// String species = "N/A";
	// if (start >= 0 && end >= 0) {
	// species = bh.getHitDef().substring(start + 1, end);
	// }
	// String[] parsedHitId = bh.getHitId().split("\\|");
	// long gi = Long.parseLong(parsedHitId[1]);
	// // GiTaxonomy giTaxonomy =
	// // entityManager.find(GiTaxonomy.class, gi);
	// // TaxonomyNode taxonomy = giTaxonomy.getTaxonomy();
	// giList.add(gi);
	// long taxonomyId = giMap.get(gi);
	// TaxonomyNode taxonomyNode = entityManager.find(
	// TaxonomyNode.class, taxonomyId);
	// Map<String, TaxonomyNode> classification = taxonomyNode
	// .getFullClassification();
	// for (String cls : classes) {
	// String name = "";
	// TaxonomyNode node = classification.get(cls);
	// if (node != null) {
	// // name = node.getName();
	// }
	// printCSV(outputStream, name);
	// }
	// printCSV(
	// outputStream,
	// bh.getHitAccession(),
	// bh.getHitId(),
	// species,
	// bh.getHitNum(),
	// bhsp.getHspNum(),
	// bhsp.getBitScore(),
	// bhsp.getEvalue(),
	// bhsp.getScore(),
	// bhsp.getAlignLen(),
	// bhsp.getIdentity(),
	// (float) bhsp.getIdentity()
	// / (float) bhsp.getAlignLen(),
	// new String(bi.getQuerySequence().toString()),
	// bh.getHitDef(), alignment);
	// outputStream.println();
	//
	// }
	// }
	// }
	// }
	//
	// logger.info("Gi list size = " + giList.size());
	// for (long gi : giList) {
	// logger.info(Long.toString(gi));
	// }
	//
	// // try {
	// // BufferedReader input = new BufferedReader(new FileReader(new
	// // File("/home/hugh/tax/gi_taxid_prot.dmp")));
	// // BufferedWriter writer = new BufferedWriter(new FileWriter(new
	// // File("/home/hugh/tax/filtered.dmp")));
	// //
	// // for (String line; (line = input.readLine()) != null;) {
	// // String[] parsedLine = line.split("\t");
	// // long gi = Long.parseLong(parsedLine[0]);
	// // long taxId = Long.parseLong(parsedLine[1]);
	// // if (giList.contains(gi)) {
	// // writer.write(gi + "\t" + taxId + "\n");
	// // }
	// // }
	// //
	// // input.close();
	// // writer.close();
	// // } catch (FileNotFoundException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // } catch (IOException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	//
	// }
	//
	// void printCSV(PrintStream outputStream, Object... args) {
	// for (Object arg : args) {
	// if (arg instanceof Number) {
	// outputStream.print(arg);
	// } else {
	// outputStream.print("\"");
	// outputStream.print(arg);
	// outputStream.print("\"");
	// }
	// outputStream.print(",");
	// }
	// }
}
