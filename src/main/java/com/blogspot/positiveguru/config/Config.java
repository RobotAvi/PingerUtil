package com.blogspot.positiveguru.config;

import java.util.List;

/**
 * Created by Avenir Voronov on 7/18/2017.
 */
public class Config {

    private static String command;
    private static int requestCount;
    private static List<String> hosts;
    private static int delay;
    private static int timeout;
    private static String reportDestination;
    private static String logFile;

    public static String getCommand() {
        return command;
    }

    public static void setCommand(String command) {
        Config.command = command;
    }

    public static int getRequestCount() {
        return requestCount;
    }

    public static void setRequestCount(int requestCount) {
        Config.requestCount = requestCount;
    }

    public static List<String> getHosts() {
        return hosts;
    }

    public static void setHosts(List<String> hosts) {
        Config.hosts = hosts;
    }

    public static int getDelay() {
        return delay;
    }

    public static void setDelay(int delay) {
        Config.delay = delay;
    }

    public static int getTimeout() {
        return timeout;
    }

    public static void setTimeout(int timeout) {
        Config.timeout = timeout;
    }

    public static String getReportDestination() {
        return reportDestination;
    }

    public static void setReportDestination(String reportDestination) {
        Config.reportDestination = reportDestination;
    }

    public static String getLogFile() {
        return logFile;
    }

    public static void setLogFile(String logFile) {
        Config.logFile = logFile;
    }
}
