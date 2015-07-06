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
package org.biomojo.alphabet;

/**
 * @author Hugh Eaves
 *
 */
public class IUPACAlphabetVariant {
	public static final int WITH_NON_CANONICAL = 1;
	public static final int WITH_GAP = 2;
	public static final int WITH_ANY = 4;
	public static final int WITH_AMBIGIGUITY = 8;
	public static final int NUM_VARIANTS = 16;
}
