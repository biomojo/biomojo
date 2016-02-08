package org.biomojo.hacks;

import org.biomojo.hacks.AbstractDependencyHack;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class HibernateDependencyHack extends AbstractDependencyHack {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(HibernateDependencyHack.class);

    @Override
    public void depend() {
        l(com.mchange.v2.c3p0.ComboPooledDataSource.class);
        l(com.mchange.v2.c3p0.management.ActiveManagementCoordinator.class);

        l(javax.persistence.PersistenceUnit.class);

        l(net.sf.ehcache.store.DefaultElementValueComparator.class);

        l(org.hibernate.annotations.common.util.impl.Log_$logger.class);
        l(org.hibernate.cache.ehcache.EhCacheMessageLogger_$logger.class);
        l(org.hibernate.cache.ehcache.StrategyRegistrationProviderImpl.class);
        l(org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory.class);
        l(org.hibernate.internal.CoreMessageLogger_$logger.class);
        l(org.hibernate.jpa.HibernatePersistenceProvider.class);
        l(org.hibernate.jpa.internal.EntityManagerMessageLogger_$logger.class);

        l(org.jboss.logging.BasicLogger.class);
    }
}
