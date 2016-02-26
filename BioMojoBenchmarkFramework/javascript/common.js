"use strict";

var DEFAULT_MAX_HEAP_KB = 134217728; // 128GiB in KiB
var DEFAULT_MAX_HEAP_KB = 4000000;

var BASE_JVM_OPTS = [ "-XX:-UseCompressedOops", "-XX:+UseG1GC", "-Dbiomojo.validateInputSeqs=false" ];

var WORKING_DIR = "/data/bio/BioMojo/";
var EXECUTABLE_BASE_DIR = "/home/hugh/code/biobench/";

var RUN_GROUP = new Date().getTime();

var EXECUTABLES = {
	BIOJAVA : "BioJavaBenchmarks/target/BioJavaBenchmarks.jar",
	BIOMOJO : "BioMojoBenchmarks/target/BioMojoBenchmarks.jar",
	JEBL : "JeblBenchmarks/target/JeblBenchmarks.jar",
	HTSJDK : "HTSJDKBenchmarks/target/HTSJDKBenchmarks.jar",
	TRIMMOMATIC : "Trimmomatic/trimmomatic-0.33.jar",
	HTSEQ : "HTSeqBenchmarks/main.py",
	BIOPYTHON : "BioPythonBenchmarks/main.py",
	SEQAN : "SeqAnBenchmarks/Release/SeqAnBenchmarks",
	BIOPERL : "BioPerlBenchmarks/main.pl"
};

function buildJVMOpts(baseJVMOpts, heapKilobytes) {
	return baseJVMOpts.concat("-Xmx" + heapKilobytes + "k", "-Xms"
			+ heapKilobytes + "k");
}

/**
 * From:
 * http://stackoverflow.com/questions/2450954/how-to-randomize-shuffle-a-javascript-array
 * 
 * Randomize array element order in-place. Using Durstenfeld shuffle algorithm.
 */
