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

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SeqSubList<T extends Seq<?, ?>> extends AbstractSeqList<T> {
	/**
	 *
	 */
	private static final long serialVersionUID = -8154271883890794936L;

	@ManyToOne(targetEntity = AbstractSeqList.class)
	protected SeqList<T> targetList;

	private int fromIndex;

	private int toIndex;

	protected SeqSubList() {
	}

	public SeqSubList(final SeqList<T> sequenceList, final int fromIndex,
			final int toIndex) {
		this.targetList = sequenceList;
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	public int getFromIndex() {
		return fromIndex;
	}

	public int getToIndex() {
		return toIndex;
	}

	public SeqList<T> getTargetList() {
		return targetList;
	}

	@Override
	public T get(final int index) {
		return targetList.get(fromIndex + index);
	}

	@Override
	public T set(final int index, final T value) {
		return targetList.set(fromIndex + index, value);
	}

	@Override
	public int size() {
		return toIndex - fromIndex;
	}

	@Override
	public boolean add(final T value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> subList(final int from, final int to) {
		return new SeqSubList<T>(targetList, fromIndex + from, toIndex + to);
	}

	@Override
	public T remove(final int pos) {
		throw new UnsupportedOperationException();
	}
}