package org.biomojo.examples.dbload;

import java.io.IOException;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.biomojo.GlobalConst;
import org.biomojo.alphabet.Letters;
import org.biomojo.cli.AbstractSpringCommand;
import org.biomojo.io.SeqInput;
import org.biomojo.io.fastx.DefaultFastaInput;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.SeqArrayList;
import org.biomojo.sequence.SeqList;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Named
@Parameters(commandNames = "dbload", commandDescription = "Load FASTA data into database")
public class DBLoad extends AbstractSpringCommand {
    @Parameter(names = { "-i", "--in" }, required = true, description = "Input file name or directory name")
    private String inputFile; // value set by command line parser

    @Parameter(names = { "-n", "--name" }, required = true, description = "Dataset name")
    private String name; // value set by command line parser

    @PersistenceContext
    private EntityManager entityManager; // auto-injected by framework

    public DBLoad() {
        super(GlobalConst.LIB_SPRING_CONTEXT);
    }

    @Override
    @Transactional
    public void runWithThrow() throws IOException {
        final SeqList<ByteSeq<?>> seqList = new SeqArrayList<>(name);
        try (SeqInput<ByteSeq<Letters>> seqInput = new DefaultFastaInput(inputFile)) {
            seqInput.forEach(seq -> seqList.add(seq));
        }

        entityManager.persist(seqList);
    }
}
