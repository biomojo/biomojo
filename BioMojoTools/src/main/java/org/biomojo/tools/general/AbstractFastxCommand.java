package org.biomojo.tools.general;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.AlphabetVariant;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.IUPACVariant;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.io.FileType;
import org.biomojo.io.FileUtil;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.io.fastx.FastaOutputStream;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.io.fastx.FastqOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.ByteSeqSupplier;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.java0.cli.FileInputFileOutputCommand;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public abstract class AbstractFastxCommand extends FileInputFileOutputCommand {
    private static final Logger logger = LoggerFactory.getLogger(AbstractFastxCommand.class);

    @Override
    public void run() {
        try {
            logger.info("Filtering input file");

            final FileType fileType = FileUtil.guessFileType(inputFile);
            if (fileType != FileUtil.guessFileType(outputFile)) {
                logger.error("Input and output file must be the same type");
                return;
            }

            setup();

            switch (fileType) {
            case FASTA:
                processFasta();
                break;
            case FASTQ:
                processFastq();
                break;
            default:
                logger.error("Unrecognized or unsupported file type");
                break;

            }

        } catch (final IOException e) {
            logger.error("Error", e);
        }
    }

    private void processFasta() throws FileNotFoundException, IOException {

        try (final SequenceInputStream<ByteSeq<ByteAlphabet>> inputStream = new FastaInputStream<>(
                new FileInputStream(inputFile), new ByteSeqSupplier<>(AlphabetId.LETTERS));

                final SequenceOutputStream<ByteSeq<ByteAlphabet>> outputStream = new FastaOutputStream<>(
                        new BufferedOutputStream(new FileOutputStream(outputFile)));) {

            process(inputStream, outputStream);
        }
    }

    private void processFastq() throws FileNotFoundException, IOException {

        try (final SequenceInputStream<FastqSeq<Nucleotide<?>, ByteQuality>> inputStream = new FastqInputStream<>(
                new FileInputStream(inputFile),
                new FastqSeqSupplier<>(AlphabetId.NUCLEOTIDE | IUPACVariant.WITH_ANY | IUPACVariant.WITH_AMBIGIGUITY
                        | AlphabetVariant.WITH_NON_CANONICAL, AlphabetId.QUALITY_SANGER));

                final SequenceOutputStream<FastqSeq<Nucleotide<?>, ByteQuality>> outputStream = new FastqOutputStream<>(
                        new BufferedOutputStream(new FileOutputStream(outputFile)));) {

            process(inputStream, outputStream);
        }
    }

    protected abstract void setup();

    protected abstract <A extends ByteAlphabet, T extends ByteSeq<A>> void process(
            final SequenceInputStream<T> inputStream, final SequenceOutputStream<T> outputStream) throws IOException;
}
