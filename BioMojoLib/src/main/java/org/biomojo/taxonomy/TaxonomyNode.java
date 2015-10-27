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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

//@NamedQuery(name = "findTaxonomyNodeByTaxonomyId", query = "select taxonomyNode from TaxonomyNode as taxonomyNode where taxonomyNode.taxonomyId = :taxonomyId")
@Entity
public class TaxonomyNode {
    // tax_id -- node id in GenBank taxonomy database
    // parent tax_id -- parent node id in GenBank taxonomy database
    // rank -- rank of this node (superkingdom, kingdom, ...)
    // embl code -- locus-name prefix; not unique
    // division id -- see division.dmp file
    // inherited div flag (1 or 0) -- 1 if node inherits division from parent
    // genetic code id -- see gencode.dmp file
    // inherited GC flag (1 or 0) -- 1 if node inherits genetic code from parent
    // mitochondrial genetic code id -- see gencode.dmp file
    // inherited MGC flag (1 or 0) -- 1 if node inherits mitochondrial gencode
    // from parent
    // GenBank hidden flag (1 or 0) -- 1 if name is suppressed in GenBank entry
    // lineage
    // hidden subtree root flag (1 or 0) -- 1 if this subtree has no rawData
    // data yet
    // comments -- free-text comments and citations

    @Id
    protected long id;

    @ManyToOne
    private TaxonomyNode parent;

    @OneToMany(mappedBy = "taxonomyNode", cascade = CascadeType.ALL)
    private List<TaxonomyName> names = new ArrayList<TaxonomyName>();

    private String rank;

    private String emblCode;

    @ManyToOne
    private TaxonomyDivision division;

    private boolean inheritedDivision;

    @ManyToOne
    private TaxonomyGeneticCode geneticCode;

    private boolean inheritedGeneticCode;

    @ManyToOne
    private TaxonomyGeneticCode mcGeneticCode;

    private boolean inheritedMcGeneticCode;

    private boolean hiddenGenbankName;

    private boolean hiddenSubtree;

    private String comments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TaxonomyNode getParent() {
        return parent;
    }

    public void setParent(TaxonomyNode parent) {
        this.parent = parent;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEmblCode() {
        return emblCode;
    }

    public void setEmblCode(String emblCode) {
        this.emblCode = emblCode;
    }

    public TaxonomyDivision getDivision() {
        return division;
    }

    public void setDivision(TaxonomyDivision division) {
        this.division = division;
    }

    public boolean isInheritedDivision() {
        return inheritedDivision;
    }

    public void setInheritedDivision(boolean inheritedDivision) {
        this.inheritedDivision = inheritedDivision;
    }

    public TaxonomyGeneticCode getGeneticCode() {
        return geneticCode;
    }

    public void setGeneticCode(TaxonomyGeneticCode geneticCode) {
        this.geneticCode = geneticCode;
    }

    public boolean isInheritedGeneticCode() {
        return inheritedGeneticCode;
    }

    public void setInheritedGeneticCode(boolean inheritedGeneticCode) {
        this.inheritedGeneticCode = inheritedGeneticCode;
    }

    public TaxonomyGeneticCode getMcGeneticCode() {
        return mcGeneticCode;
    }

    public void setMcGeneticCode(TaxonomyGeneticCode mcGeneticCode) {
        this.mcGeneticCode = mcGeneticCode;
    }

    public boolean isInheritedMcGeneticCode() {
        return inheritedMcGeneticCode;
    }

    public void setInheritedMcGeneticCode(boolean inheritedMcGeneticCode) {
        this.inheritedMcGeneticCode = inheritedMcGeneticCode;
    }

    public boolean isHiddenGenbankName() {
        return hiddenGenbankName;
    }

    public void setHiddenGenbankName(boolean hiddenName) {
        this.hiddenGenbankName = hiddenName;
    }

    public boolean isHiddenSubtree() {
        return hiddenSubtree;
    }

    public void setHiddenSubtree(boolean hiddenSubtree) {
        this.hiddenSubtree = hiddenSubtree;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Map<String, TaxonomyNode> getFullClassification() {
        Map<String, TaxonomyNode> classification;
        if (getParent() != null && getParent().getId() != this.getId()) {
            classification = getParent().getFullClassification();
        } else {
            classification = new HashMap<String, TaxonomyNode>();
        }
        if (!"no rank".equals(getRank())) {
            classification.put(getRank(), this);
        }
        return classification;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("id: " + id + "\n");
        buffer.append("rank: " + rank + "\n");
        return (buffer.toString());
    }

    public TaxonomyName getName(String name, String nameClass) {
        for (TaxonomyName taxonomyName : names) {
            if (name.equals(taxonomyName.getName()) && nameClass.equals(taxonomyName.getNameClass())) {
                return taxonomyName;
            }
        }
        return null;
    }

    public void addName(TaxonomyName taxonomyName) {
        names.add(taxonomyName);
        taxonomyName.setTaxonomyNode(this);
    }
}
