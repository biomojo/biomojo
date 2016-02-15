package org.biomojo.benchmark.framework.tests;

import java.io.File;

import org.biomojo.benchmark.framework.RandomFastaGenerator;

public class RandomFastaPreparer extends SequencesPreparer {
    @Override
    protected void createFile(final Integer numSeqs, final Integer seqLength, final File inputFile) {
        new RandomFastaGenerator().createFile(inputFile, numSeqs, seqLength);
    }
}
