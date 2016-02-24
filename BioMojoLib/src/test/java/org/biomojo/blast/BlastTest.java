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
package org.biomojo.blast;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.biomojo.BioMojo;
import org.biomojo.blast.blastoutput.BlastOutput;
import org.java0.test.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

// TODO: Auto-generated Javadoc
/**
 * The Class BlastTest.
 */
public class BlastTest extends BaseTest {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(BlastTest.class.getName());

    public BlastTest() {
        BioMojo.init();
    }

    /**
     * Test blast parser.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testBlastParser() throws Exception {
        final File outFile = File.createTempFile("blast_output_", ".xml");
        final File inFile = new File("src/test/resources/data/BlastOutput.xml");
        final InputSource inputSource = new InputSource(new FileReader(inFile));

        final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        final XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();
        final SAXSource source = new SAXSource(xmlReader, inputSource);

        final JAXBContext jaxBContext = JAXBContext.newInstance(BlastOutput.class);
        final Unmarshaller unmarshaller = jaxBContext.createUnmarshaller();
        // BlastOutput blastOutput = (BlastOutput)
        // unmarshaller.unmarshal(inFile);
        final BlastOutput blastOutput = (BlastOutput) unmarshaller.unmarshal(source);
        assertEquals("nr", blastOutput.getDb());

        final Marshaller marshaller = jaxBContext.createMarshaller();
        marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
        marshaller.marshal(blastOutput, outFile);

        outFile.delete();
    }
}
