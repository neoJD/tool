package com.ndptest.tool.apiMethod.GET;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndptest.tool.apiMethod.BASE.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.ndptest.tool.apiMethod.BASE.JsonParser.*;
import static com.ndptest.tool.apiMethod.GET.Response.*;

public class UserService {
    public static String token = "eyJraWQiOiIyIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJtaGNob2lAbmVvbGFiLm5ldCIsImF1ZCI6Im5lb2xhYiIsInJlc291cmNlT3duZXIiOiJuZW9sYWIiLCJzY29wZSI6WyJpbmsuYWRtaW4ucmVhZCIsImluay5hZG1pbi53cml0ZSIsImluay5hcHBsaWNhdGlvbi5yZWFkIiwiaW5rLmFwcGxpY2F0aW9uLndyaXRlIiwiaW5rLnJlYWQiLCJpbmsucmVzb3VyY2UucmVhZCIsImluay5yZXNvdXJjZS53cml0ZSIsImluay53cml0ZSIsIm9wZW5pZCIsInBhcGVyLmFkbWluLnJlYWQiLCJwYXBlci5hZG1pbi53cml0ZSIsInByb2ZpbGUuYWRtaW4ucmVhZCIsInByb2ZpbGUuYWRtaW4ud3JpdGUiLCJwcm9maWxlLmFwcGxpY2F0aW9uLnJlYWQiLCJwcm9maWxlLmFwcGxpY2F0aW9uLndyaXRlIiwicHJvZmlsZS5yZWFkIiwicHJvZmlsZS5yZXNvdXJjZS5yZWFkIiwicHJvZmlsZS5yZXNvdXJjZS53cml0ZSIsInByb2ZpbGUud3JpdGUiLCJyZWxheS5hZG1pbi5yZWFkIiwicmVsYXkuYWRtaW4ud3JpdGUiLCJzdG9yYWdlLmFkbWluLnJlYWQiLCJzdG9yYWdlLmFkbWluLndyaXRlIiwic3RvcmFnZS5hcHBsaWNhdGlvbi5yZWFkIiwic3RvcmFnZS5hcHBsaWNhdGlvbi53cml0ZSIsInN0b3JhZ2UucmVhZCIsInN0b3JhZ2UucmVzb3VyY2UucmVhZCIsInN0b3JhZ2UucmVzb3VyY2Uud3JpdGUiLCJzdG9yYWdlLndyaXRlIiwidXNlcmRhdGEuYWRtaW4ucmVhZCIsInVzZXJkYXRhLmFkbWluLndyaXRlIiwidXNlcmRhdGEuYXBwbGljYXRpb24ucmVhZCIsInVzZXJkYXRhLmFwcGxpY2F0aW9uLndyaXRlIiwidXNlcmRhdGEucmVhZCIsInVzZXJkYXRhLnJlc291cmNlLnJlYWQiLCJ1c2VyZGF0YS5yZXNvdXJjZS53cml0ZSIsInVzZXJkYXRhLndyaXRlIl0sImlzcyI6Imh0dHBzOlwvXC9uZHAtZGV2Lm9udGhlLmxpdmU6NzQ0MyIsInR5cGUiOiJyZWZyZXNoX3Rva2VuIiwiYXBwbGljYXRpb25JZCI6MTU0NywiZXhwIjoxNjY2MDA4NDM3LCJpYXQiOjE2NjU5ODY4MzcsImp0aSI6ImJFU05Ka0JiIn0.bUenzIPLvGK6Vvsv1AWk0AYVz3pE8dpKvwwkkhzU9W-qVF-mStB07JFIrOSvETs2dEAj_LnOo_JYfRRCPm3gP-OmnsdClA47ZuqHLFw9K2cf-3DL2V_5Mw5F6aiw8bIxfrUItG7FT1NiTns9j8kRyYsSej5tbw9BwU6LTuLRFSKiD60X56LxQrEk0zs5F4pUhr0qHGbQg244ewpKH-mQvReCrAJey6HABE58DTgIOY7qPTQgkE0-7SO87TGHyySq0XtGWw7ML0LL8Rz1sVNHusIouo8BXdSjOSl4IphufsXVE-1xLdnGGranbwln7I3HrgatGIgBLCVfmvjwADcfaw";
    //토큰 값
    public static String header = "Bearer " + token;
    public static String noteListInfo;
    public static String userInfo;
    public static ArrayList<String> userIdList = new ArrayList<>();
    public static String noteType = "";

    public static void selectNoteType(int noteTypeNumber) {
        switch (noteTypeNumber) {
            case 0:
                noteType = "ALL";
                break;

            case 1:
                noteType = "ANALOG";
                break;

            case 2:
                noteType = "DIGITAL";
                break;
        }
    }

    public static void getUserNotes(String userID, int noteTypeNumber) throws Exception {
        //입력한 계정의 모든 정보들을 GET
        selectNoteType(noteTypeNumber);
        //조회할 노트 타입 결정
        try {
            url_str = "https://ndp-dev.onthe.live:6443/user/v2/" + userID + "/notes?digital=" + noteType;
            url = new URL(url_str);

            Response.getResponse();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getPickNoteInfo(String userID, int typeNumber, String key) throws Exception {
        //입력한 계정의 노트 UUID를 모두 GET
        getUserNotes(userID, typeNumber);
        pickInfo(noteListInfo, key);
    }

    public static void getUserNote(String userID, String noteUUID) {
        //입력한 계정과 특정 노트의 정보들을 GET
        try {
            url_str = "https://ndp-dev.onthe.live:6443/user/v2/" + userID + "/notes/" + noteUUID;
            url = new URL(url_str);

            Response.getResponse();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getUsers() throws MalformedURLException {
        try {
            url_str = "https://ndp-dev.onthe.live:6443/user/v2/users/profiles";
            url = new URL(url_str);

            Response.getResponse();

            userInfo = response.toString();
            pickInfo(userInfo, "id");

            userIdList = pickInfoOutArr;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //-------------------------------------------------여기부터 한솔책임님 부분---------------------------------------------------------------------------
    public static void getUserProfile(String userID) {
        try{
            url_str = "https://ndp-dev.onthe.live:6443/user/v2/users/" + userID + "/profile";
            url = new URL(url_str);

            Response.getResponse();
        }catch (Exception e){
            System.out.println(e);
        }
    }


    //--------------------------------------------------여기부터 파트장님 부분-----------------------------------------------------------------------------
    public static void getNoteList(String userId, int noteTypeNumber) {
        // digital = "ALL" or "ANALOG" or "DIGITAL"
        try{
            selectNoteType(noteTypeNumber);
            url_str = "https://ndp-dev.onthe.live:6443/user/v2/" + userId + "/notes?digital=" + noteTypeNumber;
            url = new URL(url_str);

            Response.getResponse();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void getTags(String userId) {
        try{
            url_str = "https://ndp-dev.onthe.live:6443/user/v2/" + userId + "/tags";
            url = new URL(url_str);

            Response.getResponse();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}

