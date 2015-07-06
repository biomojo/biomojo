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

package org.biomojo.blast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.core.AbstractPropertiedEntity;
import org.biomojo.core.CommonProperties;
import org.biomojo.sequence.ByteSeqImpl;
import org.biomojo.sequence.AbstractSeqList;

@Entity
public class BlastDataSet extends AbstractPropertiedEntity {
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private BlastProgram program;

	@OneToMany(mappedBy = "blastDataSet", cascade = CascadeType.ALL)
	private List<BlastTask> blastTasks = new ArrayList<BlastTask>();

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "BlastDataSet_QuerySequenceLists")
	private List<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>> querySequenceLists = new ArrayList<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>>();

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "BlastDataSet_DatabaseSequenceLists")
	private List<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>> databaseSequenceLists = new ArrayList<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>>();

	private Date dateCreated;

	public BlastDataSet() {
	}

	/**
	 * Create a new BlastDataSet.
	 *
	 * @param datasetName
	 */
	public BlastDataSet(final String datasetName) {
		setProp(CommonProperties.NAME, datasetName);
	}

	public List<BlastTask> getBlastTasks() {
		return blastTasks;
	}

	public void setBlastTasks(final List<BlastTask> blastTasks) {
		this.blastTasks = blastTasks;
	}

	public List<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>> getQuerySequenceLists() {
		return (List<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>>) querySequenceLists;
	}

	public void setQuerySequenceLists(
			final List<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>> querySequenceLists) {
		this.querySequenceLists = (List<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>>) querySequenceLists;
	}

	public List<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>> getDatabaseSequenceLists() {
		return (List<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>>) databaseSequenceLists;
	}

	public void setDatabaseSequenceLists(
			final List<AbstractSeqList<ByteSeqImpl<ByteAlphabet>>> databaseSequenceLists) {
		this.databaseSequenceLists = databaseSequenceLists;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(final Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public BlastProgram getProgram() {
		return program;
	}

	public void setProgram(final BlastProgram program) {
		this.program = program;
	}

	public void add(final BlastTask blastTask) {
		getBlastTasks().add(blastTask);
		blastTask.setBlastDataSet(this);
	}
}
