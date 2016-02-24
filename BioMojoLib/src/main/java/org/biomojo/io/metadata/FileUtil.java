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

package org.biomojo.io;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
    private static final Map<String, FileType> suffixMap = new HashMap<>();

    static {
        suffixMap.put("fasta", FileType.FASTA);
        suffixMap.put("fa", FileType.FASTA);
        suffixMap.put("fas", FileType.FASTA);
        suffixMap.put("fna", FileType.FASTA);
        suffixMap.put("faa", FileType.FASTA);
        suffixMap.put("ffn", FileType.FASTA);
        suffixMap.put("frn", FileType.FASTA);

        suffixMap.put("fastq", FileType.FASTQ);
        suffixMap.put("fq", FileType.FASTQ);

        suffixMap.put("fast5", FileType.FAST5);

    }

    public static FileType guessFileType(final String fileName) {
        final int i = fileName.lastIndexOf('.');
        if (i > 0) {
            final FileType type = suffixMap.get(fileName.substring(i + 1));
            return (type);
        }
        return null;
    }

    public static FileType guessFileType(final File file) {
        return guessFileType(file.getName());
    }
}
