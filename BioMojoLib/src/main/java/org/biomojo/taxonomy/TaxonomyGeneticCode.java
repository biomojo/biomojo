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
import javax.persistence.Id;

// TODO: Auto-generated Javadoc
//@NamedQuery(name = "findTaxonomyGeneticCodeByGeneticCodeId", query = "select taxonomyGeneticCode from TaxonomyGeneticCode as taxonomyGeneticCode where taxonomyGeneticCode.geneticCodeId = :geneticCodeId")

/**
 * The Class TaxonomyGeneticCode.
 */
@Entity
public class TaxonomyGeneticCode {
    
    /** The id. */
    @Id
    protected long id;

    /** The abbreviation. */
    private String abbreviation;
    
    /** The name. */
    private String name;
    
    /** The translation table. */
    private String translationTable;
    
    /** The start codons. */
    private String startCodons;

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
     * Gets the abbreviation.
     *
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Sets the abbreviation.
     *
     * @param abbreviation the new abbreviation
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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
     * Gets the translation table.
     *
     * @return the translation table
     */
    public String getTranslationTable() {
        return translationTable;
    }

    /**
     * Sets the translation table.
     *
     * @param translationTable the new translation table
     */
    public void setTranslationTable(String translationTable) {
        this.translationTable = translationTable;
    }

    /**
     * Gets the start codons.
     *
     * @return the start codons
     */
    public String getStartCodons() {
        return startCodons;
    }

    /**
     * Sets the start codons.
     *
     * @param startCodons the new start codons
     */
    public void setStartCodons(String startCodons) {
        this.startCodons = startCodons;
    }
}
