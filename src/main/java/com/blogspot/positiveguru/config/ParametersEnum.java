package com.blogspot.positiveguru.config;

/**
 * Created by Avenir Voronov on 7/17/2017.
 */
public enum ParametersEnum {
    COMMAND("-c"),
    REQUEST_COUNT("-n"),
    HOSTS_FILE("-hf"),
    DELAY("-d"),
    TIMEOUT("-w"),
    REPORT_DESTINATION("-r"),
    LOG_FILE("-log");

    private final String val;

    ParametersEnum(String val) {
        this.val = val;
    }

    public String val() {
        return val;
    }

}
