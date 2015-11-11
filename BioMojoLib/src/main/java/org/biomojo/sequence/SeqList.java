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
package org.biomojo.sequence;

import org.biomojo.core.Described;
import org.biomojo.core.Identified;
import org.biomojo.property.Propertied;
import org.java0.collection.DefaultList;

/**
 * The Interface SeqList.
 *
 * @param <T> the generic type
 */
public interface SeqList<T extends Seq<?, ?>> extends DefaultList<T>, Propertied, Described, Identified {

}
