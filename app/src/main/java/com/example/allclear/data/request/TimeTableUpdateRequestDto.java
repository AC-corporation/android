package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTableUpdateRequestDto {
    @SerializedName("tableName")
    private String tableName;

    @SerializedName("timetableSubjectRequestDtoList")
    private List<TimetableSubjectRequestDto> timetableSubjectRequestDtoList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TimetableSubjectRequestDto> getTimetableSubjectRequestDtoList() {
        return timetableSubjectRequestDtoList;
    }

    public void setTimetableSubjectRequestDtoList(List<TimetableSubjectRequestDto> timetableSubjectRequestDtoList) {
        this.timetableSubjectRequestDtoList = timetableSubjectRequestDtoList;
    }

    public static class TimetableSubjectRequestDto {

        @SerializedName("subjectName")
        private String subjectName;

        @SerializedName("subjectId")
        private int subjectId;

        @SerializedName("classInfoRequestDtoList")
        private List<ClassInfoRequestDto> classInfoRequestDtoList;

        public String getSubjectName(){
            return subjectName;
        }
        public void setSubjectName(String subjectName){
            this.subjectName=subjectName;
        }
        public int getSubjectId(){
            return subjectId;
        }
        public void setSubjectId(int subjectId){
            this.subjectId=subjectId;
        }
        public List<ClassInfoRequestDto>getClassInfoRequestDtoList(){
            return classInfoRequestDtoList;
        }
        public void setClassInfoRequestDtoList(List<ClassInfoRequestDto>classInfoRequestDtoList){
            this.classInfoRequestDtoList=classInfoRequestDtoList;
        }
    }
    public static class ClassInfoRequestDto {
        @SerializedName("professor")
        private String professor;
        @SerializedName("classDay")
        private String classDay;
        @SerializedName("startTime")
        private String startTime;
        @SerializedName("endTime")
        private String endTime;
        @SerializedName("classRoom")
        private String classRoom;

        public void setProfessor(String professor) {
            this.professor = professor;
        }
        public String getProfessor(){return professor;}

        public void setClassDay(String classDay) {
            this.classDay = classDay;
        }
        public String getClassDay(){return classDay;}

        public void setStartTime(String startTime) {this.startTime = startTime;}
        public String getStartTime(){return startTime;}

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
        public String getEndTime(){return endTime;}

        public void setClassRoom(String classRoom) {
            this.classRoom = classRoom;
        }
        public String getClassRoom(){return classRoom;}
    }
}
