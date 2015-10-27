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

// TODO: Auto-generated Javadoc
/**
 * The Class TaxonomyName.
 */
@Entity
public class TaxonomyName extends AbstractEntity {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;

    /** The unique name. */
    private String uniqueName;

    /** The name class. */
    private String nameClass;

    /** The taxonomy node. */
    @ManyToOne
    private TaxonomyNode taxonomyNode;

    /**
     * Instantiates a new taxonomy name.
     */
    public TaxonomyName() {
    }

    /**
     * Instantiates a new taxonomy name.
     *
     * @param name the name
     * @param nameClass the name class
     * @param uniqueName the unique name
     */
    public TaxonomyName(String name, String nameClass, String uniqueName) {
        this.name = name;
        this.nameClass = nameClass;
        this.uniqueName = uniqueName;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the unique name.
     *
     * @return the unique name
     */
    public String getUniqueName() {
        return uniqueName;
    }

    /**
     * Sets the unique name.
     *
     * @param uniqueName the new unique name
     */
    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    /**
     * Gets the name class.
     *
     * @return the name class
     */
    public String getNameClass() {
        return nameClass;
    }

    /**
     * Sets the name class.
     *
     * @param nameClass the new name class
     */
    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    /**
     * Gets the taxonomy node.
     *
     * @return the taxonomy node
     */
    public TaxonomyNode getTaxonomyNode() {
        return taxonomyNode;
    }

    /**
     * Sets the taxonomy node.
     *
     * @param taxonomyNode the new taxonomy node
     */
    public void setTaxonomyNode(TaxonomyNode taxonomyNode) {
        this.taxonomyNode = taxonomyNode;
    }
}
