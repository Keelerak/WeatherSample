package com.example.andrey.weather;


public class CitySearch
{
    private String mCityName;
    private String mCountry;
    private String mLatitude;
    private String mLongitude;
    private String mCountryCode;
    private String mTemperature;

    public CitySearch(){}
    public CitySearch(String cityName, String countryCode,
                      String latitude, String longitude)
    {
        mCityName = cityName;
        mCountryCode = countryCode;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public String getCityName()
    {
        return mCityName;
    }

    public void setCityName(String cityName)
    {
        mCityName = cityName;
    }

    public float getTemperature()
    {
        return Float.parseFloat(mTemperature);
    }

    public void setTemperature(String temperature)
    {
        mTemperature = temperature;
    }

    public String getCountry()
    {
        return mCountry;
    }

    public void setCountry(String country)
    {
        mCountry = country;
    }

    public String getLatitude()
    {
        return mLatitude;
    }

    public void setLatitude(String latitude)
    {
        mLatitude = latitude;
    }

    public String getLongitude()
    {
        return mLongitude;
    }

    public void setLongitude(String longitude)
    {
        mLongitude = longitude;
    }

    public String getCountryCode()
    {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode)
    {
        mCountryCode = countryCode;
    }


    @Override
    public String toString()
    {
        return mCityName + ", " + mCountryCode;
    }
}