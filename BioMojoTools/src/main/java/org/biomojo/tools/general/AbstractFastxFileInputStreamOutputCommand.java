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
import org.biomojo.io.SeqInput;
import org.biomojo.io.fastx.FastaInput;
import org.biomojo.io.fastx.FastqInput;
import org.biomojo.io.metadata.FileMetaData;
import org.biomojo.io.metadata.FileTypes;
import org.biomojo.io.metadata.FileUtil;
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

            final FileMetaData fileMetaData = FileUtil.getFileMetaData(inputFile);

            if (fileMetaData.getFileType() == FileTypes.FASTA) {
                processFasta();
            } else if (fileMetaData.getFileType() == FileTypes.FASTQ) {
                processFastq();
            } else {
                logger.error("Unrecognized or unsupported file type");
            }

        } catch (final IOException e) {
            logger.error("Error", e);
        }
    }

    private void processFasta() throws FileNotFoundException, IOException {

        try (final SeqInput<ByteSeq<ByteAlphabet>> inputStream = new FastaInput<>(new FileInputStream(inputFile),
                new ByteSeqSupplier<>(AlphabetId.LETTERS))) {

            process(inputStream, new PrintStream(outputStream));
        }

    }

    private void processFastq() throws FileNotFoundException, IOException {

        try (final SeqInput<FastqSeq<Nucleotide<?>, ByteQuality>> inputStream = new FastqInput<>(
                new FileInputStream(inputFile),
                new FastqSeqSupplier<>(AlphabetId.NUCLEOTIDE | IUPACVariant.WITH_ANY | IUPACVariant.WITH_AMBIGIGUITY
                        | AlphabetVariant.WITH_NON_CANONICAL, AlphabetId.QUALITY_SANGER))) {
            process(inputStream, new PrintStream(outputStream));
        }

    }

    protected abstract void setup();

    protected abstract <A extends ByteAlphabet, T extends ByteSeq<A>> void process(final SeqInput<T> inputStream,
            final PrintStream printStream) throws IOException;
}
