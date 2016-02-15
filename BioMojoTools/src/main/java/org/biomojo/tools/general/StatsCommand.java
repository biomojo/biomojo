package org.biomojo.tools.general;

import java.io.IOException;
import java.io.PrintStream;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.sequence.ByteSeq;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;

@Parameters(commandNames = "stats", commandDescription = "Calculate some basic stats for a FASTX file")
public class StatsCommand extends AbstractFastxFileInputStreamOutputCommand {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(StatsCommand.class);

    @Override
    protected void setup() {
    }

    @Override
    protected <A extends ByteAlphabet, T extends ByteSeq<A>> void process(final SequenceInputStream<T> inputStream,
            final PrintStream printStream) throws IOException {

        long recordCount = 0;
        long totalLength = 0;
        for (final T seq : inputStream) {
            ++recordCount;
            totalLength += seq.size();
        }
        printStream.println("Num seqs: " + recordCount);
        printStream.println("Total length: " + totalLength);
        printStream.format("Avg. length: %.2f", totalLength / (double) recordCount);
    }
}
