package org.biomojo.examples.alphabetfilter;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.DNA;
import org.biomojo.alphabet.IUPACVariant;
import org.biomojo.alphabet.SangerQualityScore;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.io.fastx.FastqOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.java0.cli.InputOutputCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;

@Parameters(commandNames = "alphabetfilter", commandDescription = "Filter Input File Alphabet")
public class AlphabetFilterCommand extends InputOutputCommand {
    private static final Logger logger = LoggerFactory.getLogger(AlphabetFilterCommand.class.getName());

    @Override
    public void run() {
        try {
            logger.info("Filtering input file");

            final DNA targetAlphabet = Alphabets.getAlphabet(AlphabetId.DNA, DNA.class);

            // final Supplier<ByteSeq<NucleotideAlphabet>> supplier = new
            // ByteSeqSupplier<NucleotideAlphabet>(
            // AlphabetId.DNA | IUPACAlphabetVariant.WITH_AMBIGIGUITY |
            // IUPACAlphabetVariant.WITH_NON_CANONICAL);
            // final SequenceInputStream<ByteSeq<?>> inputStream = new
            // FastaInputStream(new FileInputStream(inputFile),
            // supplier);
            // final SequenceOutputStream<ByteSeq<?>> outputStream = new
            // FastaOutputStream(
            // new BufferedOutputStream(new FileOutputStream(outputFile)));

            final Supplier<FastqSeq<DNA, SangerQualityScore>> supplier = new FastqSeqSupplier<>(
                    AlphabetId.DNA | IUPACVariant.WITH_AMBIGIGUITY, AlphabetId.QUALITY_SANGER);
            final SequenceInputStream<FastqSeq<DNA, SangerQualityScore>> inputStream = new FastqInputStream<>(
                    new FileInputStream(inputFile), supplier);
            final SequenceOutputStream<FastqSeq<DNA, SangerQualityScore>> outputStream = new FastqOutputStream<>(
                    new BufferedOutputStream(new FileOutputStream(outputFile)));

            filter(inputStream, outputStream, targetAlphabet);

            inputStream.close();
            outputStream.close();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public <T extends ByteSeq<? extends ByteAlphabet>> void filter(final SequenceInputStream<T> inputStream,
            final SequenceOutputStream<T> outputStream, final ByteAlphabet targetAlphabet) {
        try {

            int recordCount = 0;
            for (T sequence = inputStream.read(); sequence != null; sequence = inputStream.read()) {
                ++recordCount;
                if (recordCount % 10000 == 0) {
                    logger.info("Record #{}", recordCount);
                    logger.info("Length = {}", sequence.size());
                }
                sequence.canonicalize();
                if (targetAlphabet.isValid(sequence.toByteArray())) {
                    outputStream.write(sequence);
                } else {
                    logger.info("Skipping sequence {}", sequence.getDescription());
                }
            }

        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
