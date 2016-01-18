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
package org.biomojo.benchmark.micro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.NucleotideAlphabet;
import org.biomojo.sequence.ByteSeq;
import org.java0.util.timing.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class TestDriver {
    private static final Logger logger = LoggerFactory.getLogger(TestDriver.class.getName());

    /**
     *
     */
    private static final int RUNS_PER_REP = 5;
    /**
     *
     */
    private static final int NUM_REPS = 20;
    /**
     *
     */
    private static final int POSITIONS_LENGTH = 1000 * 1000 * 100;
    /**
     *
     */
    static final int DATA_LENGTH = 1000 * 1000 * 10;

    public void test() {
        final List<Supplier<ByteSeq<NucleotideAlphabet>>> providers = new ArrayList<>();
        // providers.add(new ByteSeqProvider());
        // providers.add(new EncodedByteSeqProvider());
        // providers.add(new NullEncodedByteSeqProvider());
        // providers.add(new ThreeBitEncodedByteSeqProvider());

        // final AccessTest[] tests = { new IntArrayAccessTest(), new
        // NullAccessTest() };
        final Map<Supplier<ByteSeq<NucleotideAlphabet>>, Stopwatch> stopwatches = new HashMap<>();
        for (final Supplier<ByteSeq<NucleotideAlphabet>> provider : providers) {
            stopwatches.put(provider, new Stopwatch(provider.getClass().getName()));
        }
        logger.info("Creating test data");
        final Random random = new Random(5);
        final NucleotideAlphabet alphabet = Alphabets.getAlphabet(AlphabetId.DNA, NucleotideAlphabet.class);
        final byte[] testData = new byte[DATA_LENGTH];
        for (int i = 0; i < testData.length; ++i) {
            testData[i] = alphabet.getSymbolForOrdinal(random.nextInt(alphabet.numSymbols()));
        }
        final int[] positions = new int[POSITIONS_LENGTH];
        for (int i = 0; i < positions.length; ++i) {
            // positions[i] = random.nextInt(testData.length);
            positions[i] = i % testData.length;
            // positions[i] = (i + random.nextInt(10)) % testData.length;
        }
        for (int rep = 0; rep < NUM_REPS; ++rep) {
            Collections.shuffle(providers);
            for (final Supplier<ByteSeq<NucleotideAlphabet>> provider : providers) {
                logger.info("Setting up data for {}", provider.getClass().getName());
                final ByteSeq<NucleotideAlphabet> seq = provider.get();
                seq.setAlphabet(alphabet);
                seq.setAll(testData);
                runGC();
                long result = 0;
                stopwatches.get(provider).start();
                for (int i = 0; i < RUNS_PER_REP; ++i) {
                    for (final int position : positions) {
                        result += seq.getByte(position);
                    }
                }
                stopwatches.get(provider).stop();
                logger.info("result {}", result);
            }
        }
        for (final Stopwatch stopwatch : stopwatches.values()) {
            stopwatch.log("FINAL");
        }
    }

    /**
     *
     */
    private void runGC() {
        logger.info("Starting GC");
        System.gc();
        logger.info("Finished GC");
    }

    public static void main(final String[] args) {
        final TestDriver testDriver = new TestDriver();
        testDriver.test();
    }
}
