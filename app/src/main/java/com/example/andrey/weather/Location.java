package com.example.andrey.weather;

import java.io.Serializable;


public class Location implements Serializable {
    private String city;
    private String country;
    private String CountryCode;

    private float longitude;
    private float latitude;

    private long sunset;
    private long sunrise;

    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }

    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }

    public String getCountryCode(){
        return CountryCode;
    }
    public void setCountryCode(String countryCode){
        this.CountryCode = countryCode;
    }

    public float getLongitude() {
        return longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    public long getSunset() {
        return sunset;
    }
    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
    public long getSunrise() {
        return sunrise;
    }
    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }




}
