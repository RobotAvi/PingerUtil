package com.blogspot.positiveguru.config;

/**
 * Created by Avenir Voronov on 7/17/2017.
 */
public enum CommandsEnum {
    ICMP("IcmpPing"),
    TCP("TcpPing"),
    TRACE("Tracert");

    private final String val;

    CommandsEnum(String val) {
        this.val = val;
    }

    public String val() {
        return val;
    }
}
