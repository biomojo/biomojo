package org.biomojo.alphabet;

import org.biomojo.symbols.CommonSymbols;

public interface GappedByte<T extends ByteAlphabet> extends Gapped<Byte, T>, ByteAlphabet {
    /*
     * (non-Javadoc)
     * 
     * @see org.biomojo.alphabet.GappableAlphabet#gapSymbol()
     */
    @Override
    public default Byte gapSymbol() {
        return CommonSymbols.GAP;
    }
}
