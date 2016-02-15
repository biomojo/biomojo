package org.biomojo.benchmark.framework.tests;

import static org.biomojo.benchmark.framework.executor.Benchmark.ALIGN;
import static org.biomojo.benchmark.framework.executor.Benchmark.COUNT_KMERS;
import static org.biomojo.benchmark.framework.executor.Benchmark.LOAD_FASTA;
import static org.biomojo.benchmark.framework.executor.Benchmark.LOAD_FASTQ;
import static org.biomojo.benchmark.framework.executor.Benchmark.READ_FASTA;
import static org.biomojo.benchmark.framework.executor.Benchmark.READ_FASTQ;
import static org.biomojo.benchmark.framework.executor.Benchmark.TRANSLATE;
import static org.biomojo.benchmark.framework.executor.Benchmark.TRIM;
import static org.biomojo.benchmark.framework.executor.Library.BIOJAVA;
import static org.biomojo.benchmark.framework.executor.Library.BIOMOJO;
import static org.biomojo.benchmark.framework.executor.Library.BIOPERL;
import static org.biomojo.benchmark.framework.executor.Library.BIOPYTHON;
import static org.biomojo.benchmark.framework.executor.Library.HTSEQ;
import static org.biomojo.benchmark.framework.executor.Library.HTSJDK;
import static org.biomojo.benchmark.framework.executor.Library.JEBL;
import static org.biomojo.benchmark.framework.executor.Library.SEQAN;
import static org.biomojo.benchmark.framework.executor.Library.TRIMMOMATIC;
import static org.biomojo.benchmark.framework.tests.ConfigParams.ENCODED;
import static org.biomojo.benchmark.framework.tests.ConfigParams.LIBRARY;

