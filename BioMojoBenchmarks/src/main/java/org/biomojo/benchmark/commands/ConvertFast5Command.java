package org.biomojo.benchmark.commands;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.biomojo.alphabet.DNA;
import org.biomojo.alphabet.SangerQuality;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.io.fast5.Fast5InputStream;
import org.biomojo.io.fastx.FastqOutputStream;
import org.biomojo.sequence.FastqSeq;
import org.java0.cli.AbstractCommand;
import org.java0.cli.OutputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "convert_fast5")
public class ConvertFast5Command extends AbstractCommand {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ConvertFast5Command.class.getName());

    @Parameter(names = { "-e", "--encode" }, description = "Use two bit encoding for sequences")
    protected boolean encode = false;

    @Parameter(names = { "-i", "--in" }, required = true, description = "Input directory")
    public String inputDir;

    @Parameter(names = { "-o", "--out" }, required = true, description = "Output file name")
    protected OutputFile outputFile;

    @Override
    public void run() {
        try {
            final Fast5InputStream<DNA, SangerQuality> inputStream = new Fast5InputStream<DNA, SangerQuality>(
                    new File(inputDir));
            final SequenceOutputStream<FastqSeq<DNA, SangerQuality>> outputStream = new FastqOutputStream<>(
                    new BufferedOutputStream(new FileOutputStream(outputFile)));

            int recordCount = 0;
            for (final FastqSeq<DNA, SangerQuality> seq : inputStream) {
                ++recordCount;
                outputStream.write(seq);
                if (recordCount % 100 == 0) {
                    logger.info("{}", recordCount);
                }
            }

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
