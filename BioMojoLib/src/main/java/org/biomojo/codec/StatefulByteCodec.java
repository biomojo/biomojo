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
package org.biomojo.codec;

import org.biomojo.alphabet.ByteAlphabet;

// TODO: Auto-generated Javadoc
/**
 * The Interface StatefulByteCodec.
 *
 * @author Hugh Eaves
 */
public interface StatefulByteCodec extends StatefulCodec, ByteByteCodec {
    
    /**
     * Gets the encoded data.
     *
     * @param alphabet the alphabet
     * @return the encoded data
     */
    public byte[] getEncodedData(ByteAlphabet alphabet);
}
