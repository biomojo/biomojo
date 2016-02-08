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
package org.biomojo.benchmark.framework.executor;

import java.math.BigDecimal;

/**
 * @author Hugh Eaves
 *
 */
public interface Field {

    public static final int DEFAULT_TYPE = 0;
    public static final int TIME_TYPE = 1;
    public static final int MEMORY_TYPE = 2;
    public static final int INT_TYPE = 3;
    public static final int STRING_TYPE = 4;
    public static final int BIG_DECIMAL_TYPE = 5;

    public int getFieldType();

    public int ordinal();

    public Object convertTime(String fieldValue);

    public Object convertMemory(String fieldValue);

    public default Object getValue(String[] allValues) {
        return (convert(allValues[ordinal()]));
    }

    public default Object convert(String fieldValue) {
        switch (getFieldType()) {
        case STRING_TYPE:
            return fieldValue;
        case TIME_TYPE:
            return convertTime(fieldValue);
        case MEMORY_TYPE:
            return convertMemory(fieldValue);
        case INT_TYPE:
            return Integer.parseInt(fieldValue);
        case DEFAULT_TYPE:
            return Long.parseLong(fieldValue);
        case BIG_DECIMAL_TYPE:
            return new BigDecimal(fieldValue);
        }
        return null;
    }
}
