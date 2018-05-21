package com.example.andrey.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class WeatherParserJSON {
    public static Weather getWeather(String data) throws JSONException{
        Weather weather = new Weather();

        JSONObject jsonObject = new JSONObject(data);

        Location location = new Location();

        JSONObject coordObj = getObject("coord", jsonObject);
        location.setLatitude(getFloat("lat", coordObj));
        location.setLongitude(getFloat("lon", coordObj));

        JSONObject sysObj = getObject("sys", jsonObject);
        location.setCountry(getString("country", sysObj));
        location.setSunrise(getInt("sunrise", sysObj));
        location.setSunset(getInt("sunset", sysObj));
      //  JSONObject nameObj = getObject("name", jsonObject);
        location.setCity(getString("name", jsonObject));
        weather.location = location;

        // We get weather info (This is an array)
        JSONArray jArr = jsonObject.getJSONArray("weather");

        // We use only the first value
        JSONObject JSONWeather = jArr.getJSONObject(0);
        weather.currentCondition.setWeatherId(getInt("id", JSONWeather));
        weather.currentCondition.setDescr(getString("description", JSONWeather));
        weather.currentCondition.setCondition(getString("main", JSONWeather));
        weather.currentCondition.setIcon(getString("icon", JSONWeather));

        JSONObject mainObj = getObject("main", jsonObject);
        weather.currentCondition.setHumidity(getInt("humidity", mainObj));
        weather.currentCondition.setPressure(getInt("pressure", mainObj));
        weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weather.temperature.setTemp(getFloat("temp", mainObj));

        // Wind
        JSONObject wObj = getObject("wind", jsonObject);
        weather.wind.setSpeed(getFloat("speed", wObj));
        weather.wind.setDeg(getFloat("deg", wObj));

        // Clouds
        JSONObject cObj = getObject("clouds", jsonObject);
        weather.clouds.setPerc(getInt("all", cObj));

        return weather;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}
