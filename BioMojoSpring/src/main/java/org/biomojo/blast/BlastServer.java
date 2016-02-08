/*
 * Copyright (C) 2015  Hugh Eaves
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

package org.biomojo.blast;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import org.biomojo.util.ProcessUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class BlastServer.
 */
@Named
public class BlastServer {
    
    /** The Constant logger. */
    private final static Logger logger = LoggerFactory.getLogger(BlastServer.class.getName());

    /** The blast service. */
    @Inject
    private BlastService blastService;

    /**
     * Run blast server.
     *
     * @param numThreads the num threads
     */
    public void runBlastServer(int numThreads) {
        logger.info("Starting blast server");

        BlockingQueue<Runnable> queue = new SynchronousQueue<Runnable>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 10, TimeUnit.SECONDS, queue);

        while (true) {
            try {
                logger.info("Checking for free threads");
                if (executor.getActiveCount() < numThreads) {
                    final BlastTask blastTask;
                    logger.info("Checking for READY blast tasks");
                    if ((blastTask = blastService.getNextBlastTask()) != null) {
                        executor.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    blastService.runBlast(blastTask.getId());
                                } catch (Throwable e) {
                                    logger.error("Blast task failed" + e.toString());
                                    blastService.updateBlastTaskStatus(blastTask.getId(), BlastTaskStatus.FAILED);
                                }
                            }
                        });

                    } else {
                        logger.info("No more blast tasks, sleeping for 60 seconds");
                        ProcessUtil.sleep(60000);
                    }

                } else {
                    logger.info("All threads in use, sleeping for 5 seconds");
                    ProcessUtil.sleep(5000);

                }
            } catch (Exception e) {
                logger.error("Received eexception", e);
            }

            ProcessUtil.sleep(1000);
        }

    }
}
