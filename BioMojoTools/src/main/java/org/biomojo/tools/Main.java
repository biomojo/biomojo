package org.biomojo.tools;

import javax.inject.Named;

import org.biomojo.BioMojo;
import org.biomojo.tools.fast5.ConvertFast5Command;
import org.biomojo.tools.general.SliceCommand;

@Named
public class Main {

    public static void main(final String[] args) {
        BioMojo.init(args, new ConvertFast5Command(), new SliceCommand());
    }

}
