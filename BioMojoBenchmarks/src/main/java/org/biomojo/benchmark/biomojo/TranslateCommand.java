/*
 * Copyright (C) 2015  Hugh Eaves
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.biomojo.benchmark.biomojo;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Supplier;

import org.biomojo.BioMojo;
import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.AminoAcid;
import org.biomojo.alphabet.DNA;
import org.biomojo.alphabet.IUPACVariant;
import org.biomojo.codec.CodecId;
import org.biomojo.io.fastx.FastaInput;
import org.biomojo.io.fastx.FastaOutput;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.TranslatedSeq;
import org.biomojo.sequence.factory.ByteSeqSupplier;
import org.biomojo.sequence.factory.EncodedByteSeqSupplier;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "translate")
public class TranslateCommand extends BaseInputOutputCommand {
    private static final Logger logger = LoggerFactory.getLogger(TranslateCommand.class.getName());

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        try {
            logger.info("BioMojo sequence translation benchmark");

            final FastaInput<DNA> inputStream = new FastaInput<>(new FileInputStream(inputFile), DNA.class);
            final FastaOutput<AminoAcid> outputStream = new FastaOutput<>(
                    new BufferedOutputStream(new FileOutputStream(outputFile)));

            int recordCount = 0;
            long totalLength = 0;

            Supplier<ByteSeq<DNA>> supplier = new ByteSeqSupplier<>(AlphabetId.DNA);
            if (encode) {
                supplier = new EncodedByteSeqSupplier<>(AlphabetId.DNA, CodecId.TWO_BIT_BYTE_CODEC);
            }
            final ByteSeq<DNA> sequence = supplier.get();

            final TranslatedSeq translatedSeq = new TranslatedSeq(sequence,
                    BioMojo.getObject(AminoAcid.class, AlphabetId.AMINO_ACID + IUPACVariant.WITH_AMBIGIGUITY));

            while (inputStream.read(sequence)) {
                totalLength += sequence.size();
                outputStream.write(translatedSeq);
                ++recordCount;
            }
            outputStream.close();
            inputStream.close();

            logger.info("Done loading " + recordCount + " sequences");
            logger.info("Total length is " + totalLength + " bases");

        } catch (final FileNotFoundException e) {
            throw new UncheckedException(e);
        } catch (final IOException e) {
            throw new UncheckedException(e);
        }
    }
}
