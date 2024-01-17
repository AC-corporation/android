package com.example.allclear;

import java.io.Serializable;

public class Schedule implements Serializable {
    private int originId;
    private String scheduleName;
    private String professor;
    private int scheduleDay;
     private String startTime;
    private String endTime;
    private String roomInfo;
    private String backgroundColor= "#dddddd";
    private String textColor= "#ffffff";
    public int getOriginId(){
        return originId;
    }
    public void setOriginId(int originId){
        this.originId=originId;
    }
    public String getScheduleName(){
        return  scheduleName;
    }
    public void setScheduleName(String scheduleName){
        this.scheduleName=scheduleName;
    }
    public String getProfessor(){
        return professor;
    }
    public void setProfessor(String professor){
        this.professor=professor;
    }
    public int getScheduleDay(){
        return scheduleDay;
    }
    public void setScheduleDay(int scheduleDay){
        this.scheduleDay=scheduleDay;
    }
    public String getStartTime(){
        return startTime;
    }
    public void setStartTime(String startTime){
        this.startTime=startTime;
    }
    public String getEndTime(){
        return endTime;
    }
    public void setEndTime(String endTime){
        this.endTime=endTime;
    }
    public String getRoomInfo(){
        return roomInfo;
    }
    public void setRoomInfo(String roomInfo){
        this.roomInfo=roomInfo;
    }
    public String getBackgroundColor(){
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor){
        this.backgroundColor=backgroundColor;
    }
    public String getTextColor(){
        return textColor;
    }
    public void setTextColor(String textColor){
    this.textColor=textColor;
}
}
