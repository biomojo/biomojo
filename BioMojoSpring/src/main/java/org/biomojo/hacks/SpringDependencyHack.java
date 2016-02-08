package org.biomojo.hacks;

import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;
import org.springframework.orm.jpa.vendor.SpringVendorDependencyHack;

public class SpringDependencyHack extends AbstractDependencyHack {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(SpringDependencyHack.class);

    public static SpringVendorDependencyHack unused1;
    public static HibernateDependencyHack unused2;

    @Override
    public void depend() {
        l(org.springframework.context.config.ContextNamespaceHandler.class);
        l(org.springframework.transaction.config.TxNamespaceHandler.class);
        l(org.springframework.beans.factory.xml.SimplePropertyNamespaceHandler.class);
        // l(org.springframework.beans.CachedIntrospectionResults.class);
        l(org.springframework.beans.ExtendedBeanInfoFactory.class);
        l(org.springframework.orm.jpa.JpaTransactionManager.class);
        l(org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.class);
        l(org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter.class);
        l(org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor.class);
    }
}
