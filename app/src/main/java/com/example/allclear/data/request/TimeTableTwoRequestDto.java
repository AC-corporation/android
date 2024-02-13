package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTableTwoRequestDto {
    @SerializedName("subjectName")
    private String subjectName;
    @SerializedName("subjectId")
    private long subjectId;
    @SerializedName("classInfoRequestDtoList")
    private List<ClassInfoRequestDtoList> classInfoRequestDtoList;

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public void setClassInfoRequestDtoList(List<ClassInfoRequestDtoList> classInfoRequestDtoList) {
        this.classInfoRequestDtoList = classInfoRequestDtoList;
    }

    public static class ClassInfoRequestDtoList {
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

        public void setClassDay(String classDay) {
            this.classDay = classDay;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public void setClassRoom(String classRoom) {
            this.classRoom = classRoom;
        }
    }
}
