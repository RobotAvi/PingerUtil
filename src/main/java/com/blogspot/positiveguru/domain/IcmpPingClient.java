package com.blogspot.positiveguru.domain;

import com.blogspot.positiveguru.config.Config;
import com.blogspot.positiveguru.reporting.Report;
import com.blogspot.positiveguru.reporting.ReportProvider;
import com.blogspot.positiveguru.utils.ShellUtils;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Avenir Voronov on 7/18/2017.
 */
public class IcmpPingClient implements PingClient {

    @Override
    public Report execute(Report report) {
        StringBuilder resp = new StringBuilder("\nDate:" + new Date() + "\n");
        try {
            boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
            List<String> command = new LinkedList<>();
            command.add("ping");
            command.add(isWindows ? "-n" : "-c");
            command.add(Config.getRequestCount() + "");
            command.add("-w");
            command.add(Config.getTimeout() + "");
            command.add(report.getHost());

            resp.append(ShellUtils.runInCommandLine(command));
            report.setIcmpPing(resp.toString());
        } catch (Exception e) {

            new ReportProvider().sendJsonReportToHost(Config.getReportDestination(), report);
        }
        return report;
    }
}