import org.biomojo.benchmark.framework.executor.Benchmark;
import org.biomojo.benchmark.framework.executor.Library;
import org.java0.collection.setmap.HashSetMap;
import org.java0.collection.setmap.SetMap;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class TestBuilder {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(TestBuilder.class);

    private static SetMap<Benchmark, Operation> inputPreparers = new HashSetMap<>();

    static {
        inputPreparers.add(READ_FASTA, new RandomFastaPreparer());
        inputPreparers.add(LOAD_FASTA, new RandomFastaPreparer());
        inputPreparers.add(READ_FASTQ, new RandomFastqPreparer());
        inputPreparers.add(LOAD_FASTQ, new RandomFastqPreparer());
        inputPreparers.add(READ_FASTA, new InputFilePreparer());
        inputPreparers.add(LOAD_FASTA, new InputFilePreparer());
        inputPreparers.add(READ_FASTQ, new InputFilePreparer());
        inputPreparers.add(LOAD_FASTQ, new InputFilePreparer());
        inputPreparers.add(TRIM, new InputFilePreparer());
        inputPreparers.add(ALIGN, new InputFilePreparer());
        inputPreparers.add(COUNT_KMERS, new InputFilePreparer());
        inputPreparers.add(TRANSLATE, new InputFilePreparer());
    }

    private static SetMap<Benchmark, Operation> outputPreparers = new HashSetMap<>();

    static {
        outputPreparers.add(TRIM, new OutputPreparer("fastq"));
        outputPreparers.add(ALIGN, new OutputPreparer("txt"));
        outputPreparers.add(COUNT_KMERS, new OutputPreparer("txt"));
        outputPreparers.add(TRANSLATE, new OutputPreparer("fasta"));
    }

    private static SetMap<Library, Operation> commandLinePreparers = new HashSetMap<>();

    static {
        commandLinePreparers.add(BIOJAVA, new JavaMapper("java", "BioJavaBenchmarks.jar", "BioJavaBenchmarks/target"));
        commandLinePreparers.add(BIOMOJO, new JavaMapper("java", "BioMojoBenchmarks.jar", "BioMojoBenchmarks/target"));
        commandLinePreparers.add(BIOPERL, new PerlMapper("perl", "main.pl", "BioPerlBenchmarks"));
        commandLinePreparers.add(BIOPYTHON, new GenericMapper("python", "main.py", "BioPythonBenchmarks"));
        commandLinePreparers.add(HTSEQ, new GenericMapper("python", "main.py", "HTSeqBenchmarks"));
        commandLinePreparers.add(HTSJDK, new JavaMapper("java", "HTSJDKBenchmarks.jar", "HTSJDKBenchmarks/target"));
        commandLinePreparers.add(JEBL, new JavaMapper("java", "JeblBenchmarks.jar", "JeblBenchmarks/target"));
        commandLinePreparers.add(SEQAN, new CppMapper("", "SeqAnBenchmarks", "SeqAnBenchmarks/Release"));
        commandLinePreparers.add(TRIMMOMATIC, new TrimmomaticMapper("java", "trimmomatic-0.33.jar", "Trimmomatic"));
    }

    private static SetMap<Benchmark, Operation> testCases = new HashSetMap<>();

    static {
        testCases.add(LOAD_FASTA, new ParamSetter(LIBRARY, BIOJAVA));
        testCases.add(LOAD_FASTA, new ParamSetter(LIBRARY, BIOMOJO));
        testCases.add(LOAD_FASTA, new ParamSetter(LIBRARY, BIOMOJO, ENCODED, true));
        testCases.add(LOAD_FASTA, new ParamSetter(LIBRARY, BIOPERL));
        testCases.add(LOAD_FASTA, new ParamSetter(LIBRARY, BIOPYTHON));
        testCases.add(LOAD_FASTA, new ParamSetter(LIBRARY, HTSEQ));
        testCases.add(LOAD_FASTA, new ParamSetter(LIBRARY, HTSJDK));
        testCases.add(LOAD_FASTA, new ParamSetter(LIBRARY, JEBL));
        testCases.add(LOAD_FASTA, new ParamSetter(LIBRARY, SEQAN));
        testCases.add(LOAD_FASTA, new ParamSetter(LIBRARY, SEQAN, ENCODED, true));

        testCases.add(LOAD_FASTQ, new ParamSetter(LIBRARY, BIOJAVA));
        testCases.add(LOAD_FASTQ, new ParamSetter(LIBRARY, BIOMOJO));
        testCases.add(LOAD_FASTQ, new ParamSetter(LIBRARY, BIOMOJO, ENCODED, true));
        testCases.add(LOAD_FASTQ, new ParamSetter(LIBRARY, BIOPERL));
        testCases.add(LOAD_FASTQ, new ParamSetter(LIBRARY, BIOPYTHON));
        testCases.add(LOAD_FASTQ, new ParamSetter(LIBRARY, HTSEQ));
        testCases.add(LOAD_FASTQ, new ParamSetter(LIBRARY, HTSJDK));
        testCases.add(LOAD_FASTQ, new ParamSetter(LIBRARY, SEQAN));
        testCases.add(LOAD_FASTQ, new ParamSetter(LIBRARY, SEQAN, ENCODED, true));

        testCases.add(READ_FASTA, new ParamSetter(LIBRARY, BIOJAVA));
        testCases.add(READ_FASTA, new ParamSetter(LIBRARY, BIOMOJO));
        testCases.add(READ_FASTA, new ParamSetter(LIBRARY, BIOMOJO, ENCODED, true));
        testCases.add(READ_FASTA, new ParamSetter(LIBRARY, BIOPERL));
        testCases.add(READ_FASTA, new ParamSetter(LIBRARY, BIOPYTHON));
        testCases.add(READ_FASTA, new ParamSetter(LIBRARY, HTSEQ));
        testCases.add(READ_FASTA, new ParamSetter(LIBRARY, HTSJDK));
        testCases.add(READ_FASTA, new ParamSetter(LIBRARY, JEBL));
        testCases.add(READ_FASTA, new ParamSetter(LIBRARY, SEQAN));
        testCases.add(READ_FASTA, new ParamSetter(LIBRARY, SEQAN, ENCODED, true));

        testCases.add(READ_FASTQ, new ParamSetter(LIBRARY, BIOJAVA));
        testCases.add(READ_FASTQ, new ParamSetter(LIBRARY, BIOMOJO));
        testCases.add(READ_FASTQ, new ParamSetter(LIBRARY, BIOMOJO, ENCODED, true));
        testCases.add(READ_FASTQ, new ParamSetter(LIBRARY, BIOPERL));
        testCases.add(READ_FASTQ, new ParamSetter(LIBRARY, BIOPYTHON));
        testCases.add(READ_FASTQ, new ParamSetter(LIBRARY, HTSEQ));
        testCases.add(READ_FASTQ, new ParamSetter(LIBRARY, HTSJDK));
        testCases.add(READ_FASTQ, new ParamSetter(LIBRARY, SEQAN));
        testCases.add(READ_FASTQ, new ParamSetter(LIBRARY, SEQAN, ENCODED, true));

        testCases.add(TRIM, new ParamSetter(LIBRARY, BIOJAVA));
        testCases.add(TRIM, new ParamSetter(LIBRARY, BIOMOJO));
        testCases.add(TRIM, new ParamSetter(LIBRARY, BIOMOJO, ENCODED, true));
        testCases.add(TRIM, new ParamSetter(LIBRARY, BIOPERL));
        testCases.add(TRIM, new ParamSetter(LIBRARY, BIOPYTHON));
        testCases.add(TRIM, new ParamSetter(LIBRARY, HTSEQ));
        testCases.add(TRIM, new ParamSetter(LIBRARY, HTSJDK));
        testCases.add(TRIM, new ParamSetter(LIBRARY, SEQAN));
        testCases.add(TRIM, new ParamSetter(LIBRARY, SEQAN, ENCODED, true));
        testCases.add(TRIM, new ParamSetter(LIBRARY, TRIMMOMATIC));

        testCases.add(TRANSLATE, new ParamSetter(LIBRARY, BIOJAVA));
        testCases.add(TRANSLATE, new ParamSetter(LIBRARY, BIOMOJO));
        testCases.add(TRANSLATE, new ParamSetter(LIBRARY, BIOMOJO, ENCODED, true));
        testCases.add(TRANSLATE, new ParamSetter(LIBRARY, BIOPERL));
        testCases.add(TRANSLATE, new ParamSetter(LIBRARY, BIOPYTHON));
        testCases.add(TRANSLATE, new ParamSetter(LIBRARY, SEQAN));
        testCases.add(TRANSLATE, new ParamSetter(LIBRARY, SEQAN, ENCODED, true));
        testCases.add(TRANSLATE, new ParamSetter(LIBRARY, JEBL));

        testCases.add(ALIGN, new ParamSetter(LIBRARY, BIOJAVA));
        testCases.add(ALIGN, new ParamSetter(LIBRARY, BIOMOJO));
        testCases.add(ALIGN, new ParamSetter(LIBRARY, BIOMOJO, ENCODED, true));
        testCases.add(ALIGN, new ParamSetter(LIBRARY, BIOPYTHON));
        testCases.add(ALIGN, new ParamSetter(LIBRARY, JEBL));
        testCases.add(ALIGN, new ParamSetter(LIBRARY, SEQAN));
        testCases.add(ALIGN, new ParamSetter(LIBRARY, SEQAN, ENCODED, true));

        testCases.add(COUNT_KMERS, new ParamSetter(LIBRARY, SEQAN));
        testCases.add(COUNT_KMERS, new ParamSetter(LIBRARY, BIOMOJO));
        testCases.add(COUNT_KMERS, new ParamSetter(LIBRARY, BIOJAVA));
    }

    public static SetMap<Benchmark, Operation> getInputPreparers() {
        return inputPreparers;
    }

    public static SetMap<Benchmark, Operation> getOutputPreparers() {
        return outputPreparers;
    }

    public static SetMap<Library, Operation> getCommandLinePreparers() {
        return commandLinePreparers;
    }

    public static SetMap<Benchmark, Operation> getTestCases() {
        return testCases;
    }

}
