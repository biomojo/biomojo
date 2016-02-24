package org.biomojo.tools.general;

import java.io.IOException;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.SeqInput;
import org.biomojo.io.SeqOutput;
import org.biomojo.sequence.ByteSeq;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "slice")
public class SliceCommand extends AbstractFastxCommand {
    private static final Logger logger = LoggerFactory.getLogger(SliceCommand.class.getName());

    @Parameter(names = { "-f", "--fasta" }, description = "Output as FASTA? (otherwise FASTQ)")
    protected boolean fasta = false;

    @Parameter(names = { "-s", "--start" }, description = "Start position")
    protected long start = -1;

    @Parameter(names = { "-e", "--end" }, description = "End position")
    protected long end = -1;

    @Override
    protected void setup() {
    }

    @Override
    protected <A extends ByteAlphabet, T extends ByteSeq<A>> void process(final SeqInput<T> inputStream,
            final SeqOutput<T> outputStream) throws IOException {
        int recordCount = 0;
        for (final T seq : inputStream) {
            ++recordCount;
            final ByteSeq<A> newSeq = seq.subList(getPos(start, seq.sizeL()), getPos(end, seq.sizeL()));
            outputStream.write((T) newSeq);
            if (recordCount % 100 == 0) {
                logger.info("{}", recordCount);
            }
        }
    }

    public long getPos(final long paramPos, final long seqLength) {
        long pos = paramPos;
        if (pos < 0) {
            pos = seqLength + pos;
        }
        if (pos < 0) {
            pos = 0;
        } else if (pos > seqLength) {
            pos = seqLength;
        }
        return pos;
    }

}
