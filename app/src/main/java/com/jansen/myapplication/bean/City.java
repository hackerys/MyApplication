package com.jansen.myapplication.bean;

/**
 * Created Jansen on 2016/3/29.
 */
public class City {
    private String CityName;
    private int ProID;
    private int CitySort;

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String mCityName) {
        CityName = mCityName;
    }

    public int getProID() {
        return ProID;
    }

    public void setProID(int mProID) {
        ProID = mProID;
    }

    public int getCitySort() {
        return CitySort;
    }

    public void setCitySort(int mCitySort) {
        CitySort = mCitySort;
    }
}
