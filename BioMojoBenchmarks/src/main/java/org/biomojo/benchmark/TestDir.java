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
package org.biomojo.benchmark;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hugh Eaves
 *
 */
public class TestDir {
    private static final Logger logger = LoggerFactory.getLogger(TestDir.class.getName());

    public static void main(String[] args) {
        File dirFile = new File("/proc");
        Path dirPath = dirFile.toPath();
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("regex:^/proc/[0-9]+$");
        int matchCount = 0;
        for (int i = 0; i < 10000; ++i) {
            DirectoryStream<Path> stream = null;
            try {
                stream = Files.newDirectoryStream(dirPath);
            } catch (IOException e) {
                logger.error("Caught exception in auto-generated catch block", e);
            }
            for (Path path : stream) {
                if (matcher.matches(path)) {
                    Path statPath = path.resolve("stat");
                    Path fileName = path.getFileName();
                    try {
                        List<String> lines = Files.readAllLines(statPath);
                        quickParse(lines.get(0));
                    } catch (IOException e) {
                        logger.error("Caught exception in auto-generated catch block", e);
                    }
                    // System.out.println(statPath);
                    ++matchCount;
                }
            }
            try {
                stream.close();
            } catch (IOException e) {
                logger.error("Caught exception in auto-generated catch block", e);
            }
            System.out.println(i);

        }
    }

    public static List<String> quickParse(String line) {
        List<String> parsed = new ArrayList<>(50);
        parsed.add(line.substring(0, line.indexOf(' ')));
        int endParen = line.lastIndexOf(')');
        parsed.add(line.substring(line.indexOf('(') + 1, endParen));
        int i = endParen + 2;
        while (i < line.length()) {
            int nextPos = line.indexOf(' ', i);
            if (nextPos < 0) {
                nextPos = line.length();
            }
            parsed.add(line.substring(i, nextPos));
            i = nextPos + 1;
        }
        // System.out.println(parsed.size());
        // System.out.println(parsed);
        return parsed;
    }
}
