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

import java.util.List;

import org.biomojo.alphabet.Gappable;
import org.biomojo.alphabet.Gapped;
import org.biomojo.sequence.Seq;

/**
 * The Interface Aligner.
 *
 * @author Hugh Eaves
 * @param <T>
 *            the generic type
 */
public interface Aligner<T, A extends Gappable<T, A, G>, G extends Gapped<T, A>, S extends Seq<T, G>, U extends Seq<T, A>> {

    /**
     * Align.
     *
     * @param sequences
     *            the sequences
     * @return the alignment
     */
    public Alignment<T, A, G, S> align(List<U> sequences);
}
