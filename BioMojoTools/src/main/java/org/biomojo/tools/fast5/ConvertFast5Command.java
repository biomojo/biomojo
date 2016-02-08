package org.biomojo.tools.fast5;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.biomojo.alphabet.DNA;
import org.biomojo.alphabet.SangerQuality;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.io.fast5.Fast5InputStream;
import org.biomojo.io.fastx.FastaOutputStream;
import org.biomojo.io.fastx.FastqOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.java0.cli.AbstractCommand;
import org.java0.cli.OutputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "convert_fast5")
public class ConvertFast5Command extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(ConvertFast5Command.class.getName());

    @Parameter(names = { "-f", "--fasta" }, description = "Output as FASTA? (otherwise FASTQ)")
    protected boolean fasta = false;

    @Parameter(names = { "-i", "--in" }, required = true, description = "Input directory")
    public String inputDir;

    @Parameter(names = { "-o", "--out" }, required = true, description = "Output file name")
    protected OutputFile outputFile;

    @Override
    public void run() {

        try (final SequenceInputStream<FastqSeq<DNA, SangerQuality>> inputStream = new Fast5InputStream<DNA, SangerQuality>(
                new File(inputDir))) {
            if (fasta) {
                try (final SequenceOutputStream<ByteSeq<DNA>> outputStream = new FastaOutputStream<>(
                        new BufferedOutputStream(new FileOutputStream(outputFile)));) {
                    convert(inputStream, outputStream);
                }
            } else {
                try (final SequenceOutputStream<FastqSeq<DNA, SangerQuality>> outputStream = new FastqOutputStream<>(
                        new BufferedOutputStream(new FileOutputStream(outputFile)));) {
                    convert(inputStream, outputStream);
                }
            }
        } catch (final IOException e) {
            logger.error("Exception: ", e);
        }

    }

    public <O extends ByteSeq<DNA>, I extends O> void convert(final SequenceInputStream<I> inputStream,
            final SequenceOutputStream<O> outputStream) throws IOException {
        int recordCount = 0;
        for (final I seq : inputStream) {
            ++recordCount;
            outputStream.write(seq);
            if (recordCount % 100 == 0) {
                logger.info("{}", recordCount);
            }
        }
    }

}
