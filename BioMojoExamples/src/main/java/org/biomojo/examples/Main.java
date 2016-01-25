package org.biomojo.examples;

import javax.inject.Named;

import org.biomojo.BioMojo;
import org.biomojo.examples.alphabetfilter.AlphabetFilterCommand;
import org.biomojo.examples.simpledb.SimpleDBExample;

@Named
public class Main {

    public static void main(final String[] args) {
        BioMojo.init(args, new SimpleDBExample(), new FastaHeadCommand(), new AlphabetFilterCommand());
    }
}
