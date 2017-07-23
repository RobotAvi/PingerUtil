package com.blogspot.positiveguru.utils;

import com.blogspot.positiveguru.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Avenir Voronov on 7/18/2017.
 */
public class ShellUtils {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static String runInCommandLine(List<String> command) throws Exception {
        String stdInputStore;
        StringBuilder resp = new StringBuilder();

        resp.append("\nExecuting:\n").append(command);

        // Execute the ping/tracert command via local terminal/commandline
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // read the command received response from the Inputstream
        resp.append("\nHere is the standard OUTPUT of the command:\n");
        while ((stdInputStore = stdInput.readLine()) != null) {
            resp.append(stdInputStore).append("\n");
        }

        // read any errors from the attempted command
        resp.append("\nHere is the standard ERROR of the command (if any):\n");
        Boolean hasErros = false;
        while ((stdInputStore = stdError.readLine()) != null) {
            resp.append(stdInputStore).append("\n");
            hasErros = true;
        }
        if (hasErros) throw new Exception(String.valueOf(resp));

        return String.valueOf(resp);
    }

    static List<String> getLinesListFromFile(String filePath) {
        List<String> lines = new LinkedList<>();
        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            logger.warning("Can't reach host file" + e);
        }
        return lines;
    }

}
