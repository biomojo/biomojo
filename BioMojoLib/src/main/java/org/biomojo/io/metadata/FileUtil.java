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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
    private static final Map<String, FileTypes> suffixMap = new HashMap<>();

    static {
        suffixMap.put("fasta", FileTypes.FASTA);
        suffixMap.put("fa", FileTypes.FASTA);
        suffixMap.put("fas", FileTypes.FASTA);
        suffixMap.put("fna", FileTypes.FASTA);
        suffixMap.put("faa", FileTypes.FASTA);
        suffixMap.put("ffn", FileTypes.FASTA);
        suffixMap.put("frn", FileTypes.FASTA);

        suffixMap.put("fastq", FileTypes.FASTQ);
        suffixMap.put("fq", FileTypes.FASTQ);

        suffixMap.put("fast5", FileTypes.FAST5);

    }

    public static FileMetaData getFileMetaData(final String fileName) {
        final int i = fileName.lastIndexOf('.');
        if (i > 0) {
            final FileTypes type = suffixMap.get(fileName.substring(i + 1));
            return new DefaultFileMetaData(type);
        }
        return null;
    }

    public static FileMetaData getFileMetaData(final File file) {
        return getFileMetaData(file.getName());

    }

}
