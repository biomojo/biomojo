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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.IUPACAlphabetVariant;
import org.biomojo.alphabet.AlphabetId;
import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.io.ParseException;
import org.biomojo.symbols.AminoAcids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * A <tt>SubstitutionMatrix</tt> initialized from one of the pre-computed NCBI
 * BLAST substitution matrices.
 *
 * The pre-computed matrices are read from the classpath, but were originally
 * downloaded from:
 *
 * ftp://ftp.ncbi.nih.gov/blast/matrices/
 *
 * @author Hugh Eaves
 *
 */
public class PrecomputedAminoAcidSubstitutionMatrix extends AbstractByteSubstitutionMatrix
        implements ByteSubstitutionMatrix {
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory
            .getLogger(PrecomputedAminoAcidSubstitutionMatrix.class.getName());

    /** The amino acid order. */
    public static char[] AMINO_ACID_ORDER = { AminoAcids.ALANINE, AminoAcids.ARGININE, AminoAcids.ASPARAGINE,
            AminoAcids.ASPARTIC_ACID, AminoAcids.CYSTEINE, AminoAcids.GLUTAMINE, AminoAcids.GLUTAMIC_ACID,
            AminoAcids.GLYCINE, AminoAcids.HISTIDINE, AminoAcids.ISOLEUCINE, AminoAcids.LEUCINE, AminoAcids.LYSINE,
            AminoAcids.METHIONINE, AminoAcids.PHENYLALANINE, AminoAcids.PROLINE, AminoAcids.SERINE,
            AminoAcids.THREONINE, AminoAcids.TRYPTOPHAN, AminoAcids.TYROSINE, AminoAcids.VALINE,
            AminoAcids.ASPARTIC_ACID_OR_ASPARAGINE, AminoAcids.GLUTAMIC_ACID_OR_GLUTAMINE, AminoAcids.ANY,
            AminoAcids.STOP };

    /** The Constant PATH_PREFIX. */
    protected static final String PATH_PREFIX = "org/biomojo/alignment/matrix/";

    /**
     * Instantiates a new precomputed amino acid substitution matrix.
     *
     * @param prefix the prefix
     * @param instance the instance
     */
    public PrecomputedAminoAcidSubstitutionMatrix(String prefix, int instance) {
        super(Alphabets.getAlphabet(
                AlphabetId.AMINO_ACID | IUPACAlphabetVariant.WITH_ANY | IUPACAlphabetVariant.WITH_AMBIGIGUITY,
                ByteAlphabet.class));

        String fileName = PATH_PREFIX + prefix + instance;

        logger.debug("Reading substitution matrix from file {}", fileName);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)));

        try {
            int validLines = 0;
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                logger.debug("Read line [{}]", line);
                line = line.trim();
                String[] tokens = line.split("\\s+");
                if ((tokens.length == AMINO_ACID_ORDER.length + 1) && tokens[0] != "#") {
                    String aminoAcid = tokens[0];
                    if (aminoAcid.length() != 1) {
                        throw new ParseException(
                                "Invalid substitution matrix line in " + fileName + ": [" + line + "]");

                    }
                    char fromChar = aminoAcid.charAt(0);
                    if (getOrder(fromChar) < 0) {
                        throw new ParseException(
                                "Invalid substitution matrix line in " + fileName + ": [" + line + "]");
                    }

                    for (int i = 1; i < tokens.length; ++i) {
                        int score = Integer.parseInt(tokens[i]);
                        char toChar = AMINO_ACID_ORDER[i - 1];
                        setScore(fromChar, toChar, score);
                    }
                    ++validLines;
                }
            }

            if (validLines < AMINO_ACID_ORDER.length) {
                throw new ParseException("Missing lines in " + fileName);
            }

            reader.close();
        } catch (IOException e) {
            logger.error("Caught exception in auto-generated catch block", e);
        }
    }

    /**
     * Gets the order.
     *
     * @param aminoAcid the amino acid
     * @return the order
     */
    protected int getOrder(char aminoAcid) {
        for (int i = 0; i < AMINO_ACID_ORDER.length; ++i) {
            if (AMINO_ACID_ORDER[i] == aminoAcid) {
                return i;
            }
        }
        return -1;
    }
}
