/*
 * Copyright (C) 2014  Hugh Eaves
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
package org.biomojo.sequence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.biomojo.alphabet.Alphabets;
import org.biomojo.alphabet.ByteAlphabet;

/**
 * @author Hugh Eaves
 *
 */
@Converter(autoApply = true)
public class ByteAlphabetConverter implements AttributeConverter<ByteAlphabet, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ByteAlphabet alphabet) {
        return (int) alphabet.getId();
    }

    @Override
    public ByteAlphabet convertToEntityAttribute(Integer alphabetId) {
        return Alphabets.getAlphabet(alphabetId, ByteAlphabet.class);
    }
}
