package com.blogspot.positiveguru.domain;

import com.blogspot.positiveguru.config.Config;
import com.blogspot.positiveguru.reporting.Report;
import com.blogspot.positiveguru.reporting.ReportProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Avenir Voronov on 7/18/2017.
 */
public class TcpPingClient implements PingClient {

    private static final Logger logger = Logger.getLogger(TcpPingClient.class.getName());

    @Override
    public Report execute(Report report) {
        StringBuilder resp = new StringBuilder("\nDate:" + new Date() + "\n");

        try {
            Map responseData = this.sendHTTPData(report.getHost(), Config.getTimeout());

            int respCode = responseData != null ? (Integer) responseData.get("response_code") : 0;

            // Get the response from the connection
            resp.append("\nURL: ").append(responseData != null ? responseData.get("url") : null);
            resp.append("\nResponseCode: ").append(respCode);
            resp.append("\nResponseMessage: ").append(responseData != null ? responseData.get("response_message") : null);
            resp.append("\nResponseTime (ms): ").append(responseData != null ? responseData.get("response_time") : null);

            report.setTcp_ping(resp.toString());

            if (respCode != HttpURLConnection.HTTP_OK) {
                new ReportProvider().sendJsonReportToHost(Config.getReportDestination(), report);
            }

        } catch (IOException e) {
            logger.warning(e.toString());
            resp.append(e);
        }
        return report;
    }

    private Map sendHTTPData(String url, int timeOut) throws IOException {
        HashMap<String, String> responseMap = new HashMap<>();

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {

            long startTime = System.currentTimeMillis();
            connection = (HttpURLConnection) new URL(url).openConnection();

            if (timeOut > 0) {
                connection.setConnectTimeout(timeOut);
                connection.setReadTimeout(timeOut);
            }
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("HEAD");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            connection.setRequestProperty("Content-Type", "text/html");
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/json,application/xml;q=0.9,image/webp,*/*;q=0.8");

            connection.connect();

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write("Ping");
            wr.flush();
            wr.close();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            long elapsedTime = System.currentTimeMillis() - startTime;

            responseMap.put("url", connection.getURL().toString());
            responseMap.put("response_code", String.valueOf(connection.getResponseCode()));
            responseMap.put("response_message", connection.getResponseMessage());
            responseMap.put("response_time", elapsedTime + "");

        } catch (MalformedURLException | ProtocolException e) {
            logger.warning("\nInvalid URL: " + e);
            throw e;
        } catch (IOException e) {
            logger.warning("\n Error while processing the http call : " + e);
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }


        return responseMap;
    }
}
