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
package org.biomojo.benchmark.framework.procutil;

/**
 * @author Hugh Eaves
 *
 */
public class LinuxTimeCommandInfo extends AbstractProcessInfo {

    String commandLine;
    String percentCPU;

    public LinuxTimeCommandInfo(final String[] values) {
        parse(values);
    }

    // command line ^ major faults ^ minor faults ^ max rss ^ percent cpu ^
    // system time ^ user time ^ elapsed time
    public void parse(final String[] rawValues) {
        int pos = 0;
        commandLine = rawValues[pos++];
        majorFaults = toLong(rawValues[pos++]);
        minorFaults = toLong(rawValues[pos++]);
        residentBytes = memoryKBToBytes(rawValues[pos++]);
        percentCPU = rawValues[pos++];
        systemMilliseconds = secondsToMS(rawValues[pos++]);
        userMilliseconds = secondsToMS(rawValues[pos++]);
        elapsedMilliseconds = secondsToMS(rawValues[pos++]);
    }

    public static long secondsToMS(final String value) {
        return (long) (Double.parseDouble(value) * (1000L));
    }

    public static long memoryKBToBytes(final String value) {
        return Long.parseLong(value) * 1024;
    }

}
