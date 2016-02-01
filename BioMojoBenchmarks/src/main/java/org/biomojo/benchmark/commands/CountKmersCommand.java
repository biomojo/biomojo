package org.biomojo.benchmark.commands;

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
import org.java0.core.exception.UncheckedException;
import org.java0.util.bits.Masks;
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

            final FastaInputStream<DNA> inputStream = new FastaInputStream<>(new FileInputStream(inputFile), false);
            final PrintStream outputStream = new PrintStream(
                    new BufferedOutputStream(new FileOutputStream(outputFile)));

            int numKmers = 4;
            for (int i = 1; i < kmerLength; ++i) {
                numKmers = numKmers * 4;
            }
            final int kmerCounts[] = new int[numKmers];

            int recordCount = 0;
            Supplier<ByteSeq<DNA>> supplier = new ByteSeqSupplier<>(AlphabetId.DNA);
            if (encode) {
                supplier = new EncodedByteSeqSupplier<>(AlphabetId.DNA, CodecId.TWO_BIT_BYTE_CODEC);
            }
            final ByteSeq<DNA> sequence = supplier.get();
            final DNA alphabet = sequence.getAlphabet();

            final int bitsPerSymbol = 2;
            final int kmerMask = Masks.LOW_ORDER_INT_MASK[kmerLength * bitsPerSymbol];

            while (inputStream.read(sequence)) {
                final byte[] bytes = sequence.toByteArray();
                int kmer = 0;
                for (int i = 0; i < kmerLength; ++i) {
                    kmer = kmer << bitsPerSymbol;
                    kmer = kmer | alphabet.getOrdinalForSymbol(bytes[i]);
                }
                kmerCounts[kmer]++;
                for (int i = kmerLength; i < bytes.length; ++i) {
                    kmer = kmer << bitsPerSymbol;
                    kmer = kmer | alphabet.getOrdinalForSymbol(bytes[i]);
                    kmerCounts[kmer & kmerMask]++;
                }
                ++recordCount;
            }
            inputStream.close();

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
