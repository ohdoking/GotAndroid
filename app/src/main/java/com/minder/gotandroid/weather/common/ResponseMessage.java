package com.minder.gotandroid.weather.common;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.HashMap;

public class ResponseMessage {
    private String statusCode;
    private String resultMessage;
    private HashMap resultParser;
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    @Override
    public String toString() {
        return resultMessage;
    }

    public void setResultHashmap(HashMap seojin) {
        this.resultParser = seojin;
    }

    public HashMap getResultHashmap() {
        return resultParser;
    }


//    public void setParseJson(String resultMessage) {
//        try {
//            JSONObject jsonObj = new JSONObject(resultMessage);
//            JSONArray summary = null;
//            summary = jsonObj.getJSONArray("summary");
//            // grid
//            JSONObject grid = summary.getJSONObject(0);
//            String latitude = grid.getString("latitude");
//            String longitude = grid.getString("longitude");
//            String city = grid.getString("city");
//            String county = grid.getString("county");
//            String village = grid.getString("village");
//            // yesterday
//            JSONObject yesterday = summary.getJSONObject(2);
//            JSONObject yesterday_sky = yesterday.getJSONObject("sky");
//            String yesterday_name = yesterday_sky.getString("name");
//            String yesterday_code = yesterday_sky.getString("code");
//            JSONObject yesterday_temperature = yesterday.getJSONObject("temperature");
//            String yesterday_tmax = yesterday_temperature.getString("tmax");
//            String yesterday_tmin = yesterday_temperature.getString("tmin");
//            // today
//            JSONObject today = summary.getJSONObject(3);
//            JSONObject today_sky = today.getJSONObject("sky");
//            String today_name = today_sky.getString("name");
//            String today_code = today_sky.getString("code");
//            JSONObject today_temperature = today.getJSONObject("temperature");
//            String today_tmax = today_temperature.getString("tmax");
//            String today_tmin = today_temperature.getString("tmin");
//            // tomorrow
//            JSONObject tomorrow = summary.getJSONObject(4);
//            JSONObject tomorrow_sky = tomorrow.getJSONObject("sky");
//            String tomorrow_name = tomorrow_sky.getString("name");
//            String tomorrow_code = tomorrow_sky.getString("code");
//            JSONObject tomorrow_temperature = tomorrow.getJSONObject("temperature");
//            String tomorrow_tmax = tomorrow_sky.getString("tmax");
//            String tomorrow_tmin = tomorrow_sky.getString("tmin");
//            // dayAfterTomorrow
//            JSONObject dayAfterTomorrow = summary.getJSONObject(5);
//            JSONObject dayAfterTomorrow_sky = dayAfterTomorrow.getJSONObject("sky");
//            String dayAfterTomorrow_name = dayAfterTomorrow_sky.getString("name");
//            String dayAfterTomorrow_code = dayAfterTomorrow_sky.getString("code");
//            JSONObject dayAfterTomorrow_temperature = dayAfterTomorrow.getJSONObject("temperature");
//            String dayAfterTomorrow_tmax = dayAfterTomorrow_temperature.getString("tmax");
//            String dayAfterTomorrow_tmin = dayAfterTomorrow_temperature.getString("tmin");
//            HashMap<String, String> resultWeather = new HashMap<>();
//            resultWeather.put("latitude", latitude);
//            resultWeather.put("longitude", longitude);
//            resultWeather.put("city", city);
//            resultWeather.put("county", county);
//            resultWeather.put("village", village);
//            resultWeather.put("yesterday_name", yesterday_name);
//            resultWeather.put("yesterday_code", yesterday_code);
//            resultWeather.put("yesterday_tmax", yesterday_tmax);
//            resultWeather.put("yesterday_tmin", yesterday_tmin);
//            resultWeather.put("today_name", today_name);
//            resultWeather.put("today_code", today_code);
//            resultWeather.put("today_tmax", today_tmax);
//            resultWeather.put("today_tmin", today_tmin);
//            resultWeather.put("tomorrow_name", tomorrow_name);
//            resultWeather.put("tomorrow_code", tomorrow_code);
//            resultWeather.put("tomorrow_tmax", tomorrow_tmax);
//            resultWeather.put("tomorrow_tmin", tomorrow_tmin);
//            resultWeather.put("dayAfterTomorrow_name", dayAfterTomorrow_name);
//            resultWeather.put("dayAfterTomorrow_code", dayAfterTomorrow_code);
//            resultWeather.put("dayAfterTomorrow_tmax", dayAfterTomorrow_tmax);
//            resultWeather.put("dayAfterTomorrow_tmin", dayAfterTomorrow_tmin);
//            this.resultParser = resultWeather;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//    public HashMap getResultParser() {
//        return resultParser;
//    }


}
