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
package org.biomojo.tools.general;

import java.io.IOException;

import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.SequenceOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "alphabetfilter", commandDescription = "Filter Input File Alphabet")
public class AlphabetFilterCommand extends AbstractFastxCommand {
    private static final Logger logger = LoggerFactory.getLogger(AlphabetFilterCommand.class.getName());

    @Parameter(names = { "-a", "--alphabetId" }, required = true, description = "Numeric alphabet id")
    private int alphabetId;

    private ByteAlphabet targetAlphabet;

    @Override
    protected void setup() {
        targetAlphabet = Alphabets.getAlphabet(alphabetId);

    }

    @Override
    protected <A extends ByteAlphabet, T extends ByteSeq<A>> void process(final SequenceInputStream<T> inputStream,
            final SequenceOutputStream<T> outputStream) {
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
            logger.error("Error", e);
        }
    }

}
