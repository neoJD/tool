package com.ndptest.tool.apiMethod.BASE;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;

public class JsonParser {
    public static ArrayList<String> keyArr = new ArrayList<>();
    public static ArrayList<Object> valArr = new ArrayList<>();
    public static ArrayList<String> pickInfoOutArr = new ArrayList<>();
    public static int count = 0;

//    public static void JsonParser(String inputObj) throws ParseException {
//
//        JSONParser jsonParser = new JSONParser();
//
//        org.json.simple.JSONArray arr = (org.json.simple.JSONArray) jsonParser.parse(inputObj);
////        int count = 0;
//
//        for (int i = 0; i < arr.size(); i++) {
//
//            org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) jsonParser.parse(arr.get(i).toString());
//
//            Iterator<String> keys = jsonObj.keySet().iterator();
////            count = i++;
//            System.out.println((i + 1) + "번째 정보");
//            for (int k = 0; k < jsonObj.size(); k++) {
//                String key = keys.next();
//                Object obj = jsonObj.get(key);
//
//                keyArr.add(key);
//                valArr.add(obj);
//                System.out.println(key + " : " + obj);
//            }
//            System.out.println("\n");
//        }
//    }

    public static ArrayList<String> pickInfo(String inputObj, String key) throws ParseException {
        JSONParser jsonParser = new JSONParser();

        org.json.simple.JSONArray arr = (org.json.simple.JSONArray) jsonParser.parse(inputObj);
//        int count = 0;

        for (int i = 0; i < arr.size(); i++) {

            org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) jsonParser.parse(arr.get(i).toString());

            Iterator<String> keys = jsonObj.keySet().iterator(); //dead code
            Object value = jsonObj.get(key);

            pickInfoOutArr.add(value.toString());

        }
        return pickInfoOutArr;
    }


    public static void pickInfoLoop(String inputObj, String key) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        Object value = null;

        org.json.simple.JSONArray arr = (org.json.simple.JSONArray) jsonParser.parse(inputObj);
        org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) jsonParser.parse(arr.get(0).toString());


        Iterator<String> keys = jsonObj.keySet().iterator();
        for (int k = 0; k < jsonObj.size(); k++) {
            String aa = keys.next();
            keyArr.add(aa);
        }
        System.out.println(keyArr);

        for (int k = 0; k < keyArr.size(); k++) {
            for (int i = 0; i < arr.size(); i++) {

                org.json.simple.JSONObject aa = (org.json.simple.JSONObject) jsonParser.parse(arr.get(i).toString());

                System.out.println(keyArr.get(k) + ":" + aa.get(keyArr.get(k)));
            }
        }


    }



}