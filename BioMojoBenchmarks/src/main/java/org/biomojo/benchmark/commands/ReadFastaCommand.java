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
package org.biomojo.benchmark.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.DNA;
import org.biomojo.codec.CodecId;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.sequence.ByteSeq;
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
@Parameters(commandNames = "read_fasta")
public class ReadFastaCommand extends BaseInputCommand {
    private static final Logger logger = LoggerFactory.getLogger(ReadFastaCommand.class.getName());

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        try {
            logger.info("BioMojo Fasta Read Benchmark");

            final FastaInputStream<DNA> inputStream = new FastaInputStream<>(new FileInputStream(inputFile),
                    false);

            int recordCount = 0;
            long totalLength = 0;

            Supplier<ByteSeq<DNA>> supplier = new ByteSeqSupplier<>(AlphabetId.DNA);
            if (encode) {
                supplier = new EncodedByteSeqSupplier<>(AlphabetId.DNA, CodecId.TWO_BIT_BYTE_CODEC);
            }
            final ByteSeq<DNA> sequence = supplier.get();

            while (inputStream.read(sequence)) {
                totalLength += sequence.size();
                ++recordCount;
            }
            inputStream.close();

            logger.info("Done loading " + recordCount + " sequences");
            logger.info("Total length is " + totalLength + " bases");

            Thread.sleep(0);

        } catch (final FileNotFoundException e) {
            throw new UncheckedException(e);
        } catch (final IOException e) {
            throw new UncheckedException(e);
        } catch (final InterruptedException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }

    }
}
