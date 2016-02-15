package org.biomojo.benchmark.framework.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.java0.core.exception.UncheckedException;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

public abstract class AbstractPreparer implements Operation {
    private static final Logger logger = LoggerFactory.getLogger(AbstractPreparer.class);

    private final Set<String> requiredPresent = new HashSet<>();
    private final Set<String> requiredAbsent = new HashSet<>();
    private final List<String> inputParams = new ArrayList<>();
    private final List<String> outputParams = new ArrayList<>();

    protected AbstractPreparer() {
    }

    protected void setRequiredPresent(final String... keys) {
        addKeys(requiredPresent, keys);
    }

    protected void setRequiredAbsent(final String... keys) {
        addKeys(requiredAbsent, keys);
    }

    protected void setInputParams(final String... keys) {
        addKeys(inputParams, keys);
        addKeys(requiredPresent, keys);
    }

    protected void setOutputParams(final String... keys) {
        addKeys(outputParams, keys);
        addKeys(requiredAbsent, keys);
    }

    private void addKeys(final Collection<String> collection, final String[] keys) {
        for (final String key : keys) {
            collection.add(key);
        }
    }

    @Override
    public void execute(final TestParameters params) {
        if (checkAllPresent(requiredPresent, params) && checkAllAbsent(requiredAbsent, params)) {

            safeExecute(params);

            if (!checkAllPresent(outputParams, params)) {
                final Set<String> missing = findMissing(outputParams, params);
                throw new UncheckedException(
                        "Missing output parameters " + missing + " in " + this.getClass().getName());
            }

        } else {
            logger.info("skipping preparer");
        }
    }

    public abstract void safeExecute(TestParameters parameters);

    private boolean checkAllPresent(final Collection<String> keys, final TestParameters params) {
        return findMissing(keys, params).size() == 0;
    }

    private Set<String> findMissing(final Collection<String> keys, final TestParameters params) {
        final Set<String> missing = new HashSet<>();
        ;
        for (final String key : keys) {
            if (params.get(key) == null) {
                missing.add(key);
            }
        }
        return missing;
    }

    private boolean checkAllAbsent(final Collection<String> keys, final TestParameters params) {
        boolean allAbsent = true;
        for (final String key : keys) {
            if (params.get(key) != null) {
                allAbsent = false;
            }
        }
        return allAbsent;
    }

}
