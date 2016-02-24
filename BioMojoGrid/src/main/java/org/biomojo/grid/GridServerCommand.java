package org.biomojo.grid;

import org.java0.cli.AbstractCommand;
import org.java0.logging.slf4j.Logger;
import org.java0.logging.slf4j.LoggerFactory;

import com.beust.jcommander.Parameters;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Parameters(commandNames = "grid_server")
public class GridServerCommand extends AbstractCommand {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(GridServerCommand.class);

    @Override
    public void runWithThrow() {
        final HazelcastInstance instance = Hazelcast.newHazelcastInstance(null);
    }

}
