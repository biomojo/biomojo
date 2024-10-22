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
package org.biomojo.alignment;

import org.biomojo.GlobalConst;

// TODO: Auto-generated Javadoc
/**
 * The Interface SubstitutionMatrices.
 *
 * @author Hugh Eaves
 */
public interface SubstitutionMatrices {

    public static final long START = GlobalConst.GROUP_ID * 4L;

    /** The Constant BLOSUM. */
    public static final int BLOSUM = 1;

    /** The Constant PAM. */
    public static final int PAM = 2;

    /** The Constant BLOSUMN. */
    public static final int BLOSUMN = 0;

    /** The Constant DAYHOFF. */
    public static final int DAYHOFF = 0;

    /** The Constant GONNET. */
    public static final int GONNET = 0;

    /** The Constant IDENTITY. */
    public static final int IDENTITY = 0;

    /** The Constant MATCH. */
    public static final int MATCH = 0;

    /** The Constant NUC_4_2. */
    public static final int NUC_4_2 = 0;

    /** The Constant NUC_4_4. */
    public static final int NUC_4_4 = 0;
}
