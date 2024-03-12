package com.example.allclear.timetable;

import java.io.Serializable;

public class DataModel_timeplace implements Serializable {
    private String starttime;
    private String endtime;
    private String day;
    private String place;

    public DataModel_timeplace(String starttime, String endtime,String day,String place) {
        this.starttime = starttime;
        this.endtime=endtime;
        this.day=day;
        this.place = place;
    }

    public void setStarttime(String starttime) {
        this.starttime=starttime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setDay(String day) {
        this.day = day;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getDay() {
        return day;
    }

    public String getPlace() {
        return place;
    }
}
