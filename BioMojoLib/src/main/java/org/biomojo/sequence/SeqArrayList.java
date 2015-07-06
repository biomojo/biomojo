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
package org.biomojo.sequence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;

import org.biomojo.core.CommonProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class SeqArrayList<T extends Seq<?, ?>> extends AbstractSeqList<T> {

	/**
     *
     */
	private static final long serialVersionUID = 5511937136125948750L;

	@ManyToMany(targetEntity = AbstractSeq.class)
	@OrderColumn
	@LazyCollection(LazyCollectionOption.EXTRA)
	protected List<T> sequences = new ArrayList<>();

	public SeqArrayList() {

	}

	/**
	 * Create a new BasicMultiSequence.
	 *
	 * @param name
	 */
	public SeqArrayList(final String name) {
		setProp(CommonProperties.NAME, name);
	}

	@Override
	public T get(final int index) {
		return sequences.get(index);
	}

	@Override
	public T set(final int index, final T value) {
		return sequences.get(index);
	}

	@Override
	public int size() {
		return sequences.size();
	}

	@Override
	public List<T> subList(final int start, final int end) {
		return new SeqSubList<T>(this, start, end);
	}

	@Override
	public boolean add(final T e) {
		return sequences.add(e);
	}

	@Override
	public T remove(final int pos) {
		return sequences.remove(pos);
	}
}
