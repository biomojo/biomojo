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
package org.biomojo.io.metadata;

import org.biomojo.alphabet.Alphabet;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class DefaultFileMetaData implements FileMetaData {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(DefaultFileMetaData.class);

    private final FileType fileType;

    private Alphabet<?> mainAlphabet;

    public DefaultFileMetaData(final FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public FileType getFileType() {
        return null;
    }

    @Override
    public Alphabet<?> getMainAlphabet() {
        return null;
    }
}
