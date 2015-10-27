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
package org.biomojo.taxonomy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.biomojo.core.AbstractEntity;

@Entity
public class TaxonomyName extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String name;

    private String uniqueName;

    private String nameClass;

    @ManyToOne
    private TaxonomyNode taxonomyNode;

    public TaxonomyName() {
    }

    public TaxonomyName(String name, String nameClass, String uniqueName) {
        this.name = name;
        this.nameClass = nameClass;
        this.uniqueName = uniqueName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public TaxonomyNode getTaxonomyNode() {
        return taxonomyNode;
    }

    public void setTaxonomyNode(TaxonomyNode taxonomyNode) {
        this.taxonomyNode = taxonomyNode;
    }
}
