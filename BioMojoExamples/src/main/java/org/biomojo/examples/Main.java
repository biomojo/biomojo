package org.biomojo.examples;

import java.io.InputStream;

import javax.inject.Named;

import org.biomojo.BioMojo;
import org.biomojo.examples.commands.AlignCommand;
import org.biomojo.examples.dbload.DBLoad;
import org.biomojo.examples.simpledb.SimpleDBExample;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

@Named
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(AlignCommand.class);

    public static void main(final String[] args) {

        final InputStream is = System.in;
        logger.info("{}", is.markSupported());

        BioMojo.init(args, new SimpleDBExample(), new DBLoad());
    }
}