function shuffleArray(array) {
	for (var i = array.length - 1; i > 0; i--) {
		var j = Math.floor(Math.random() * (i + 1));
		var temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	return array;
}


function buildParams(benchmark, testCase, runNumber, inputFile,
		numSequences, sequenceLength, totalLength, javaHeapSizeKb, runGroup, runGroupId, baseJVMOpts) {
	var testParams = {};

	for ( var prop in testCase) {
		testParams[prop] = testCase[prop];
	}

	testParams.RUN_GROUP = runGroup;
	testParams.RUN_GROUP_ID = runGroupId;
	testParams.BENCHMARK = benchmark;
	testParams.INPUT_FILE = inputFile;
	testParams.NUM_SEQUENCES = numSequences;
	testParams.SEQUENCE_LENGTH = sequenceLength;
	testParams.TOTAL_LENGTH = totalLength;
	
	testParams.RUN_NUMBER = runNumber;
	testParams.JVM_OPTS = buildJVMOpts(baseJVMOpts, javaHeapSizeKb);
	testParams.JAVA_HEAP_SIZE = javaHeapSizeKb * 1024;
	testParams.COMMAND_LINE = buildCommandLine(testParams);
	return testParams;
}

function updateParams(runNumber, testParams, javaHeapSizeKb, baseJVMOpts) {
	testParams.RUN_NUMBER = runNumber;
	testParams.JVM_OPTS = buildJVMOpts(baseJVMOpts, javaHeapSizeKb);
	testParams.JAVA_HEAP_SIZE = javaHeapSizeKb * 1024;
	testParams.COMMAND_LINE = buildCommandLine(testParams);
	return testParams;
}

function buildCommandLine(testParams) {

	var commandLine = [];
	var library = testParams.LIBRARY;

	switch (library) {
	case "BIOJAVA":
	case "JEBL":
	case "HTSJDK":
	case "BIOMOJO":
		commandLine = commandLine.concat("java", testParams.JVM_OPTS, "-jar",
				EXECUTABLE_BASE_DIR + EXECUTABLES[library],
				testParams.BENCHMARK, "-i", testParams.INPUT_FILE);
		if (testParams.ENCODED != null) {
			commandLine = commandLine.concat("-e", testParams.ENCODED);
		}
		if (testParams.OUTPUT_FILE != null) {
			commandLine = commandLine.concat("-o", testParams.OUTPUT_FILE);
		}
		if (testParams.TRIM_QUALITY_SCORE != null) {
			commandLine = commandLine.concat("-q",
					testParams.TRIM_QUALITY_SCORE);
		}
		if (testParams.KMER_LENGTH != null) {
			commandLine = commandLine.concat("-k", testParams.KMER_LENGTH);
		}
		break;
	case "TRIMMOMATIC":
		commandLine = commandLine.concat("java", testParams.JVM_OPTS, "-jar",
				EXECUTABLE_BASE_DIR + EXECUTABLES[library], "SE", "-phred33",
				testParams.INPUT_FILE, testParams.OUTPUT_FILE, "TRAILING:"
						+ testParams.TRIM_QUALITY_SCORE);
		break;
	case "BIOPYTHON":
	case "HTSEQ":
		commandLine = commandLine.concat("python", EXECUTABLE_BASE_DIR
				+ EXECUTABLES[library], testParams.BENCHMARK,
				testParams.INPUT_FILE);
		break;
	case "BIOPERL":
		commandLine = commandLine.concat("perl", "-I", EXECUTABLE_BASE_DIR
				+ "BioPerlBenchmarks", EXECUTABLE_BASE_DIR
				+ EXECUTABLES[library], testParams.BENCHMARK,
				testParams.INPUT_FILE);
		break;
	case "SEQAN":
		commandLine = commandLine.concat(EXECUTABLE_BASE_DIR
				+ EXECUTABLES[library], testParams.BENCHMARK,
				testParams.ENCODED, testParams.INPUT_FILE);

		break;

	}

	// add optional params for the non-java libraries
	switch (library) {
	case "BIOPERL":
	case "BIOPYTHON":
	case "HTSEQ":
	case "SEQAN":
		if (testParams.OUTPUT_FILE != null) {
			commandLine = commandLine.concat(testParams.OUTPUT_FILE);
		}
		if (testParams.TRIM_QUALITY_SCORE != null) {
			commandLine = commandLine.concat(testParams.TRIM_QUALITY_SCORE);
		}
		if (testParams.KMER_LENGTH != null) {
			commandLine = commandLine.concat(testParams.KMER_LENGTH);
		}
	}

	return commandLine;
}


function simulatedDataBenchmark(benchmarkServices, benchmark, testCases, fileType) {
	
	for (var numSequences = 50000; numSequences <= 200000; numSequences += 10000) {
		for (var sequenceLength = 5000; sequenceLength <= 40000; sequenceLength += 5000) {
			// for (numSequences = 2000; numSequences <= 30000; numSequences +=
			// 2000) {
			// for (sequenceLength = 2000; sequenceLength <= 30000;
			// sequenceLength += 2000) {

			// for (i = 0; i < 1000; ++i) {
			// numSequences = Math.floor(Math.random() * 29000 + 1000);
			// sequenceLength = Math.floor(Math.random() * 29000 + 1000);

			var inputFile = benchmarkServices.createTempFile(WORKING_DIR,
					"testdata_", fileType);

			var totalLength = benchmarkServices
					.createRandomSequenceFileWithFixedLengthSeqs(inputFile,
							numSequences, sequenceLength);

			shuffleArray(testCases);

			for (var i = 0; i < testCases.length; ++i) {

				var runGroupId = benchmarkServices.newBenchmarkRunGroup(RUN_GROUP);

				var testParams = buildParams(benchmark, testCases[i], 0,
						inputFile, numSequences, sequenceLength, totalLength,
						DEFAULT_MAX_HEAP_KB, RUN_GROUP, runGroupId, BASE_JVM_OPTS);
				
				var runResult = benchmarkServices.runBenchmark(testParams);

				if (testParams.SHRINK_HEAP == true
						&& runResult.EXIT_STATUS == 0) {
					shrinkHeap(benchmarkServices, testParams, runResult.RESIDENT_BYTES);
				}
			}

			benchmarkServices.deleteFile(testParams.INPUT_FILE);
		}
	}
}

function shrinkHeap(benchmarkServices, testParams, startingHeapSizeBytes) {
	benchmarkServices.log("Running shrink heap. startingHeapSize = "
			+ startingHeapSizeBytes);

	var done = false;
	var lowHeap = 0;
	var highHeap = Math.floor(startingHeapSizeBytes / 1024) * 2;
	var runNumber = 0;
	
	while (!done) {

		benchmarkServices.log("Running: highHeap = " + highHeap
				+ ", lowHeap = " + lowHeap);

		runNumber += 1;

		var javaHeapSizeKb = Math.round((lowHeap + highHeap) / 2);

		var testParams = updateParams(runNumber, testParams, javaHeapSizeKb, BASE_JVM_OPTS)

		var runResult = benchmarkServices.runBenchmark(testParams);

		if (runResult.EXIT_STATUS == 0) {
			highHeap = javaHeapSizeKb;
		} else if (runResult.EXIT_STATUS == 77) {
			lowHeap = javaHeapSizeKb;
		} else {
			done = true;
		}

		var delta = Math.abs(lowHeap - highHeap);
		if (delta / (lowHeap + highHeap) < 0.0001 || delta < 32) {
			done = true;
		}

	}
}
