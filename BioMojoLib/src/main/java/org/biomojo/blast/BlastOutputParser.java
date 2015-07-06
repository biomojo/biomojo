/*
 * Copyright (C) 2014  Hugh Eaves
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

import java.io.InputStream;

import javax.inject.Named;
import javax.transaction.Transactional;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.biomojo.blast.blastoutput.BlastOutput;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

@Named
public class BlastOutputParser {
	private final static Logger logger = LoggerFactory
			.getLogger(BlastOutputParser.class.getName());

	@Transactional
	public BlastOutput parseResults(InputStream blastOutputStream,
			BlastSequenceResolver sequenceResolver) {

		logger.info("Parsing BLAST results");

		try {
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setFeature("http://xml.org/sax/features/validation",
					false);
			parserFactory.setFeature(XMLConstants.ACCESS_EXTERNAL_DTD, false);

			XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();
			InputSource inputSource = new InputSource(blastOutputStream);
			SAXSource source = new SAXSource(xmlReader, inputSource);

			JAXBContext jaxBContext = JAXBContext.newInstance(BlastOutput.class
					.getName());
			Unmarshaller unmarshaller = jaxBContext.createUnmarshaller();
			BlastOutput blastOutput = (BlastOutput) unmarshaller
					.unmarshal(source);

			sequenceResolver.resolveSequences(blastOutput);

			logger.info("Successfully parsed BLAST output");
			return blastOutput;
		} catch (JAXBException e) {
			throw new UncheckedException(e);
		} catch (SAXNotRecognizedException e) {
			throw new UncheckedException(e);
		} catch (SAXNotSupportedException e) {
			throw new UncheckedException(e);
		} catch (ParserConfigurationException e) {
			throw new UncheckedException(e);
		} catch (SAXException e) {
			throw new UncheckedException(e);
		}

	}
}
