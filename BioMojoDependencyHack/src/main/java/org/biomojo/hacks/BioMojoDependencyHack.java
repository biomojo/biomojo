package org.biomojo.hacks;

import org.biomojo.sequence.converter.ByteAlphabetConverter;
import org.biomojo.sequence.converter.ByteCodecConverter;
import org.biomojo.sequence.converter.CharSequenceConverter;

public class BioMojoDependencyHack extends AbstractDependencyHack {
    @Override
    public void depend() {
        l(ByteAlphabetConverter.class);
        l(ByteCodecConverter.class);
        l(CharSequenceConverter.class);
    }

}
