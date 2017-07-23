package com.blogspot.positiveguru.reporting;

/**
 * Created by Avenir Voronov on 7/16/2017.
 */
public class Report {
    private String host;
    private String icmpPing;
    private String tcpPing;
    private String trace;

    public Report(String host) {
        this.host = host;
    }

    public String getTrace() {
        return trace;
    }
    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }

    public String getIcmpPing() {
        return icmpPing;
    }
    public void setIcmpPing(String icmpPing) {
        this.icmpPing = icmpPing;
    }

    public String getTcp_ping() {
        return tcpPing;
    }
    public void setTcp_ping(String tcpPing) {
        this.tcpPing = tcpPing;
    }

    public String toJson() {
        return   "{\"host\":\"" + host + "\"," +
                "\"icmp_ping\":\"" + icmpPing + "\","
                + "\"tcp_ping\":\"" + tcpPing + "\","
                + "\"trace\":\"" + trace + "\"}";

    }
}
