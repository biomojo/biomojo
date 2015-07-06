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

package org.biomojo.examples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.inject.Named;

import org.java0.core.exception.UncheckedException;

@Named
public class VelvetStats {

	// private static final Logger logger = Logger
	// .getLogger(VelvetStats.class.getName());

	public static final int KMER_LENGTH = 51;

	public VelvetStats() {
	}

	public void createStats() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"/home/hugh/stats.txt"));
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"/home/hugh/histogram.csv"));

			HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
			HashMap<Integer, Double> coverageSums = new HashMap<Integer, Double>();

			String line = null;
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] values = line.split("\t");
				int id = Integer.parseInt(values[0]);
				if (id % 10000 == 0) {
					System.out.println("id = " + id);
				}
				int length = Integer.parseInt(values[1]) + KMER_LENGTH + 1;
				double coverage = 0;
				if (!values[5].equals("Inf")) {
					coverage = Double.parseDouble(values[5]);
				}
				Integer count;
				if ((count = counts.get(length)) == null) {
					counts.put(length, 1);
				} else {
					counts.put(length, count + 1);
				}
				Double sum;
				if ((sum = coverageSums.get(length)) == null) {
					coverageSums.put(length, coverage);
				} else {
					coverageSums.put(length, sum + coverage);
				}
			}

			for (int count : counts.keySet()) {
				writer.write(count + "\t" + counts.get(count) + "\t"
						+ coverageSums.get(count) + "\n");
			}

			reader.close();
			writer.close();
		} catch (IOException e) {
			throw new UncheckedException(e);
		}
	}

}
