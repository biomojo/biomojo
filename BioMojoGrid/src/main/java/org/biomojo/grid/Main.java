package org.biomojo.grid;

import org.biomojo.BioMojo;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class Main {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        BioMojo.init(args, new GridServerCommand());
    }
}
