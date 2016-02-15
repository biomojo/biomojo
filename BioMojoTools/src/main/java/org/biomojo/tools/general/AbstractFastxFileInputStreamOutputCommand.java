package org.biomojo.tools.general;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.AlphabetVariant;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.alphabet.ByteQuality;
import org.biomojo.alphabet.IUPACVariant;
import org.biomojo.alphabet.Nucleotide;
import org.biomojo.io.FileType;
import org.biomojo.io.FileUtil;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.FastqSeq;
import org.biomojo.sequence.factory.ByteSeqSupplier;
import org.biomojo.sequence.factory.FastqSeqSupplier;
import org.java0.cli.FileInputStreamOutputCommand;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public abstract class AbstractFastxFileInputStreamOutputCommand extends FileInputStreamOutputCommand {
    private static final Logger logger = LoggerFactory.getLogger(AbstractFastxFileInputStreamOutputCommand.class);

    @Override
    public void run() {

        try {

            final FileType fileType = FileUtil.guessFileType(inputFile);

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
                new FileInputStream(inputFile), new ByteSeqSupplier<>(AlphabetId.LETTERS))) {

            process(inputStream, new PrintStream(outputStream));
        }

    }

    private void processFastq() throws FileNotFoundException, IOException {

        try (final SequenceInputStream<FastqSeq<Nucleotide<?>, ByteQuality>> inputStream = new FastqInputStream<>(
                new FileInputStream(inputFile),
                new FastqSeqSupplier<>(AlphabetId.NUCLEOTIDE | IUPACVariant.WITH_ANY | IUPACVariant.WITH_AMBIGIGUITY
                        | AlphabetVariant.WITH_NON_CANONICAL, AlphabetId.QUALITY_SANGER))) {
            process(inputStream, new PrintStream(outputStream));
        }

    }

    protected abstract void setup();

    protected abstract <A extends ByteAlphabet, T extends ByteSeq<A>> void process(
            final SequenceInputStream<T> inputStream, final PrintStream printStream) throws IOException;
}
