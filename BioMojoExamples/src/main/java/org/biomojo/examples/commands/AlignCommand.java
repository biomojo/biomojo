package org.biomojo.examples.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.biomojo.BioMojo;
import org.biomojo.alignment.ByteSeqAligner;
import org.biomojo.alignment.MatchMismatchByteSubstitutionMatrix;
import org.biomojo.alignment.SmithWatermanLinearGapByteSeqAligner;
import org.biomojo.alphabet.DNA;
import org.biomojo.io.SeqInput;
import org.biomojo.io.fastx.FastaInput;
import org.biomojo.sequence.ByteSeq;
import org.java0.cli.AbstractCommand;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;

@Parameters(commandNames = "align")
public class AlignCommand extends AbstractCommand {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AlignCommand.class);

    @Override
    public void run() {
        try {
            final SeqInput<ByteSeq<DNA>> si = new FastaInput<DNA>(new FileInputStream("INS.fasta"), DNA.class);
            final ByteSeq<DNA> insSeq = si.read();
            si.close();
            // final ByteSeq<DNA> seq = new BasicByteSeq<DNA>("AGTC");

            final ByteSeqAligner<DNA> aligner = new SmithWatermanLinearGapByteSeqAligner<DNA>(
                    new MatchMismatchByteSubstitutionMatrix(BioMojo.getObject(DNA.class), 1, -1), 1);
        } catch (final FileNotFoundException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        } catch (final IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }

    }

}
