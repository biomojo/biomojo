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
package org.biomojo.taxonomy;

import javax.persistence.Id;
import javax.persistence.OneToOne;

// TODO: Auto-generated Javadoc
/**
 * The Class GiTaxonomy.
 */
//@Entity
public class GiTaxonomy {
    
    /** The id. */
    @Id
    protected long id;

    /** The taxonomy. */
    @OneToOne
    private TaxonomyNode taxonomy;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the taxonomy.
     *
     * @return the taxonomy
     */
    public TaxonomyNode getTaxonomy() {
        return taxonomy;
    }

    /**
     * Sets the taxonomy.
     *
     * @param taxonomy the new taxonomy
     */
    public void setTaxonomy(TaxonomyNode taxonomy) {
        this.taxonomy = taxonomy;
    }
}
