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

package org.biomojo.property;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("N")
public abstract class NumericProperty extends BasicProperty {
	/**
	 *
	 */
	private static final long serialVersionUID = 8901783423194087216L;

	//
	// private short exponent;
	//
	// @Column(scale = 10, precision = 20)
	// private BigDecimal number;
	//
	// private boolean exact;

	public NumericProperty() {
	}
	//
	// public boolean isExact() {
	// return exact;
	// }
	//
	// @PrePersist
	// @PreUpdate
	// protected void onPrePersist() {
	// normalize();
	// }
	//
	// @PostLoad
	// protected void onPostLoad() {
	// denormalize();
	// }
	//
	// private void normalize() {
	// // TO DO Finish this
	// if (!isNormalized()) {
	// if (number instanceof BigDecimal) {
	// decimal = (BigDecimal) number;
	// } else if (number instanceof Double || number instanceof Float) {
	// decimal = new BigDecimal((Double) number);
	// } else if (number instanceof Integer || number instanceof Short
	// || number instanceof Byte) {
	// decimal = new BigDecimal(number.intValue());
	// } else if (number instanceof Long) {
	// decimal = new BigDecimal((Long) number);
	// } else if (number instanceof BigInteger) {
	// decimal = new BigDecimal((BigInteger) number);
	// } else {
	//
	// }
	// }
	// }
	//
	// private void denormalize() {
	//
	// }
}
