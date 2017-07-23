package com.blogspot.positiveguru.domain;

import com.blogspot.positiveguru.config.Config;
import com.blogspot.positiveguru.reporting.Report;

import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by Avenir Voronov on 7/17/2017.
 */
public class BatchRunner {
    private static final Logger logger = Logger.getLogger(BatchRunner.class.getName());

    public static ConcurrentHashMap<String, Report> run(PingClient pingClient) {
        if (pingClient == null) {
            logger.warning("Don't have PingClinet");
            return null;
        }
        ConcurrentHashMap<String, Report> tasksReports = new ConcurrentHashMap<>();


        ScheduledExecutorService executor = Executors.newScheduledThreadPool(Config.getHosts().size());

        Config.getHosts().forEach(host -> {
            Runnable task = () -> {
                Report report = new Report(host);
                try {
                    tasksReports.put(host, pingClient.execute(report));
                } catch (Exception e) {
                    logger.warning("Can't run task:" + e);
                }
            };

            executor.scheduleAtFixedRate(task, 0, Config.getDelay(), TimeUnit.MILLISECONDS);
        });

        return tasksReports;
    }


}
