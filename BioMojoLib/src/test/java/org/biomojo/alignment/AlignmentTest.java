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
package org.biomojo.alignment;

import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.AminoAcid;
import org.biomojo.alphabet.DNA;
import org.biomojo.sequence.BasicByteSeq;
import org.biomojo.sequence.ByteSeq;
import org.biomojo.sequence.SeqArrayList;
import org.biomojo.sequence.SeqList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlignmentTest {
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AlignmentTest.class);

    @Test
    public void testSmithWatermanLinear() {
        final AminoAcid alphabet = Alphabets.getAlphabet(AlphabetId.AMINO_ACID, AminoAcid.class);
        final ByteSeq<AminoAcid> seq1 = new BasicByteSeq<>("PRTEINS".getBytes(), alphabet);
        final ByteSeq<AminoAcid> seq2 = new BasicByteSeq<>("PRTWPSEIN".getBytes(), alphabet);
        final ByteSubstitutionMatrix matrix = new PrecomputedAminoAcidSubstitutionMatrix("BLOSUM", 62);
        final ByteSeqAligner<AminoAcid> aligner = new NeedlemanWunschLinearGapByteSeqAligner<>(matrix, 0);
        final SeqList<ByteSeq<AminoAcid>> seqList = new SeqArrayList<>();
        seqList.add(seq1);
        seqList.add(seq2);
        final ByteSeqAlignment<AminoAcid> alignment = aligner.align(seqList);
        for (int i = 0; i < alignment.size(); ++i) {
            logger.info("Seq {} = {}", i, alignment.get(i).toString());
        }
    }

    @Test
    public void test2() {
        final DNA alphabet = Alphabets.getAlphabet(AlphabetId.DNA, DNA.class);
        final ByteSeq<DNA> seq1 = new BasicByteSeq<>("GAATTCAGTTA".getBytes(), alphabet);
        final ByteSeq<DNA> seq2 = new BasicByteSeq<>("GGATCGA".getBytes(), alphabet);
        final ByteSubstitutionMatrix matrix = new MatchMismatchByteSubstitutionMatrix(alphabet, 2, -1);
        final ByteSeqAligner<DNA> aligner = new NeedlemanWunschLinearGapByteSeqAligner<>(matrix, -2);
        final SeqList<ByteSeq<DNA>> seqList = new SeqArrayList<>();
        seqList.add(seq1);
        seqList.add(seq2);
        final ByteSeqAlignment<DNA> alignment = aligner.align(seqList);
        for (int i = 0; i < alignment.size(); ++i) {
            logger.info("Seq {} = {}", i, alignment.get(i).toString());
        }
    }

    @Test
    public void test3() {
        final DNA alphabet = Alphabets.getAlphabet(AlphabetId.DNA, DNA.class);
        final ByteSeq<DNA> seq1 = new BasicByteSeq<>("AGTCAGTCAGTCAGTCAGTCAGTC".getBytes(), alphabet);
        final ByteSeq<DNA> seq2 = new BasicByteSeq<>("AGTCAGTCACCGTCAGTCAGTCAGTC".getBytes(), alphabet);
        final ByteSubstitutionMatrix matrix = new MatchMismatchByteSubstitutionMatrix(alphabet, 2, -1);
        final ByteSeqAligner<DNA> aligner = new NeedlemanWunschAffineGapByteSeqAligner<>(matrix, -2, -2);
        final SeqList<ByteSeq<DNA>> seqList = new SeqArrayList<>();
        seqList.add(seq1);
        seqList.add(seq2);
        final ByteSeqAlignment<DNA> alignment = aligner.align(seqList);
        for (int i = 0; i < alignment.size(); ++i) {
            logger.info("Seq {} = {}", i, alignment.get(i).toString());
        }
    }
}
