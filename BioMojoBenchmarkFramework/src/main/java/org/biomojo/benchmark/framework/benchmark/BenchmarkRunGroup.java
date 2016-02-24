package org.biomojo.benchmark.framework.benchmark;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.biomojo.core.AbstractPropertiedEntity;

@Entity
public class BenchmarkRunGroup extends AbstractPropertiedEntity {

    private static final long serialVersionUID = 1L;

    protected String runGroup;

    @OneToMany
    protected List<BenchmarkRun> runs = new ArrayList<BenchmarkRun>();

    public BenchmarkRunGroup() {

    }

    public BenchmarkRunGroup(final String runGroup) {
        this.runGroup = runGroup;
    }

    public void add(final BenchmarkRun run) {
        runs.add(run);
    }

    public String getRunGroup() {
        return runGroup;
    }

}
