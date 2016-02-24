/*
 * Copyright (C) 2015  Hugh Eaves
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
package org.biomojo.examples.simpledb;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.biomojo.BioMojo;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.cli.AbstractSpringCommand;
import org.biomojo.sequence.BasicByteSeq;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;

/**
 * @author Hugh Eaves
 *
 */
@Named
@Parameters(commandNames = "simpledbexample", commandDescription = "Trivial example of database access")
public class SimpleDBExample extends AbstractSpringCommand {
    /**
     * Create a new SimpleDBExample.
     *
     * @param configLocation
     */
    public SimpleDBExample() {
        super("simpledb-context.xml");

    }

    private static final Logger logger = LoggerFactory.getLogger(SimpleDBExample.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    DbUtil dbUtil;

    public static void main(final String[] args) {
        BioMojo.init(args, new SimpleDBExample());
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    @Transactional
    public void run() {
        logger.info("Started");

        final ByteSeq<ByteAlphabet> sequence = new BasicByteSeq<ByteAlphabet>("AGTGCCGGTC".getBytes(),
                BioMojo.getObject(ByteAlphabet.class));
        final String name = "Sequence " + new Random().nextInt(100000);
        logger.info("Creating sequence: {}", name);
        sequence.setProp("name", name);
        sequence.setProp("another", "test value");
        entityManager.persist(sequence);
        entityManager.flush();

        for (final Object result : entityManager.createQuery("from BasicByteSeq").getResultList()) {
            final ByteSeq<ByteAlphabet> seq = (ByteSeq<ByteAlphabet>) result;
            logger.info(seq.getProp("name", String.class));
        }

        final BasicByteSeq seq = dbUtil.findByAttribute(BasicByteSeq.class, "name", name);
        logger.info("seq {}", seq);

        logger.info("Done");
    }
}
