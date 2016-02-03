package org.biomojo.examples.commands;

import java.io.IOException;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.biomojo.alphabet.DNA;
import org.biomojo.io.SequenceInputStream;
import org.biomojo.io.fastx.FastaInputStream;
import org.biomojo.sequence.ByteSeq;

@Named
public class LoadSequencesCommand {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void loadFromFile(final String inputFileName) throws IOException {
        final SequenceInputStream<ByteSeq<DNA>> inputStream = new FastaInputStream<>(inputFileName);
        for (final ByteSeq<DNA> seq : inputStream) {
            entityManager.persist(seq);
        }
        inputStream.close();
    }
}
