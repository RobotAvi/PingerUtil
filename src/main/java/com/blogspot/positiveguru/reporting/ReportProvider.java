package com.blogspot.positiveguru.reporting;

import com.blogspot.positiveguru.Application;
import com.blogspot.positiveguru.config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Avenir Voronov on 7/16/2017.
 */
public class ReportProvider {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public void sendJsonReportToHost(String host, Report report) {
        String json = report.toJson();

        logger.warning(json);

        try {
            Map responseData = this.sendHTTPData(Config.getReportDestination(), json);

            int responseCode = responseData != null ? (Integer) responseData.get("response_code") : 0;
            System.out.println("\nSending 'POST' request to URL : " + Config.getReportDestination());
            System.out.println("Response Code : " + responseCode);
            System.out.println("ResponseMessage: " + (responseData != null ? responseData.get("response_message") : null));
            System.out.println("ResponseTime (ms): " + (responseData != null ? responseData.get("response_time") : null));

        } catch (IOException e) {
            logger.warning(e.toString());
        }


    }

    private Map sendHTTPData(String url, String data) throws IOException {
        HashMap<String,String> responseMap = new HashMap<>();

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {

            long startTime = System.currentTimeMillis();
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/json,application/xml;q=0.9,image/webp,*/*;q=0.8");

            connection.connect();

            if(data != null) {
                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            long elapsedTime = System.currentTimeMillis() - startTime;

            responseMap.put("url", connection.getURL().toString());
            responseMap.put("response_code", String.valueOf(connection.getResponseCode()));
            responseMap.put("response_message", connection.getResponseMessage());
            responseMap.put("response_time", elapsedTime+"");

        } catch (MalformedURLException | ProtocolException e){
            logger.warning("\nInvalid URL: " + e);
            throw e;
        } catch (IOException e) {
            logger.warning("\n Error while processing the http call : " + e);
            throw e;
        } finally {
            if (reader != null){
                try{
                    reader.close();
                } catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
            if (connection != null){
                connection.disconnect();
            }
        }


        return responseMap;
    }

}
