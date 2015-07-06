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

//@NamedQuery(name = "findTaxonomyGeneticCodeByGeneticCodeId", query = "select taxonomyGeneticCode from TaxonomyGeneticCode as taxonomyGeneticCode where taxonomyGeneticCode.geneticCodeId = :geneticCodeId")

@Entity
public class TaxonomyGeneticCode {
    @Id
    protected long id;

    private String abbreviation;
    private String name;
    private String translationTable;
    private String startCodons;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslationTable() {
        return translationTable;
    }

    public void setTranslationTable(String translationTable) {
        this.translationTable = translationTable;
    }

    public String getStartCodons() {
        return startCodons;
    }

    public void setStartCodons(String startCodons) {
        this.startCodons = startCodons;
    }
}
