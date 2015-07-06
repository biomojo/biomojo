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

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.biomojo.alphabet.ByteAlphabet;
import org.biomojo.blast.blastoutput.BlastHit;
import org.biomojo.blast.blastoutput.BlastIteration;
import org.biomojo.blast.blastoutput.BlastOutput;
import org.biomojo.sequence.ByteSeqImpl;

@Named
public class SequenceIdSequenceResolver implements BlastSequenceResolver {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void resolveSequences(final BlastOutput blastOutput) {
		for (final BlastIteration iteration : blastOutput.getIterations()) {
			final long queryId = Long.parseLong(iteration.getQueryDef());
			final ByteSeqImpl<ByteAlphabet> querySequence = entityManager.find(
					ByteSeqImpl.class, queryId);
			iteration.setQuerySequence(querySequence);
			for (final BlastHit hit : iteration.getHits()) {
				final long hitId = Long.parseLong(hit.getHitDef());
				final ByteSeqImpl<ByteAlphabet> hitSequence = entityManager.find(
						ByteSeqImpl.class, hitId);
				hit.setHitSequence(hitSequence);
			}
		}
	}
}
