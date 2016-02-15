package org.biomojo.tools;

import org.biomojo.BioMojo;
import org.biomojo.tools.fast5.ConvertFast5Command;
import org.biomojo.tools.general.AlphabetFilterCommand;
import org.biomojo.tools.general.HeadCommand;
import org.biomojo.tools.general.SliceCommand;
import org.biomojo.tools.general.StatsCommand;

public class Main {
    public static void main(final String[] args) {
        BioMojo.init(args, new ConvertFast5Command(), new SliceCommand(), new HeadCommand(),
                new AlphabetFilterCommand(), new StatsCommand());
    }
}
