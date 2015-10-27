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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.biomojo.sequence.SequenceService;
import org.java0.core.exception.UncheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class TaxonomyService.
 */
@Named
public class TaxonomyService {
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(SequenceService.class.getName());

    /** The entity manager. */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Load divisisions.
     *
     * @param divisions the divisions
     */
    @Transactional
    public void loadDivisisions(final InputStream divisions) {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(divisions));

            for (String line; (line = reader.readLine()) != null;) {
                final String[] fields = splitLine(line);
                if (fields.length != 4) {
                    throw new UncheckedException("Invalid Taxonomy Division file");
                }

                TaxonomyDivision division = findDivision(fields[0]);
                if (division == null) {
                    division = new TaxonomyDivision();
                    division.setId(Long.parseLong(fields[0]));
                    entityManager.persist(division);
                }

                division.setCode(fields[1]);
                division.setName(fields[2]);
                division.setComments(fields[3]);
            }
        } catch (final IOException e) {
            throw new UncheckedException(e);
        }
    }

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

    /**
     * Load taxonomy nodes.
     *
     * @param taxonomyNodes the taxonomy nodes
     */
    @Transactional
    public void loadTaxonomyNodes(final InputStream taxonomyNodes) {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(taxonomyNodes));

            final Map<Long, TaxonomyNode> nodeCache = new HashMap<Long, TaxonomyNode>();

            int lineCount = 0;
            for (String line; (line = reader.readLine()) != null;) {
                ++lineCount;
                if (lineCount % 1000 == 0) {
                    logger.info("Processing line " + lineCount);
                }

                final String[] fields = splitLine(line);

                if (fields.length != 13) {
                    throw new UncheckedException("Invalid Taxonomy Nodes file");
                }

                final long taxonomyId = Long.parseLong(fields[0]);
                TaxonomyNode taxonomyNode = findTaxonomyNode(taxonomyId, nodeCache);
                if (taxonomyNode == null) {
                    // logger.info("Creating node for id " + taxonomyId);
                    taxonomyNode = new TaxonomyNode();
                    taxonomyNode.setId(taxonomyId);
                    entityManager.persist(taxonomyNode);
                    nodeCache.put(taxonomyId, taxonomyNode);
                }

                final long parentTaxonomyId = Long.parseLong(fields[1]);
                TaxonomyNode parentTaxonomyNode = findTaxonomyNode(parentTaxonomyId, nodeCache);
                if (parentTaxonomyNode == null) {
                    // logger.info("Creating parent node for id " +
                    // parentTaxonomyId);
                    parentTaxonomyNode = new TaxonomyNode();
                    parentTaxonomyNode.setId(parentTaxonomyId);
                    entityManager.persist(parentTaxonomyNode);
                    nodeCache.put(parentTaxonomyId, parentTaxonomyNode);
                }

                // logger.info("Node Cache:");
                // for (Long l : nodeCache.keySet()) {
                // logger.info ("l = " + l);
                // }

                taxonomyNode.setParent(parentTaxonomyNode);
                taxonomyNode.setRank(fields[2]);
                taxonomyNode.setEmblCode(fields[3]);

                final TaxonomyDivision division = findDivision(fields[4]);
                if (division != null) {
                    taxonomyNode.setDivision(division);
                } else {
                    throw new UncheckedException("Invalid Taxonomy Nodes file - taxonomy division not found");
                }

                taxonomyNode.setInheritedDivision(translateBoolean(fields[5]));

                final TaxonomyGeneticCode geneticCode = findGeneticCode(fields[6]);
                if (geneticCode != null) {
                    taxonomyNode.setGeneticCode(geneticCode);
                } else {
                    throw new UncheckedException("Invalid Taxonomy Nodes file - genetic code not found");
                }

                taxonomyNode.setInheritedGeneticCode(translateBoolean(fields[7]));

                final TaxonomyGeneticCode mcGeneticCode = findGeneticCode(fields[8]);
                if (mcGeneticCode != null) {
                    taxonomyNode.setMcGeneticCode(mcGeneticCode);
                } else {
                    throw new UncheckedException("Invalid Taxonomy Nodes file - mitochondrial genetic code not found");
                }

                taxonomyNode.setInheritedMcGeneticCode(translateBoolean(fields[9]));

                taxonomyNode.setHiddenGenbankName(translateBoolean(fields[10]));
                taxonomyNode.setHiddenSubtree(translateBoolean(fields[11]));
                taxonomyNode.setComments(fields[12]);

            }
        } catch (final IOException e) {
            throw new UncheckedException(e);
        }

    }

    /**
     * Load names.
     *
     * @param taxaNames the taxa names
     */
    @Transactional
    public void loadNames(final InputStream taxaNames) {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(taxaNames));

            int lineCount = 0;
            for (String line; (line = reader.readLine()) != null;) {
                ++lineCount;
                if (lineCount % 100 == 0) {
                    logger.info("Processing line " + lineCount);
                }

                final String[] fields = splitLine(line);
                if (fields.length != 4) {
                    throw new UncheckedException("Invalid Taxonomy Name file");
                }

                final long taxonomyId = Long.parseLong(fields[0]);
                TaxonomyNode taxonomyNode = findTaxonomyNode(taxonomyId, null);
                if (taxonomyNode == null) {
                    taxonomyNode = new TaxonomyNode();
                    taxonomyNode.setId(taxonomyId);
                    entityManager.persist(taxonomyNode);
                }

                final String name = fields[1];
                final String uniqueName = fields[2];
                final String nameClass = fields[3];
                TaxonomyName taxonomyName = taxonomyNode.getName(name, nameClass);
                if (taxonomyName == null) {
                    taxonomyName = new TaxonomyName(name, nameClass, uniqueName);
                    taxonomyNode.addName(taxonomyName);
                } else {
                    taxonomyName.setUniqueName(uniqueName);
                }
            }
        } catch (final IOException e) {
            throw new UncheckedException(e);
        }
    }

    /**
     * Load genetic codes.
     *
     * @param geneticCodes the genetic codes
     */
    @Transactional
    public void loadGeneticCodes(final InputStream geneticCodes) {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(geneticCodes));

            for (String line; (line = reader.readLine()) != null;) {
                final String[] fields = splitLine(line);
                if (fields.length != 5) {
                    throw new UncheckedException("Invalid Genetic Codes file");
                }

                TaxonomyGeneticCode taxonomyGeneticCode = findGeneticCode(fields[0]);
                if (taxonomyGeneticCode == null) {
                    taxonomyGeneticCode = new TaxonomyGeneticCode();
                    taxonomyGeneticCode.setId(Long.parseLong(fields[0]));
                    entityManager.persist(taxonomyGeneticCode);
                }

                taxonomyGeneticCode.setAbbreviation(fields[1]);
                taxonomyGeneticCode.setName(fields[2]);
                taxonomyGeneticCode.setTranslationTable(fields[3]);
                taxonomyGeneticCode.setStartCodons(fields[4]);
            }
        } catch (final IOException e) {
            throw new UncheckedException(e);
        }

    }

    /**
     * Translate boolean.
     *
     * @param string the string
     * @return true, if successful
     */
    private boolean translateBoolean(final String string) {
        if ("1".equals(string)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Find division.
     *
     * @param divisionIdStr the division id str
     * @return the taxonomy division
     */
    @Transactional
    private TaxonomyDivision findDivision(final String divisionIdStr) {
        final long divisionId = Integer.parseInt(divisionIdStr);
        final TaxonomyDivision division = entityManager.find(TaxonomyDivision.class, divisionId);
        return division;
    }

    /**
     * Find genetic code.
     *
     * @param codeId the code id
     * @return the taxonomy genetic code
     */
    @Transactional
    private TaxonomyGeneticCode findGeneticCode(final String codeId) {
        final long geneticCodeId = Integer.parseInt(codeId);
        final TaxonomyGeneticCode geneticCode = entityManager.find(TaxonomyGeneticCode.class, geneticCodeId);
        return geneticCode;

    }

    /**
     * Find taxonomy node.
     *
     * @param taxonomyId the taxonomy id
     * @param nodeCache the node cache
     * @return the taxonomy node
     */
    @Transactional
    private TaxonomyNode findTaxonomyNode(final long taxonomyId, final Map<Long, TaxonomyNode> nodeCache) {

        if (nodeCache != null) {
            final TaxonomyNode node = nodeCache.get(taxonomyId);
            if (node != null) {
                return node;
            }
        }
        final TaxonomyNode node = entityManager.find(TaxonomyNode.class, taxonomyId);

        return node;
    }

    /**
     * Split line.
     *
     * @param line the line
     * @return the string[]
     */
    private String[] splitLine(final String line) {
        final String[] fields = line.split("\t\\|");
        for (int i = 0; i < fields.length; ++i) {
            fields[i] = fields[i].trim();
        }
        return fields;
    }
};
