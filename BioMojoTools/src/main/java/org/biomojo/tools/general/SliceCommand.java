package org.biomojo.tools.general;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.biomojo.alphabet.DNA;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.io.fastx.FastaOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.java0.cli.InputOutputCommand;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "slice")
public class SliceCommand extends InputOutputCommand {
    private static final Logger logger = LoggerFactory.getLogger(SliceCommand.class.getName());

    @Parameter(names = { "-f", "--fasta" }, description = "Output as FASTA? (otherwise FASTQ)")
    protected boolean fasta = false;

    @Parameter(names = { "-s", "--start" }, description = "Start position")
    protected long start = -1;

    @Parameter(names = { "-e", "--end" }, description = "End position")
    protected long end = -1;

    @Override
    public void run() {

        try (final SequenceInputStream<ByteSeq<DNA>> inputStream = new FastaInputStream<DNA>(
                new FileInputStream(inputFile))) {
            logger.info("Starting {}", fasta);
            if (fasta) {
                try (final SequenceOutputStream<ByteSeq<DNA>> outputStream = new FastaOutputStream<>(
                        new BufferedOutputStream(new FileOutputStream(outputFile)));) {
                    convert(inputStream, outputStream);
                }
            }
        } catch (final IOException e) {
            logger.error("Exception: ", e);
        }

    }

    public <I extends ByteSeq<DNA>> void convert(final SequenceInputStream<I> inputStream,
            final SequenceOutputStream<ByteSeq<DNA>> outputStream) throws IOException {
        int recordCount = 0;
        for (final I seq : inputStream) {
            ++recordCount;
            final ByteSeq<DNA> newSeq = seq.subList(getPos(start, seq.sizeL()), getPos(end, seq.sizeL()));
            outputStream.write(newSeq);
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
