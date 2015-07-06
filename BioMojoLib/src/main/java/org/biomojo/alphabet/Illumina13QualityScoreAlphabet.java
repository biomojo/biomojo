package org.biomojo.alphabet;

//Valid range: 64 to 126 (Phred score + 64)
public class Illumina13QualityScoreAlphabet extends
		AbstractQualityScoreAlphabet {

	protected Illumina13QualityScoreAlphabet() {
		super(AlphabetId.QUALITY_ILLUMINA_13, 64, 126);
	}

}
