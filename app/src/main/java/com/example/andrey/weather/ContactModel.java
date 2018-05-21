package com.example.andrey.weather;

/**
 * Created by Andrey on 14.03.2018.
 */

public class ContactModel {
    private String ID, firstName, countryName, temperature;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setTemperature(String temperature){this.temperature = temperature;}
    public String getTemperature(){return temperature;}
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
