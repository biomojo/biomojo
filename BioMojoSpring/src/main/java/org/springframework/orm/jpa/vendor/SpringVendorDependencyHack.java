package org.springframework.orm.jpa.vendor;

import org.biomojo.hacks.AbstractDependencyHack;

public class SpringVendorDependencyHack extends AbstractDependencyHack {
    @Override
    public void depend() {
        l(org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.class);
    }

}
