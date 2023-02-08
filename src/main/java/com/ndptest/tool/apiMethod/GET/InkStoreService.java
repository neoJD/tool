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
        // 소켓 open부터 request보내고 response를 받는데까지 걸리는 시간(ms) return
        long responseTime = 0;
        try {
            selectQueryType(queryTypeNumber);

            url_str = "https://ndp-dev.onthe.live:9443/inkstore/v2/stroke/" + userID + queryType;
            url = new URL(url_str);

            long beginTime = System.currentTimeMillis(); // response time 시간체크

            Response.getResponse();

            responseTime = System.currentTimeMillis() - beginTime; // 밀리세컨드


        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseTime;
    }

    public static int countUserStrokes(String userID, int queryTypeNumber ) throws InterruptedException {
        // userID 가 갖고 있는 strokeCount 리턴, RestTemplte 사용
        int strokeCount = 0; // 스트로크 갯수

            selectQueryType(queryTypeNumber);

            //ResTemplate 생성
            RestTemplate restTemplate = new RestTemplate();

            //헤더 생성 인증
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
            headers.add("Authorization", header); //Token 인증해주기

            // url 생성
            url_str = "https://ndp-dev.onthe.live:9443/inkstore/v2/stroke/" + userID;
            URI url = URI.create(url_str);
        try {
           //url 호출 / 응답
            RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, url);
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);


            JSONParser jsonParser = new JSONParser();

            // test code for Unexpected token END OF FILE at position 0.
            wholeStrokeArray = (JSONArray) jsonParser.parse(response.getBody());

            for (int i=0; i<wholeStrokeArray.size(); i++ ) {
                jsonObject = (JSONObject) wholeStrokeArray.get(i);
                jsonNoteInfoArray = (JSONArray) jsonObject.get("strokes"); // jsonArray에서 strokeArray만 빼서 담고

                for (int j = 0; j < jsonNoteInfoArray.size(); j++) {
                    strokeCount++;
                }
            }

        }catch(HttpClientErrorException e) {
            restTemplate.setErrorHandler(new RestTemplateErrorHandler());
            Thread.sleep(5000);

            return -1; // strokeCount에 -1을 넣고 예외처리

        } catch (Exception e) {
            e.printStackTrace();
        }

        return strokeCount;
    }

//    public int countUserStrokes1(String userID, int queryTypeNumber ) {
//        // userID 가 갖고 있는 strokeCount 리턴
//        int strokeCount = 0; // 스트로크 갯수
//        try {
//            selectQueryType(queryTypeNumber);
//
//            url_str = "https://ndp-dev.onthe.live:9443/inkstore/v2/stroke/" + userID + queryType;
//
//            url = new URL(url_str);
//
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//
//            conn.setRequestMethod("GET"); // http 메서드
//            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
//            conn.setRequestProperty("Authorization", header); // header의 auth 정보
//            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true
//
//
//
//            StringBuilder sb = new StringBuilder();
//
//            Response.getInputData(conn, sb); // InputStreamReader로 데이터 땡겨와서 StringBuilder sb에 append.
//
//            JSONParser jsonParser = new JSONParser();
//
//            // test code for Unexpected token END OF FILE at position 0.
//            wholeStrokeArray = (JSONArray) jsonParser.parse(sb.toString());
//
//
//            for (int i=0; i<wholeStrokeArray.size(); i++ ) {
//                jsonObject = (JSONObject) wholeStrokeArray.get(i);
//                jsonNoteInfoArray = (JSONArray) jsonObject.get("strokes"); // jsonArray에서 strokeArray만 빼서 담고
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

            //ResTemplate 생성
            RestTemplate restTemplate = new RestTemplate();

            //헤더 생성 인증
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
            headers.add("Authorization", header); //Token 인증해주기

            // url 생성
            url_str = "https://ndp-dev.onthe.live:9443/inkstore/v2/stroke/" + userID;
            URI url = URI.create(url_str);

        try {
            //url 호출 / 응답
            RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, url);
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);


            JSONParser jsonParser = new JSONParser();

            wholeStrokeArray = (JSONArray) jsonParser.parse(response.getBody());

            for (int i = 0; i < wholeStrokeArray.size(); i++) {
                // jsonArray 에서 key값 "strokes"인것만 뽑아서 뺴서 저장
                jsonObject = (JSONObject) wholeStrokeArray.get(i);
                jsonNoteInfoArray = (JSONArray) jsonObject.get("strokes");
//                System.out.println(jsonObject.keySet()); // test code


                for (int j = 0; j < jsonNoteInfoArray.size(); j++) {
                    jsonStrokesInfoObject = (JSONObject) jsonNoteInfoArray.get(j);
                    dotCount += Integer.parseInt(jsonStrokesInfoObject.get("dotCount").toString()); // dotCount 세기
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