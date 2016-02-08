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
package org.biomojo.benchmark.executor;

public enum LinuxProcStatField implements Field {

    PID(INT_TYPE), COMMAND(STRING_TYPE), STATE(STRING_TYPE), PPID(INT_TYPE), PGRP(INT_TYPE), SESSION(INT_TYPE), TTY_NR(
            INT_TYPE), TPGID(INT_TYPE), FLAGS(
                    INT_TYPE), MINOR_FAULTS, CHILD_MINOR_FAULTS, MAJOR_FAULTS, CHILD_MAJOR_FAULTS, USER_TIME(
                            TIME_TYPE), SYSTEM_TIME(TIME_TYPE), CHILD_USER_TIME(TIME_TYPE), CHILD_SYSTEM_TIME(
                                    TIME_TYPE), PRIORITY, NICE, NUM_THREADS, ITREALVALUE, STARTTIME, TOTAL_MEM(
                                            MEMORY_TYPE), RSS(MEMORY_TYPE), RSS_LIMIT(
                                                    BIG_DECIMAL_TYPE), STARTCODE, ENDCODE, STARTSTACK, KSTKESP, KSTKEIP, SIGNAL, BLOCKED, SIGIGNORE, SIGCATCH, WCHAN(
                                                            BIG_DECIMAL_TYPE), NSWAP, CNSWAP, EXIT_SIGNAL(
                                                                    INT_TYPE), PROCESSOR(INT_TYPE), RT_PRIORITY(
                                                                            INT_TYPE), POLICY(
                                                                                    INT_TYPE), DELAYACCT_BLKIO_TICKS, GUEST_TIME, CGUEST_TIME, START_DATA, END_DATA, START_BRK, ARG_START, ARG_END, ENV_START, ENV_END, EXIT_CODE(
                                                                                            INT_TYPE);

    int fieldType;

    LinuxProcStatField() {

    }

    LinuxProcStatField(int fieldType) {
        this.fieldType = fieldType;
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
        return Long.parseLong(fieldValue) * (1000L / 100L);
    }

    /**
     * @see org.biomojo.benchmark.executor.ValueParser#convertMemory(java.lang.String)
     */
    @Override
    public Object convertMemory(String fieldValue) {
        return Long.parseLong(fieldValue) * 4096;
    }
}