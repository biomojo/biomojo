package org.biomojo.hacks;

import org.biomojo.alphabet.ByteAlphabetConverter;
import org.biomojo.codec.ByteCodecConverter;
import org.biomojo.core.CharSequenceConverter;

public class BioMojoDependencyHack extends AbstractDependencyHack {
    @Override
    public void depend() {
        l(ByteAlphabetConverter.class);
        l(ByteCodecConverter.class);
        l(CharSequenceConverter.class);
    }

}
