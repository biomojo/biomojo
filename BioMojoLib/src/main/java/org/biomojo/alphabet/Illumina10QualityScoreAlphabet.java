package org.biomojo.alphabet;

//Valid range: 59 to 126 (Phred score + 64)
public class Illumina10QualityScoreAlphabet extends
		AbstractQualityScoreAlphabet {

	protected Illumina10QualityScoreAlphabet() {
		super(AlphabetId.QUALITY_ILLUMINA_10, 59, 126);
	}

}
