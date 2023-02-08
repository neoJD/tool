package com.ndptest.tool.apiMethod.GET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.ndptest.tool.apiMethod.GET.UserService.*;

public class Response {

    public static String url_str = "";
    public static URL url;
    public static StringBuffer response;
    public static String inputLine;

    public static void getResponse() throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", header);
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

        }
        response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine).append("\n");
        }

        System.out.println(response);
        br.close();
    }
}