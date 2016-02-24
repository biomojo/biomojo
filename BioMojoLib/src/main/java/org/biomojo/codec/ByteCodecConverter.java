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

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.biomojo.BioMojo;

// TODO: Auto-generated Javadoc
/**
 * The Class ByteCodecConverter.
 *
 * @author Hugh Eaves
 */
@Converter(autoApply = true)
public class ByteCodecConverter implements AttributeConverter<ByteByteCodec, Long> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.
     * Object)
     */
    @Override
    public Long convertToDatabaseColumn(final ByteByteCodec codec) {
        return codec.getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.
     * Object)
     */
    @Override
    public ByteByteCodec convertToEntityAttribute(final Long codecId) {
        return BioMojo.getObject(ByteByteCodec.class, codecId);
    }

}
