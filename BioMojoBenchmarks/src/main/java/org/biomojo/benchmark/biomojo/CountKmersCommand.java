package org.biomojo.benchmark.biomojo;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.DNA;
import org.biomojo.codec.CodecId;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.factory.ByteSeqSupplier;
import org.biomojo.sequence.factory.EncodedByteSeqSupplier;
import org.biomojo.stats.KmerCounter;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "count_kmers")
public class CountKmersCommand extends BaseInputOutputCommand {
    private static final Logger logger = LoggerFactory.getLogger(CountKmersCommand.class);

    @Parameter(names = { "-k", "--kmerlength" }, description = "Kmer Length")
    private final int kmerLength = 2;

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        logger.info("BioMojo Kmer Count Benchmark");

        try {

            final FastaInputStream<DNA> inputStream = new FastaInputStream<>(new FileInputStream(inputFile));
            final PrintStream outputStream = new PrintStream(
                    new BufferedOutputStream(new FileOutputStream(outputFile)));

            int recordCount = 0;
            Supplier<ByteSeq<DNA>> supplier = new ByteSeqSupplier<>(AlphabetId.DNA);
            if (encode) {
                supplier = new EncodedByteSeqSupplier<>(AlphabetId.DNA, CodecId.TWO_BIT_BYTE_CODEC);
            }

            final ByteSeq<DNA> sequence = supplier.get();
            final KmerCounter<DNA> counter = new KmerCounter<>(kmerLength, sequence.getAlphabet());

            while (inputStream.read(sequence)) {
                counter.count(sequence);
                ++recordCount;
            }

            inputStream.close();

            final int[] kmerCounts = counter.getCounts();
            for (int i = 0; i < kmerCounts.length; ++i) {
                outputStream.println(kmerCounts[i]);
            }

            outputStream.close();

            logger.info("Done loading " + recordCount + " sequences");

        } catch (final FileNotFoundException e) {
            throw new UncheckedException(e);
        } catch (final IOException e) {
            throw new UncheckedException(e);
        }
    }
}
