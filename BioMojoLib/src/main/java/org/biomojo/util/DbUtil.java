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

package org.biomojo.util;

import java.util.Collection;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Declare this class as a @Named, so that Spring "knows about it"
@Named
public class DbUtil {

    private static final Logger logger = LoggerFactory.getLogger(DbUtil.class.getName());

    // This annotation tells Spring to pass a reference to
    // the EntityManager to this class when an instance is created
    @PersistenceContext
    private EntityManager entityManager;

    public <T> T findByAttribute(Class<T> resultClass, String attributeKey, Long attributeValue) {
        return findByAttributeInternal(resultClass, "long", attributeKey, attributeValue);
    }

    public <T> T findByAttribute(Class<T> resultClass, String attributeKey, Double attributeValue) {
        return findByAttributeInternal(resultClass, "double", attributeKey, attributeValue);
    }

    public <T> T findByAttribute(Class<T> resultClass, String attributeKey, String attributeValue) {
        return findByAttributeInternal(resultClass, "string", attributeKey, attributeValue);
    }

    protected <T> T findByAttributeInternal(Class<T> resultClass, String attributeType, String attributeKey,
            Object attributeValue) {
        try {
            TypedQuery<T> query = entityManager.createQuery("select obj from " + resultClass.getName()
                    + " obj inner join treat(obj.attributes as " + attributeType + "Attribute" + ") a where key(a) = "
                    + ":attributeKey and a." + attributeType + "Value = :attributeValue", resultClass);

            query.setParameter("attributeKey", attributeKey);
            query.setParameter("attributeValue", attributeValue);
            query.setHint("org.hibernate.cacheable", true);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void clearCache(Object object) {
        entityManager.flush();
        entityManager.detach(object);
    }

    public void clearCache(Collection<Object> objects) {
        entityManager.flush();
        for (Object object : objects) {
            entityManager.detach(object);
        }
    }

    public void logStats(String comment) {
        if (entityManager instanceof HibernateEntityManager) {
            Session session = ((HibernateEntityManager) entityManager).getSession();
            logger.info("STATS: " + comment);
            session.getSessionFactory().getStatistics().logSummary();
        } else {
            logger.warn("Entity Manager is not a hibernate entity manager");
        }
    }

}
