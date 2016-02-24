package org.biomojo.io.fastx;

import java.io.InputStream;

import org.biomojo.GlobalConst;
import org.biomojo.alphabet.Letters;
import org.biomojo.io.DefaultHeaderParser;
import org.biomojo.sequence.factory.ByteSeqSupplier;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class DefaultFastaInput extends FastaInput<Letters> {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(DefaultFastaInput.class);

    public DefaultFastaInput(final String inputFileName) {
        super(inputFileName, new DefaultHeaderParser(), new ByteSeqSupplier<Letters>(Letters.class),
                GlobalConst.VALIDATE_INPUT_SEQS);
    }

    public DefaultFastaInput(final InputStream inputStream) {
        super(inputStream, new DefaultHeaderParser(), new ByteSeqSupplier<Letters>(Letters.class),
                GlobalConst.VALIDATE_INPUT_SEQS);
    }

    public DefaultFastaInput(final InputStream inputStream, final boolean validateSequenceData) {
        super(inputStream, new DefaultHeaderParser(), new ByteSeqSupplier<Letters>(Letters.class),
                validateSequenceData);
    }

}
