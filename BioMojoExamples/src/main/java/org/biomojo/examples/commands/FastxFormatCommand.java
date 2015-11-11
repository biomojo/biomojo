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
package org.biomojo.examples.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.io.fastx.FastaOutputStream;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.ByteSeqImpl;
import org.java0.cli.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Parameters(commandNames = "fastxformat", commandDescription = "Reformat Fastx file")
public class FastxFormatCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(FastxFormatCommand.class.getName());

    @Parameter(names = { "-i", "--input" }, required = true, description = "Input File Name")
    private InputStream inputStream;

    @Parameter(names = { "-o", "--output" }, required = true, description = "Output File Name")
    private OutputStream outputStream;

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream
     *            the inputStream to set
     */
    public void setInputStream(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * @return the outputStream
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * @param outputStream
     *            the outputStream to set
     */
    public void setOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * @see org.java0.cli.Command#run()
     */
    @Override
    public void run() {
        try (FastaInputStream fastaInput = new FastaInputStream(inputStream);
                FastaOutputStream fastaOutput = new FastaOutputStream(outputStream)) {

            boolean readRecord = false;
            ByteSeq<ByteAlphabet> record;

            do {
                record = new ByteSeqImpl<ByteAlphabet>();
                readRecord = fastaInput.read(record);
                if (readRecord) {
                    fastaOutput.write(record);
                }
            } while (readRecord);

        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }
    }
}
