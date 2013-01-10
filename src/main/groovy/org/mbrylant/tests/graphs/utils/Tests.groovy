package org.mbrylant.tests.graphs.utils

import org.apache.log4j.Logger

/**
 * Created with IntelliJ IDEA.
 * User: mariuszb
 * Date: 09/01/2013
 * Time: 22:26
 * To change this template use File | Settings | File Templates.
 */
class Tests {

    private final static Logger LOG = Logger.getLogger(Tests)

    static long timeExecution(Runnable runner){
        LOG.info("Starting ${runner.class}")
        long startTime = System.nanoTime();
        runner.run()
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        LOG.info("${runner.class} test took ${duration * 0.000000001}s")
        duration
    }
}
