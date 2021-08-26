package com.shoppa.Model;

public class CountryModel {

    String CountryId, CountryName;

    public CountryModel(String countryId, String countryName) {
        CountryId = countryId;
        CountryName = countryName;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }
}
