package com.example.allclear.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTableEssentialResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private TimeTableResponseData data;

    public TimeTableResponseData getData() {
        return data;
    }

    public static class TimeTableResponseData {
        private List<timetableGeneratorSubjectResponseDtoList> subjectResponseDtoList;

        public List<timetableGeneratorSubjectResponseDtoList> getSubjectResponseDtoList() {
            return subjectResponseDtoList;
        }
    }

    public static class timetableGeneratorSubjectResponseDtoList {
        @SerializedName("timetableSubjectId")
        private Long timetableSubjectId;
        @SerializedName("subjectId")
        private Long subjectId;
        @SerializedName("subjectName")
        private String subjectName;
        @SerializedName("classInfoResponseDtoList")
        private List<classInfoResponseDtoList> classInfoResponseDtoList;

        public List<classInfoResponseDtoList> getClassInfoResponseDtoList() {
            return classInfoResponseDtoList;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public Long getSubjectId() {
            return subjectId;
        }

        public static class classInfoResponseDtoList {
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

            public String getProfessor() {
                return professor;
            }
        }
    }
}

