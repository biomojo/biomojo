TEST_CASES = [
// {
// LIBRARY : "BIOPYTHON"
// }, {
// LIBRARY : "BIOPERL"
// }, {
// LIBRARY : "HTSEQ"
// }, {
// LIBRARY : "SEQAN",
// ENCODED : true
// }, {
// LIBRARY : "SEQAN",
// ENCODED : false
// },

// {
// LIBRARY : "BIOJAVA",
// SHRINK_HEAP : true
// },

// {
// LIBRARY : "HTSJDK",
// SHRINK_HEAP : true
// },
//
// {
// LIBRARY : "JEBL",
// SHRINK_HEAP : true
// },
//
// {
// LIBRARY : "BIOMOJO",
// ENCODED : true,
// SHRINK_HEAP : true
// },

{
	LIBRARY : "BIOMOJO",
	ENCODED : false,
	SHRINK_HEAP : true
}

];

benchmark = "load_fasta";

function startBenchmark(benchmarkServices) {

	for (numSequences = 40000; numSequences <= 100000; numSequences += 10000) {
		for (sequenceLength = 5000; sequenceLength <= 20000; sequenceLength += 5000) {
			// for (numSequences = 2000; numSequences <= 30000; numSequences +=
			// 2000) {
			// for (sequenceLength = 2000; sequenceLength <= 30000;
			// sequenceLength += 2000) {

			// for (i = 0; i < 1000; ++i) {
			// numSequences = Math.floor(Math.random() * 29000 + 1000);
			// sequenceLength = Math.floor(Math.random() * 29000 + 1000);

			inputFile = benchmarkServices.createTempFile(WORKING_DIR,
					"testdata_", "fasta");

			totalLength = benchmarkServices
					.createRandomSequenceFileWithFixedLengthSeqs(inputFile,
							numSequences, sequenceLength);

			shuffleArray(TEST_CASES);

			for (i = 0; i < TEST_CASES.length; ++i) {

				runNumber = 0;
				runGroupId = benchmarkServices.newBenchmarkRunGroup(RUN_GROUP);

				testParams = buildParams(benchmark, TEST_CASES[i], runNumber,
						inputFile, numSequences, sequenceLength, totalLength,
						DEFAULT_MAX_HEAP, RUN_GROUP, runGroupId);
				runResult = benchmarkServices.runBenchmark(testParams);

				if (testParams.SHRINK_HEAP == true
						&& runResult.EXIT_STATUS == 0) {

					benchmarkServices.log("runResult.RESIDENT_BYTES = "
							+ runResult.RESIDENT_BYTES);

					done = false;
					lowHeap = 0;
					highHeap = Math.floor(runResult.RESIDENT_BYTES / 1024) * 2;

					while (!done) {

						benchmarkServices.log("highHeap = " + highHeap
								+ ", lowHeap = " + lowHeap);

						runNumber += 1;

						javaHeapSize = Math.round((lowHeap + highHeap) / 2);

						testParams = buildParams(benchmark, TEST_CASES[i],
								runNumber, inputFile, numSequences,
								sequenceLength, totalLength, javaHeapSize,
								RUN_GROUP, runGroupId);

						runResult = benchmarkServices.runBenchmark(testParams);

						if (runResult.EXIT_STATUS == 0) {
							highHeap = javaHeapSize;
						} else if (runResult.EXIT_STATUS == 77) {
							lowHeap = javaHeapSize;
						} else {
							done = true;
						}

						delta = Math.abs(lowHeap - highHeap);
						if (delta / (lowHeap + highHeap) < 0.0001 || delta < 32) {
							done = true;
						}

					}
				}
			}

			benchmarkServices.deleteFile(testParams.INPUT_FILE);
		}
	}
}
