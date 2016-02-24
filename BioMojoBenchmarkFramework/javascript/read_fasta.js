TEST_CASES = [ {
	LIBRARY : "BIOPYTHON"
}, {
	LIBRARY : "BIOPERL"
}, {
	LIBRARY : "HTSEQ"
}, {
	LIBRARY : "SEQAN",
	ENCODED : true
}, {
	LIBRARY : "SEQAN",
	ENCODED : false
}, {
	LIBRARY : "BIOJAVA",
	SHRINK_HEAP : false
}, {
	LIBRARY : "HTSJDK",
	SHRINK_HEAP : false
}, {
	LIBRARY : "JEBL",
	SHRINK_HEAP : false
}, {
	LIBRARY : "BIOMOJO",
	ENCODED : true,
	SHRINK_HEAP : false
}, {
	LIBRARY : "BIOMOJO",
	ENCODED : false,
	SHRINK_HEAP : false
} ];

BENCHMARK = "read_fasta";

function startBenchmark(benchmarkServices) {
	for (numSequences = 1000; numSequences <= 2000; numSequences += 1000) {
		for (sequenceLength = 1000; sequenceLength <= 2000; sequenceLength += 1000) {

			inputFile = benchmarkServices.createTempFile(WORKING_DIR,
					"testdata_", "fasta");

			totalLength = benchmarkServices
					.createRandomSequenceFileWithRandomLengthSeqs(inputFile,
							numSequences, sequenceLength);

			// for (runNumber = 0; runNumber < 5; ++runNumber) {
			runNumber = 0;
			shuffleArray(TEST_CASES);
			for (i = 0; i < TEST_CASES.length; ++i) {

				testParams = initTestParams(BENCHMARK, TEST_CASES[i], runNumber)

				testParams.INPUT_FILE = inputFile;
				testParams.NUM_SEQUENCES = numSequences;
				testParams.SEQUENCE_LENGTH = sequenceLength;
				testParams.TOTAL_LENGTH = totalLength;

				jvmArgs = JVM_ARGS;
				
				if (testParams.JVM_HEAP_SIZE != null) {
					javaHeapSizeOption = "-Xmx" + JVM_HEAP_SIZE + "k";
					jvmArgs = jvmArgs.concat(javaHeapSizeOption);
				}
				
				testParams.COMMAND_LINE = buildCommandLine(testParams);

				exitStatus = benchmarkServices.runBenchmark(testParams);
			}
			// }

			benchmarkServices.deleteFile(testParams.INPUT_FILE);
		}
	}
}