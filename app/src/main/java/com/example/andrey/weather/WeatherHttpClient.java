package com.example.andrey.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class WeatherHttpClient {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String LAST = "&appid=17d96931d2482c0f29b544ef190e58f9";


    public String getWeatherData(String location){
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection)
                    (new URL(BASE_URL + location + LAST)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            StringBuffer buffer = new StringBuffer();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader =
                    new BufferedReader( new InputStreamReader(inputStream));
            String line=null;
            while ((line = bufferedReader.readLine()) != null)
                buffer.append(line + "\r\n");
            inputStream.close();
            connection.disconnect();
            return buffer.toString();
        }
         catch (Throwable e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            }catch (Throwable t){}
            try {
                connection.disconnect();
            }catch (Throwable t){}
        }
        return null;
    }

}
