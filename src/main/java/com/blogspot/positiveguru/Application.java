package com.blogspot.positiveguru;

import com.blogspot.positiveguru.domain.IcmpPingClient;
import com.blogspot.positiveguru.domain.PingClient;
import com.blogspot.positiveguru.domain.TcpPingClient;
import com.blogspot.positiveguru.domain.TracertPingClient;
import com.blogspot.positiveguru.config.*;
import com.blogspot.positiveguru.domain.BatchRunner;
import com.blogspot.positiveguru.reporting.Report;
import com.blogspot.positiveguru.reporting.ReportProvider;
import com.blogspot.positiveguru.utils.ConfigUtil;


import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


/**
 * Created by Avenir Voronov on 7/16/2017.
 */

public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());


    public static void main(String[] args) {

        if (args.length == 0) printHelp();

        System.out.println("Initialisation...");
        try {
            ConfigUtil.fillConfigFromArray(args);
        } catch (Exception e) {
            logger.warning("Config creation failed:" + e);
            System.out.println("Initialisation failed!");
            printHelp();
            System.exit(1);
        }


        String command = Config.getCommand();
        PingClient pingClient = null;
        if (command.equals(CommandsEnum.ICMP.val())) {
            pingClient = new IcmpPingClient();
        } else if (command.equals(CommandsEnum.TCP.val())) {
            pingClient = new TcpPingClient();
        } else if (command.equals(CommandsEnum.TRACE.val())) {
            pingClient = new TracertPingClient();
        } else {
            System.out.println("Wrong command name: " + command);
            logger.warning("Wrong command name: " + command);
        }

        System.out.println("Starting batch...");
        ConcurrentHashMap<String, Report> reports = BatchRunner.run(pingClient);

        System.out.println("Sending final report...");
        for (Report report : reports.values()) {
            new ReportProvider().sendJsonReportToHost(Config.getReportDestination(), report);
        }

    }

    private static void printHelp() {
        System.out.println("pinger \n"
                + "     -c      Command             IcmpPing, TcpPing, IcmpTracert \n"
                + "     [-n]     Count               Number of requests \n"
                + "     -hf     Hosts File          Line by line text file with destination hosts \n"
                + "     [-d]     Delay               Time to wait between scheduled request list \n"
                + "     [-w]     TimeOut             (TcpPing only) time to wait for response \n"
                + "     -r      Report Url          URL to send the report \n"
                + "     -log    Log File            Log file to be use \n"
                + " \nExample: pinger -c IcmpPing -hf C:\\hosts.txt -r www.yandex.ru -n 5 -d 3 -log logs.log \n\n"
        );
    }
}
