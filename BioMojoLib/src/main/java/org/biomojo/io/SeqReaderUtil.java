package org.biomojo.io;

import java.io.FileNotFoundException;

import org.biomojo.alphabet.Alphabet;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.io.fastx.FastqInputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.Seq;

public class SeqReaderUtil<T, A extends Alphabet<T>, S extends Seq<T, A>> {
    @SuppressWarnings("unchecked")
    public static <A extends ByteAlphabet> SequenceInputStream<ByteSeq<A>> getInputStream(final String fileName)
            throws FileNotFoundException {
        SequenceInputStream<? extends ByteSeq<A>> inputStream = null;

        if (fileName.endsWith(".fasta")) {
            inputStream = new FastaInputStream<A>(fileName);
        } else if (fileName.endsWith(".fasta")) {
            inputStream = new FastqInputStream(fileName);
        } else if (fileName.endsWith(".fast5")) {

        }
        return null;

    }
}
