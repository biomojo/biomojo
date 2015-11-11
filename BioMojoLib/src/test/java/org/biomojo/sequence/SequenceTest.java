package org.biomojo.sequence;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.IUPACAlphabet;
import org.biomojo.alphabet.InvalidSymbolException;
import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.codec.ByteCodec;
import org.biomojo.codec.CodecId;
import org.biomojo.codec.Codecs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public class SequenceTest {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(SequenceTest.class.getName());

    public SequenceTest() {
    }

    @Test(expected = InvalidSymbolException.class)
    public void testValidation() {
        final EncodedByteSeq<IUPACAlphabet> seq = new EncodedByteSeq<>(
                Alphabets.getAlphabet(AlphabetId.DNA, NucleotideAlphabet.class),
                Codecs.getCodec(CodecId.TWO_BIT_BYTE_CODEC, ByteCodec.class));
        seq.setAll("N".getBytes());
    }

}
