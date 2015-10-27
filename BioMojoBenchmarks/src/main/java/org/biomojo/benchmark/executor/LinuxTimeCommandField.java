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
package org.biomojo.benchmark.executor;

/**
 * @author Hugh Eaves
 *
 */
public enum LinuxTimeCommandField implements Field {
    COMMAND_LINE("C", STRING_TYPE), AVG_UNSHARED_DATA("D", MEMORY_TYPE), WALL_CLOCK_HMS("E", STRING_TYPE), MAJOR_FAULTS(
            "F"), NUM_FS_INPUTS("I"), AVG_TOTAL_MEM("K", MEMORY_TYPE), MAX_RSS("M", MEMORY_TYPE), NUM_FS_OUTPUTS(
                    "O"), PERCENT_CPU("P", STRING_TYPE), MINOR_FAULTS("R"), SYSTEM_TIME("S", TIME_TYPE), USER_TIME("U",
                            TIME_TYPE), SWAPPED_OUT("W"), AVG_TEXT_MEM("X", MEMORY_TYPE), INV_CONTEXT_SWITCHES(
                                    "c"), WALL_CLOCK_TIME("e", TIME_TYPE), SIGNALS_DELIVERED("k"), AVG_STACK_MEM("p",
                                            MEMORY_TYPE), SOCKET_MSG_REC("r"), SOCKET_MSG_SENT("s"), AVG_RSS("t",
                                                    MEMORY_TYPE), VOL_CONTEXT_SWITCHES("w");

    String fieldCode;
    int fieldType = DEFAULT_TYPE;

    LinuxTimeCommandField(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    LinuxTimeCommandField(String fieldCode, int fieldType) {
        this.fieldCode = fieldCode;
        this.fieldType = fieldType;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * @see org.biomojo.benchmark.executor.ValueParser#getFieldType()
     */
    @Override
    public int getFieldType() {
        return fieldType;
    }

    /**
     * @see org.biomojo.benchmark.executor.ValueParser#convertTime(java.lang.String)
     */
    @Override
    public Object convertTime(String fieldValue) {
        return (long) (Double.parseDouble(fieldValue) * 1000.0);
    }

    /**
     * @see org.biomojo.benchmark.executor.ValueParser#convertMemory(java.lang.String)
     */
    @Override
    public Object convertMemory(String fieldValue) {
        return Long.parseLong(fieldValue) * 1024;
    }

}
