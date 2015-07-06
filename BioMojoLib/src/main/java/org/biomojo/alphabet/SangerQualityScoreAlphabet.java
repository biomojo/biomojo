package org.biomojo.alphabet;

/**
 * @author hugh
 *
 *         Valid range: 33 - 126 (i.e. Phred score + 33)
 *
 */
public class SangerQualityScoreAlphabet extends AbstractQualityScoreAlphabet {
	public SangerQualityScoreAlphabet() {
		super(AlphabetId.QUALITY_SANGER, 33, 126);
	}
}
