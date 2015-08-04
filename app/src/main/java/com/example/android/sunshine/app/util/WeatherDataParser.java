package com.example.android.sunshine.app.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyadwadkar on 7/25/15.
 */
public class WeatherDataParser {
    public static final String WEATHER_DATA_PARSER = "WeatherDataParser";


    public static double getMaxTempForDay(String weatherJsonStr, int day) throws JSONException {
        JSONObject root = new JSONObject(weatherJsonStr);

        if (root.length() == 0) {
            throw new JSONException("No data found or unable to parse data. Length of root json " +
                    "node was 0");
        }

        int count = root.getInt("cnt");
        if (count == 0) {
            throw new JSONException("Invalid data received. Number of days with reported temperatures" +
                    " is 0");
        }

        if (day >= count) {
            throw new IllegalArgumentException("You're asking for more days than are available in the " +
                    "data returned from the server.");
        }

        JSONArray dayList = root.getJSONArray("list");

        if (dayList.length() != count) {
            String errMsg = String.format("Invalid data returned. Temperature list is of length %s " +
                    "while count was %s", dayList.length(), count);
            throw new JSONException(errMsg);
        }

        JSONObject dayTemperatures =  dayList.getJSONObject(day).getJSONObject("temp");
        return dayTemperatures.getDouble("max");

    }

}
