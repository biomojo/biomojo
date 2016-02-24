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

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.SeqInput;
import org.biomojo.io.SeqOutput;
import org.biomojo.sequence.ByteSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "head", commandDescription = "Copy n records from the head of a FASTX file")
public class HeadCommand extends AbstractFastxCommand {
    private static final Logger logger = LoggerFactory.getLogger(HeadCommand.class.getName());

    @Parameter(names = { "-n", "--numrecs" }, required = true, description = "Number of records to copy")
    protected int numRecords = 0;

    @Parameter(names = { "-c", "--canonicalize" }, description = "Canonicalize characters")
    protected boolean canonicalize;

    @Override
    protected void setup() {
    }

    @Override
    protected <A extends ByteAlphabet, T extends ByteSeq<A>> void process(final SeqInput<T> inputStream,
            final SeqOutput<T> outputStream) throws IOException {
        int recordCount = 0;
        for (final T seq : inputStream) {
            ++recordCount;
            if (canonicalize) {
                seq.canonicalize();
            }
            outputStream.write(seq);
            if (recordCount % 1000 == 0) {
                logger.info("{}", recordCount);
            }
            if (recordCount >= numRecords) {
                break;
            }
        }
        logger.info("Wrote {} records to output file", recordCount);
    }

}
