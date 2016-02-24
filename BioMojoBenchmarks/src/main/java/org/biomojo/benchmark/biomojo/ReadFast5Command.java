package org.biomojo.benchmark.biomojo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.DNA;
import org.biomojo.alphabet.SangerQuality;
import org.biomojo.codec.CodecId;
import org.biomojo.io.SeqInput;
import org.biomojo.io.fast5.Fast5Input;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.EncodedFastqSeqSupplier;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.java0.core.exception.UncheckedException;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;
import org.java0.util.timing.Stopwatch;

import com.beust.jcommander.Parameters;

@Parameters(commandNames = "read_fast5")
public class ReadFast5Command extends BaseInputCommand {
    private static final Logger logger = LoggerFactory.getLogger(ReadFast5Command.class.getName());

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        try {
            logger.info("BioMojo Fasta Read Benchmark");

            final Stopwatch sw = new Stopwatch();
            sw.start();

            final SeqInput<FastqSeq<DNA, SangerQuality>> inputStream = new Fast5Input<DNA, SangerQuality>(inputFile);

            int recordCount = 0;
            long totalLength = 0;

            Supplier<FastqSeq<DNA, SangerQuality>> supplier = new FastqSeqSupplier<>(AlphabetId.DNA,
                    AlphabetId.QUALITY_SANGER);
            if (encode) {
                supplier = new EncodedFastqSeqSupplier<>(AlphabetId.DNA, CodecId.TWO_BIT_BYTE_CODEC,
                        AlphabetId.QUALITY_SANGER);
            }

            final FastqSeq<DNA, SangerQuality> sequence = supplier.get();
            logger.info("Reading records");
            while (inputStream.read(sequence)) {
                totalLength += sequence.size();
                ++recordCount;
                if (recordCount % 1000 == 0) {
                    logger.info("Loaded {} records", recordCount);
                }
            }
            inputStream.close();

            logger.info("Done loading " + recordCount + " sequences");
            logger.info("Total length is " + totalLength + " bases");

            sw.stop();

        } catch (final FileNotFoundException e) {
            throw new UncheckedException(e);
        } catch (final IOException e) {
            throw new UncheckedException(e);
        }

    }
}
