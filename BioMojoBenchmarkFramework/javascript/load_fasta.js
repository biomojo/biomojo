"use strict";

function startBenchmark(benchmarkServices) {

	var testCases = [ {
		LIBRARY : "BIOPYTHON",
		SHRINK_HEAP : false
	}, {
		LIBRARY : "BIOPERL",
		SHRINK_HEAP : false
	}, {
		LIBRARY : "HTSEQ",
		SHRINK_HEAP : false
	}, {
		LIBRARY : "SEQAN",
		SHRINK_HEAP : false,
		ENCODED : true
	}, {
		LIBRARY : "SEQAN",
		SHRINK_HEAP : false,
		ENCODED : false
	}, 
//	{
//		LIBRARY : "BIOJAVA",
//		SHRINK_HEAP : true
//	}, 
	{
		LIBRARY : "HTSJDK",
		SHRINK_HEAP : true
	}, {
		LIBRARY : "JEBL",
		SHRINK_HEAP : true
	}, {
		LIBRARY : "BIOMOJO",
		ENCODED : true,
		SHRINK_HEAP : true
	}, {
		LIBRARY : "BIOMOJO",
		ENCODED : false,
		SHRINK_HEAP : true
	}

	];

	simulatedDataBenchmark(benchmarkServices, "load_fasta", testCases, "fasta")
}
