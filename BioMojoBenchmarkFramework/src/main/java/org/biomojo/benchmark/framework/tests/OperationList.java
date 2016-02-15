package org.biomojo.benchmark.framework.tests;

import java.util.List;

import org.java0.collection.DefaultList;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public class OperationList implements DefaultList<Operation> {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(OperationList.class);

    @Override
    public Operation get(final int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Operation remove(final int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Operation set(final int index, final Operation operation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Operation> subList(final int fromIndex, final int toIndex) {
        throw new UnsupportedOperationException();
    }
}
