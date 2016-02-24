package org.biomojo.io.fast5;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.io.SeqOutput;
import org.biomojo.sequence.FastqSeq;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class Fast5Output<A extends Nucleotide<?>, Q extends ByteQuality> implements SeqOutput<FastqSeq<A, Q>> {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(Fast5Output.class);

    @Override
    public void close() throws IOException {
    }

    @Override
    public <X extends FastqSeq<A, Q>> void write(final X sequence) throws UncheckedIOException {
    }
}
