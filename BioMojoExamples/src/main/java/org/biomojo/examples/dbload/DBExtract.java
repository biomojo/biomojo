package org.biomojo.examples.dbload;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.biomojo.GlobalConst;
import org.biomojo.alphabet.Letters;
import org.biomojo.cli.AbstractSpringCommand;
import org.biomojo.io.SeqOutput;
import org.biomojo.io.fastx.FastaOutput;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.SeqList;
import org.biomojo.util.DbUtil;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Named
@Parameters(commandNames = "dbextract", commandDescription = "Load FAST5 data into database")
public class DBExtract extends AbstractSpringCommand {
    @Parameter(names = { "-o", "--out" }, required = true, description = "Output file name")
    private String outputFile; // value set by command line parser

    @Parameter(names = { "-n", "--name" }, required = true, description = "Dataset name")
    private String name; // value set by command line parser

    @PersistenceContext
    private EntityManager entityManager; // auto-injected by framework

    @Inject
    private DbUtil dbUtil; // auto-injected by framework

    public DBExtract() {
        super(GlobalConst.LIB_SPRING_CONTEXT);
    }

    @Override
    @Transactional
    public void runWithThrow() throws FileNotFoundException, IOException {
        final SeqList<ByteSeq<Letters>> seqList = dbUtil.findByAttribute(SeqList.class, "name", name);

        try (final SeqOutput<ByteSeq<Letters>> seqOutput = new FastaOutput<>(outputFile)) {
            seqList.forEach(seq -> seqOutput.write(seq));

        } catch (final Exception e) {

        }
        entityManager.persist(seqList);
    }
}
