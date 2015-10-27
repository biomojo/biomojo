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

// TODO: Auto-generated Javadoc
/**
 * The Class TaxonomyNode.
 */
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

    /** The id. */
    @Id
    protected long id;

    /** The parent. */
    @ManyToOne
    private TaxonomyNode parent;

    /** The names. */
    @OneToMany(mappedBy = "taxonomyNode", cascade = CascadeType.ALL)
    private List<TaxonomyName> names = new ArrayList<TaxonomyName>();

    /** The rank. */
    private String rank;

    /** The embl code. */
    private String emblCode;

    /** The division. */
    @ManyToOne
    private TaxonomyDivision division;

    /** The inherited division. */
    private boolean inheritedDivision;

    /** The genetic code. */
    @ManyToOne
    private TaxonomyGeneticCode geneticCode;

    /** The inherited genetic code. */
    private boolean inheritedGeneticCode;

    /** The mc genetic code. */
    @ManyToOne
    private TaxonomyGeneticCode mcGeneticCode;

    /** The inherited mc genetic code. */
    private boolean inheritedMcGeneticCode;

    /** The hidden genbank name. */
    private boolean hiddenGenbankName;

    /** The hidden subtree. */
    private boolean hiddenSubtree;

    /** The comments. */
    private String comments;

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
     * Gets the parent.
     *
     * @return the parent
     */
    public TaxonomyNode getParent() {
        return parent;
    }

    /**
     * Sets the parent.
     *
     * @param parent the new parent
     */
    public void setParent(TaxonomyNode parent) {
        this.parent = parent;
    }

    /**
     * Gets the rank.
     *
     * @return the rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Sets the rank.
     *
     * @param rank the new rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * Gets the embl code.
     *
     * @return the embl code
     */
    public String getEmblCode() {
        return emblCode;
    }

    /**
     * Sets the embl code.
     *
     * @param emblCode the new embl code
     */
    public void setEmblCode(String emblCode) {
        this.emblCode = emblCode;
    }

    /**
     * Gets the division.
     *
     * @return the division
     */
    public TaxonomyDivision getDivision() {
        return division;
    }

    /**
     * Sets the division.
     *
     * @param division the new division
     */
    public void setDivision(TaxonomyDivision division) {
        this.division = division;
    }

    /**
     * Checks if is inherited division.
     *
     * @return true, if is inherited division
     */
    public boolean isInheritedDivision() {
        return inheritedDivision;
    }

    /**
     * Sets the inherited division.
     *
     * @param inheritedDivision the new inherited division
     */
    public void setInheritedDivision(boolean inheritedDivision) {
        this.inheritedDivision = inheritedDivision;
    }

    /**
     * Gets the genetic code.
     *
     * @return the genetic code
     */
    public TaxonomyGeneticCode getGeneticCode() {
        return geneticCode;
    }

    /**
     * Sets the genetic code.
     *
     * @param geneticCode the new genetic code
     */
    public void setGeneticCode(TaxonomyGeneticCode geneticCode) {
        this.geneticCode = geneticCode;
    }

    /**
     * Checks if is inherited genetic code.
     *
     * @return true, if is inherited genetic code
     */
    public boolean isInheritedGeneticCode() {
        return inheritedGeneticCode;
    }

    /**
     * Sets the inherited genetic code.
     *
     * @param inheritedGeneticCode the new inherited genetic code
     */
    public void setInheritedGeneticCode(boolean inheritedGeneticCode) {
        this.inheritedGeneticCode = inheritedGeneticCode;
    }

    /**
     * Gets the mc genetic code.
     *
     * @return the mc genetic code
     */
    public TaxonomyGeneticCode getMcGeneticCode() {
        return mcGeneticCode;
    }

    /**
     * Sets the mc genetic code.
     *
     * @param mcGeneticCode the new mc genetic code
     */
    public void setMcGeneticCode(TaxonomyGeneticCode mcGeneticCode) {
        this.mcGeneticCode = mcGeneticCode;
    }

    /**
     * Checks if is inherited mc genetic code.
     *
     * @return true, if is inherited mc genetic code
     */
    public boolean isInheritedMcGeneticCode() {
        return inheritedMcGeneticCode;
    }

    /**
     * Sets the inherited mc genetic code.
     *
     * @param inheritedMcGeneticCode the new inherited mc genetic code
     */
    public void setInheritedMcGeneticCode(boolean inheritedMcGeneticCode) {
        this.inheritedMcGeneticCode = inheritedMcGeneticCode;
    }

    /**
     * Checks if is hidden genbank name.
     *
     * @return true, if is hidden genbank name
     */
    public boolean isHiddenGenbankName() {
        return hiddenGenbankName;
    }

    /**
     * Sets the hidden genbank name.
     *
     * @param hiddenName the new hidden genbank name
     */
    public void setHiddenGenbankName(boolean hiddenName) {
        this.hiddenGenbankName = hiddenName;
    }

    /**
     * Checks if is hidden subtree.
     *
     * @return true, if is hidden subtree
     */
    public boolean isHiddenSubtree() {
        return hiddenSubtree;
    }

    /**
     * Sets the hidden subtree.
     *
     * @param hiddenSubtree the new hidden subtree
     */
    public void setHiddenSubtree(boolean hiddenSubtree) {
        this.hiddenSubtree = hiddenSubtree;
    }

    /**
     * Gets the comments.
     *
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the comments.
     *
     * @param comments the new comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Gets the full classification.
     *
     * @return the full classification
     */
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("id: " + id + "\n");
        buffer.append("rank: " + rank + "\n");
        return (buffer.toString());
    }

    /**
     * Gets the name.
     *
     * @param name the name
     * @param nameClass the name class
     * @return the name
     */
    public TaxonomyName getName(String name, String nameClass) {
        for (TaxonomyName taxonomyName : names) {
            if (name.equals(taxonomyName.getName()) && nameClass.equals(taxonomyName.getNameClass())) {
                return taxonomyName;
            }
        }
        return null;
    }

    /**
     * Adds the name.
     *
     * @param taxonomyName the taxonomy name
     */
    public void addName(TaxonomyName taxonomyName) {
        names.add(taxonomyName);
        taxonomyName.setTaxonomyNode(this);
    }
}
