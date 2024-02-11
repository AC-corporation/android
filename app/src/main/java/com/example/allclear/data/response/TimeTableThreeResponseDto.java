package com.example.allclear.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTableThreeResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    public TimeTableThreeResponseData data;

    public static class TimeTableThreeResponseData {
        public List<RequirementComponentResponseDto> requirementComponentResponseDtoList;
        private List<SubjectResponseDto> subjectResponseDtoList;
    }

    public static class RequirementComponentResponseDto {
        @SerializedName("requirementComponentId")
        private Long requirementComponentId;
        @SerializedName("requirementCategory")
        private String requirementCategory;
        @SerializedName("requirementArgument")
        public String requirementArgument;
        @SerializedName("requirementCriteria")
        public double requirementCriteria;
        @SerializedName("requirementComplete")
        public double requirementComplete;
        @SerializedName("requirementResult")
        private String requirementResult;
    }

    public static class SubjectResponseDto {
        @SerializedName("subjectId")
        private Long subjectId;
        @SerializedName("subjectName")
        private String subjectName;
        @SerializedName("department")
        private String department;
        @SerializedName("majorClassification")
        private String majorClassification;
        @SerializedName("multiMajorClassification")
        private String multiMajorClassification;
        @SerializedName("engineeringCertification")
        private String engineeringCertification;
        @SerializedName("liberalArtsClassification")

        private String liberalArtsClassification;
        @SerializedName("classType")
        private String classType;
        @SerializedName("credit")
        private int credit;
        @SerializedName("design")
        private String design;
        @SerializedName("subjectTime")
        private double subjectTime;
        @SerializedName("subjectTarget")
        private String subjectTarget;
        @SerializedName("classInfoResponseDtoList")
        private List<ClassInfoResponseDto> classInfoResponseDtoList;

        public static class ClassInfoResponseDto {
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
        }
    }
}


