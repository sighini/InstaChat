package com.sighini.instachat.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ConnectionManager {

    private static final String TAG = ConnectionManager.class.getSimpleName();
    public static String SERVER_URL = "http://raml.orainteractive.com:8081/";
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int DATARETRIEVAL_TIMEOUT = 10000;

    private static final String REGISTER_USER = "users";  //POST
    private static final String LOGIN = "users/login";      //POST
    private static final String PROFILE = "users/_current";
    private static final String CHAT = "chats";
    private static final String MESSAGE =  "messages";         // "chats/{chat_id}/messages";



    public enum RequestType {

		REGISTER_USER, //POST
		LOGIN,          //POST
		VIEW_PROFILE,   //GET
		EDIT_PROFILE,   //PUT
		GET_CHAT_LIST,  //GET
		CREATE_CHAT,    //POST
		GET_MESSAGE_LIST,   //GET
		CREATE_MSG          //POST
	}

	private static Context mContext = null;
	
	public static void sendRequest(Context context, final Handler handler,
                                   final RequestType requestType, final Object request) {

        mContext = context;
        final String url = getUrl(requestType, request);
        new Thread() {
            public void run() {
                String param = getPostParameters(requestType, request);
                String token = getRequestToken(requestType, request);
                String response = null;
                try {
                    //response = makeRequest(url, param, requestType, token);
                    Log.e(TAG, "Response:" + response);
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                if (Constants.isTestMode) {
                    response = getTestResponse(requestType);
                    Log.e(TAG, "TestResponse:" + response);
                }

                Message msgObj = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putString(Constants.RESPONSE, response);
                msgObj.setData(b);
                handler.sendMessage(msgObj);
            }
        }.start();
    }

    private static String getRequestToken(RequestType requestType, Object request) {
        switch(requestType) {
            case GET_CHAT_LIST:
            case VIEW_PROFILE:
               return (String) request;
            case REGISTER_USER:
            case LOGIN:
            case EDIT_PROFILE:
            case CREATE_CHAT:
            case GET_MESSAGE_LIST:
            case CREATE_MSG:
            default:
                return null;
        }

    }

    private static String getUrl(RequestType requestType, Object request) {
        switch (requestType){
            case REGISTER_USER:
                return SERVER_URL + REGISTER_USER;
            case LOGIN:
                return SERVER_URL + LOGIN;
            case VIEW_PROFILE:
            case EDIT_PROFILE:
                return SERVER_URL + PROFILE;
            case GET_CHAT_LIST:
                String charset = "UTF-8";
                String query = null;
                try {
                    query = String.format("page=%s&limit=%s",
                            URLEncoder.encode("1", charset),
                            URLEncoder.encode("1", charset));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return SERVER_URL + CHAT + "?" + query;
            case CREATE_CHAT:
                return SERVER_URL + CHAT;
            case GET_MESSAGE_LIST:
                return SERVER_URL + CHAT +"/" + (String)request + "/" + MESSAGE;
            case CREATE_MSG:
                String[] info = (String[])request;
                return SERVER_URL + CHAT +"/" + (String)info[0] + "/" + MESSAGE;
            default:
                return SERVER_URL;
        }
    }

    public static String getPostParameters(RequestType requestType, final Object request) {
        switch (requestType){
            case REGISTER_USER:
                return ((User) request).getPostParameters();
            case LOGIN:
                return ((LoginInfo) request).getPostParameters();
            case VIEW_PROFILE:
                return null;
            case EDIT_PROFILE:
                //TODO
                break;
            case GET_CHAT_LIST:
                return null;
            case CREATE_CHAT:
                //TODO
                break;
            case GET_MESSAGE_LIST:
                return null;
            case CREATE_MSG:
                try {
                    String[] info = (String[]) request;
                    return URLEncoder.encode("message=" + info[1] , "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            default:
                return null;
        }
        return null;
    }

    public static String makeRequest(String url, String postParameters, RequestType requestType,
                                     String token) throws IOException{

        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(url);
            Log.e(TAG, "URL:" + urlToRequest.toString());
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);

            // handle POST parameters
            if (postParameters != null) {

                Log.e(TAG, "Post param:" + postParameters);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setFixedLengthStreamingMode(
                        postParameters.getBytes().length);
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                urlConnection.setRequestProperty("Accept", "application/json");

                //send the POST out
                PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                out.print(postParameters);
                out.close();
            } else {
                //Handle get request
                urlConnection.setRequestProperty("Accept", "application/json");
                //TODO: Need to check which get request needs jwt authorization token
                if (requestType == RequestType.VIEW_PROFILE) {
                    urlConnection.setRequestProperty("Authorization", "Bearer " + token);
                }
            }

            int statusCode = urlConnection.getResponseCode();
            Log.e(TAG, "statusCode:" + statusCode);
            if (statusCode != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "HTTP NOT OK");
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return getResponseText(in);
            }
        } catch (MalformedURLException e) {
            // TODO handle invalid URL
            throw new IOException(e);
        } catch (SocketTimeoutException e) {
            throw new IOException(e);
        }  finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }


    private static String getTestResponse(RequestType requestType) {
        switch (requestType){
            case REGISTER_USER:
                return new String ("{\n" +
                        "  \"success\": true,\n" +
                        "  \"data\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNDM0NDY3NDUxfQ.Or5WanRwK1WRqqf4oeIkAHRYgNyRssM3CCplZobxr4w\",\n" +
                        "    \"email\": \"andre@orainteractive.com\",\n" +
                        "    \"name\": \"andre\"\n" +
                        "  }\n" +
                        "}");
            case LOGIN:
                return new String("{\n" +
                        "  \"success\": true,\n" +
                        "  \"data\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNDM0NDY3NDUxfQ.Or5WanRwK1WRqqf4oeIkAHRYgNyRssM3CCplZobxr4w\",\n" +
                        "    \"email\": \"andre@orainteractive.com\",\n" +
                        "    \"name\": \"andre\"\n" +
                        "  }\n" +
                        "}");
            case VIEW_PROFILE:
                return new String("{\n" +
                        "  \"success\": true,\n" +
                        "  \"data\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNDM0NDY3NDUxfQ.Or5WanRwK1WRqqf4oeIkAHRYgNyRssM3CCplZobxr4w\",\n" +
                        "    \"email\": \"andre@orainteractive.com\",\n" +
                        "    \"name\": \"andre\"\n" +
                        "  }\n" +
                        "}");
            case EDIT_PROFILE:
                //TODO
                break;
            case GET_CHAT_LIST:
                return new String("{\n" +
                        "    \"success\": true,\n" +
                        "    \"data\": [\n" +
                        "        {\n" +
                        "            \"id\": 1,\n" +
                        "            \"user_id\": 1,\n" +
                        "            \"name\": \"A Chat\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 2,\n" +
                        "            \"user_id\": 2,\n" +
                        "            \"name\": \"A Chat 2\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"pagination\": {\n" +
                        "        \"page_count\": 1,\n" +
                        "        \"current_page\": 1,\n" +
                        "        \"has_next_page\": false,\n" +
                        "        \"has_prev_page\": false,\n" +
                        "        \"count\": 1,\n" +
                        "        \"limit\": null\n" +
                        "    }\n" +
                        "}");
            case CREATE_CHAT:
                //TODO
                break;
            case GET_MESSAGE_LIST:
                return new String("{\n" +
                        "            \"success\": true,\n" +
                        "            \"data\": [\n" +
                        "              {\n" +
                        "                \"id\": 1,\n" +
                        "                \"chat_id\": 1,\n" +
                        "                \"user_id\": 1,\n" +
                        "                \"message\": \"Hey there!\",\n" +
                        "                \"user\": {\n" +
                        "                  \"id\": 1,\n" +
                        "                  \"name\": \"andre\"\n" +
                        "                }\n" +
                        "              },\n" +
                        "              {\n" +
                        "                \"id\": 2,\n" +
                        "                \"chat_id\": 1,\n" +
                        "                \"user_id\": 2,\n" +
                        "                \"message\": \"Oh hey!\",\n" +
                        "                \"user\": {\n" +
                        "                  \"id\": 2,\n" +
                        "                  \"name\": \"dan\"\n" +
                        "                }\n" +
                        "              }\n" +
                        "            ],\n" +
                        "            \"pagination\": {\n" +
                        "              \"page_count\": 1,\n" +
                        "              \"current_page\": 1,\n" +
                        "              \"has_next_page\": false,\n" +
                        "              \"has_prev_page\": false,\n" +
                        "              \"count\": 2,\n" +
                        "              \"limit\": null\n" +
                        "            }\n" +
                        "          }");
            case CREATE_MSG:
                return new String("{\n" +
                        "            \"success\": true,\n" +
                        "            \"data\": {\n" +
                        "              \"id\": 1,\n" +
                        "              \"chat_id\": 1,\n" +
                        "              \"user_id\": 1,\n" +
                        "              \"message\": \"Hey there!\",\n" +
                        "              \"user\": {\n" +
                        "                \"id\": 1,\n" +
                        "                \"name\": \"andre\"\n" +
                        "              }\n" +
                        "            }\n" +
                        "          }");
            default:
                return null;
        }
        return null;
    }

    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }
}
