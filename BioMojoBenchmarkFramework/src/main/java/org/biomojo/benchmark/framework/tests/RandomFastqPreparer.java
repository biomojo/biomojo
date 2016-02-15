package org.biomojo.benchmark.framework.tests;

import java.io.File;

import org.biomojo.benchmark.framework.RandomFastqGenerator;

public class RandomFastqPreparer extends SequencesPreparer {
    @Override
    protected void createFile(final Integer numSeqs, final Integer seqLength, final File inputFile) {
        new RandomFastqGenerator().createFile(inputFile, numSeqs, seqLength);
    }
}
