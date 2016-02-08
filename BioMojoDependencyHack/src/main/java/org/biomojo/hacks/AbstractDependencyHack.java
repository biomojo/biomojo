package org.biomojo.hacks;

public abstract class AbstractDependencyHack {
    public int hashCode = 0;

    public void l(final Class<?> c) {
        if (c != null) {
            hashCode += c.hashCode();
        }
    }

    public abstract void depend();
}
