package com.blogspot.positiveguru.utils;

import com.blogspot.positiveguru.config.Config;
import com.blogspot.positiveguru.config.ParametersEnum;
import com.blogspot.positiveguru.utils.ShellUtils;

import java.util.stream.IntStream;

/**
 * Created by Avenir Voronov on 7/17/2017.
 */

public class ConfigUtil {


    public static void fillConfigFromArray(String[] array) throws IllegalArgumentException {

        if (array.length == 0 || array.length % 2 == 1) {
            throw new IllegalArgumentException("Some necessary parameters are absent.");
        }

        IntStream.iterate(0, i -> i + 2)
                .limit(array.length / 2)
                .forEach(i -> addParameterToConfig(array[i], array[i + 1]));

        if (checkThatAllParametersAdded()) {
            throw new IllegalArgumentException("Some necessary parameters are absent.");
        }
    }


    private static boolean checkThatAllParametersAdded() {
        return Config.getCommand() == null ||
                Config.getHosts().isEmpty() ||
                Config.getLogFile() == null ||
                Config.getReportDestination() == null;
    }

    private static void addParameterToConfig(String parmeter, String value) throws IllegalArgumentException {
        if (parmeter.equals(ParametersEnum.COMMAND.val())) {
            Config.setCommand(value);
        } else if (parmeter.equals(ParametersEnum.DELAY.val())) {
            Config.setDelay(Integer.parseInt(value));
        } else if (parmeter.equals(ParametersEnum.HOSTS_FILE.val())) {
            Config.setHosts(ShellUtils.getLinesListFromFile(value));
        } else if (parmeter.equals(ParametersEnum.LOG_FILE.val())) {
            Config.setLogFile(value);
        } else if (parmeter.equals(ParametersEnum.REPORT_DESTINATION.val())) {
            Config.setReportDestination(value);
        } else if (parmeter.equals(ParametersEnum.REQUEST_COUNT.val())) {
            Config.setRequestCount(Integer.parseInt(value));
        } else if (parmeter.equals(ParametersEnum.TIMEOUT.val())) {
            Config.setTimeout(Integer.parseInt(value));
        } else {
            throw new IllegalArgumentException("Unknown parameter:" + parmeter);
        }

    }

    private void addDefaults(){
        //TODO:Add defaults from application.properties
    }

}
