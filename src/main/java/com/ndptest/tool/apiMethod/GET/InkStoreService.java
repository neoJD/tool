package com.ndptest.tool.apiMethod.GET;

import org.apache.catalina.User;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.ndptest.tool.apiMethod.GET.Response.*;
import static com.ndptest.tool.apiMethod.GET.UserService.*;
import static com.ndptest.tool.apiMethod.GET.UserService.header;
import static com.ndptest.tool.apiMethod.GET.UserService.userIdList;

public class InkStoreService {
    private static String queryType;

    private static JSONArray wholeStrokeArray;

    private static JSONObject jsonObject;

    private static JSONArray jsonNoteInfoArray;

    private static JSONObject jsonStrokesInfoObject;


    private static ArrayList<String> strokeArray;

    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        UserService.getUsers();
        HashMap<String, Integer> hashMap = new HashMap<>();

        for ( int i = 0 ; i < userIdList.size(); i++ ) {
            hashMap.put(userIdList.get(i), InkStoreService.countDots(userIdList.get(i), 0));

        }
        System.out.println(hashMap);
    }


    public static void selectQueryType(int queryTypeNumber) {

        switch (queryTypeNumber) {
            case 0:
                queryType = "";
                break;

            case 1:
                queryType = "/?queryType=CONTAIN_DELETED";
                break;

            case 2:
                queryType = "/?queryType=SNAPSHOT";
                break;
        }
    }


    public static void getUserStroke(String userID, int queryTypeNumber) throws IOException, ParseException {
        try {
            selectQueryType(queryTypeNumber);

            url_str = "https://ndp-dev.onthe.live:9443/inkstore/v2/stroke/" + userID + queryType;
            url = new URL(url_str);

            Response.getResponse();

//            UserService.userInfo = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static long getResponseTime(String userID, int queryTypeNumber ) {
        // ?????? open?????? request????????? response??? ??????????????? ????????? ??????(ms) return
        long responseTime = 0;
        try {
            selectQueryType(queryTypeNumber);

            url_str = "https://ndp-dev.onthe.live:9443/inkstore/v2/stroke/" + userID + queryType;
            url = new URL(url_str);

            long beginTime = System.currentTimeMillis(); // response time ????????????

            Response.getResponse();

            responseTime = System.currentTimeMillis() - beginTime; // ???????????????


        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseTime;
    }

    public static int countUserStrokes(String userID, int queryTypeNumber ) throws InterruptedException {
        // userID ??? ?????? ?????? strokeCount ??????, RestTemplte ??????
        int strokeCount = 0; // ???????????? ??????

            selectQueryType(queryTypeNumber);

            //ResTemplate ??????
            RestTemplate restTemplate = new RestTemplate();

            //?????? ?????? ??????
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
            headers.add("Authorization", header); //Token ???????????????

            // url ??????
            url_str = "https://ndp-dev.onthe.live:9443/inkstore/v2/stroke/" + userID;
            URI url = URI.create(url_str);
        try {
           //url ?????? / ??????
            RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, url);
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);


            JSONParser jsonParser = new JSONParser();

            // test code for Unexpected token END OF FILE at position 0.
            wholeStrokeArray = (JSONArray) jsonParser.parse(response.getBody());

            for (int i=0; i<wholeStrokeArray.size(); i++ ) {
                jsonObject = (JSONObject) wholeStrokeArray.get(i);
                jsonNoteInfoArray = (JSONArray) jsonObject.get("strokes"); // jsonArray?????? strokeArray??? ?????? ??????

                for (int j = 0; j < jsonNoteInfoArray.size(); j++) {
                    strokeCount++;
                }
            }

        }catch(HttpClientErrorException e) {
            restTemplate.setErrorHandler(new RestTemplateErrorHandler());
            Thread.sleep(5000);

            return -1; // strokeCount??? -1??? ?????? ????????????

        } catch (Exception e) {
            e.printStackTrace();
        }

        return strokeCount;
    }

//    public int countUserStrokes1(String userID, int queryTypeNumber ) {
//        // userID ??? ?????? ?????? strokeCount ??????
//        int strokeCount = 0; // ???????????? ??????
//        try {
//            selectQueryType(queryTypeNumber);
//
//            url_str = "https://ndp-dev.onthe.live:9443/inkstore/v2/stroke/" + userID + queryType;
//
//            url = new URL(url_str);
//
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//
//            conn.setRequestMethod("GET"); // http ?????????
//            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type ??????
//            conn.setRequestProperty("Authorization", header); // header??? auth ??????
//            conn.setDoOutput(true); // ??????????????? ?????? ?????? ????????? true
//
//
//
//            StringBuilder sb = new StringBuilder();
//
//            Response.getInputData(conn, sb); // InputStreamReader??? ????????? ???????????? StringBuilder sb??? append.
//
//            JSONParser jsonParser = new JSONParser();
//
//            // test code for Unexpected token END OF FILE at position 0.
//            wholeStrokeArray = (JSONArray) jsonParser.parse(sb.toString());
//
//
//            for (int i=0; i<wholeStrokeArray.size(); i++ ) {
//                jsonObject = (JSONObject) wholeStrokeArray.get(i);
//                jsonNoteInfoArray = (JSONArray) jsonObject.get("strokes"); // jsonArray?????? strokeArray??? ?????? ??????
//
//                for (int j = 0; j < jsonNoteInfoArray.size(); j++) {
//                    strokeCount++;
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return strokeCount;
//    }

    public static int countDots(String userID, int queryTypeNumber) throws MalformedURLException, InterruptedException {
        int dotCount = 0;

            selectQueryType(queryTypeNumber);

            //ResTemplate ??????
            RestTemplate restTemplate = new RestTemplate();

            //?????? ?????? ??????
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
            headers.add("Authorization", header); //Token ???????????????

            // url ??????
            url_str = "https://ndp-dev.onthe.live:9443/inkstore/v2/stroke/" + userID;
            URI url = URI.create(url_str);

        try {
            //url ?????? / ??????
            RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, url);
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);


            JSONParser jsonParser = new JSONParser();

            wholeStrokeArray = (JSONArray) jsonParser.parse(response.getBody());

            for (int i = 0; i < wholeStrokeArray.size(); i++) {
                // jsonArray ?????? key??? "strokes"????????? ????????? ?????? ??????
                jsonObject = (JSONObject) wholeStrokeArray.get(i);
                jsonNoteInfoArray = (JSONArray) jsonObject.get("strokes");
//                System.out.println(jsonObject.keySet()); // test code


                for (int j = 0; j < jsonNoteInfoArray.size(); j++) {
                    jsonStrokesInfoObject = (JSONObject) jsonNoteInfoArray.get(j);
                    dotCount += Integer.parseInt(jsonStrokesInfoObject.get("dotCount").toString()); // dotCount ??????
                }
            }

        } catch(HttpClientErrorException e) {
            dotCount = -1;

            restTemplate.setErrorHandler(new RestTemplateErrorHandler());
            Thread.sleep(5000);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dotCount;
    }


    public static void getUserStroke_extra(String extraKey, String extraValue, String userID, int queryTypeNumber) {
        try {
            selectQueryType(queryTypeNumber);

            url_str = "https://ndp-dev.onthe.live:9443/inkstore/v2/stroke/" + userID + queryType;
            url = new URL(url_str);

            Response.getResponse();

            UserService.userInfo = response.toString();

            System.out.println(UserService.userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}