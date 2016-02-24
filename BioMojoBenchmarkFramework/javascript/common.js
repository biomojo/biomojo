DEFAULT_MAX_HEAP = 134217728; // 128GiB in KiB
//DEFAULT_MAX_HEAP = 4000000;

BASE_JVM_OPTS = [ "-XX:-UseCompressedOops", "-XX:+UseG1GC", "-Dbiomojo.validateInputSeqs" ];

WORKING_DIR = "/data/bio/BioMojo/";
EXECUTABLE_BASE_DIR = "/home/hugh/code/biobench/";

RUN_GROUP = new Date().getTime();

EXECUTABLES = {
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

function buildJVMOpts(heapKilobytes) {
	return BASE_JVM_OPTS.concat("-Xmx" + heapKilobytes + "k", "-Xms"
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
		numSequences, sequenceLength, totalLength, javaHeapSize, runGroup, runGroupId) {
	var testParams = {};

	for ( var prop in testCase) {
		testParams[prop] = testCase[prop];
	}

	testParams.RUN_GROUP = runGroup;
	testParams.RUN_GROUP_ID = runGroupId;
	testParams.BENCHMARK = benchmark;
	testParams.RUN_NUMBER = runNumber;
	testParams.INPUT_FILE = inputFile;
	testParams.NUM_SEQUENCES = numSequences;
	testParams.SEQUENCE_LENGTH = sequenceLength;
	testParams.TOTAL_LENGTH = totalLength;
	testParams.JVM_OPTS = buildJVMOpts(javaHeapSize);
	testParams.JAVA_HEAP_SIZE = javaHeapSize * 1024;
	testParams.COMMAND_LINE = buildCommandLine(testParams);
	return testParams;
}

function buildCommandLine(testParams) {

	commandLine = [];
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
