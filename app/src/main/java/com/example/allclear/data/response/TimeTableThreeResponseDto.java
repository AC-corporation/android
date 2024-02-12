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
    private TimeTableThreeResponseData data;

    public TimeTableThreeResponseData getData() {
        return data;
    }

    public static class TimeTableThreeResponseData {
        private List<RequirementComponentResponseDto> requirementComponentResponseDtoList;
        private List<SubjectResponseDto> subjectResponseDtoList;

        public List<RequirementComponentResponseDto> getRequirementComponentResponseDtoList() {
            return requirementComponentResponseDtoList;
        }

        public List<SubjectResponseDto> getSubjectResponseDtoList() {
            return subjectResponseDtoList;
        }
    }

    public static class RequirementComponentResponseDto {
        @SerializedName("requirementComponentId")
        private Long requirementComponentId;
        @SerializedName("requirementCategory")
        private String requirementCategory;
        @SerializedName("requirementArgument")
        private String requirementArgument;
        @SerializedName("requirementCriteria")
        private double requirementCriteria;
        @SerializedName("requirementComplete")
        private double requirementComplete;
        @SerializedName("requirementResult")
        private String requirementResult;

        public String getRequirementArgument() {
            return requirementArgument;
        }

        public double getRequirementCriteria() {
            return requirementCriteria;
        }

        public double getRequirementComplete() {
            return requirementComplete;
        }
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
        public List<ClassInfoResponseDto> classInfoResponseDtoList;

        public String getSubjectName() {
            return subjectName;
        }

        public double getSubjectTime() {
            return subjectTime;
        }

        public Long getSubjectId() {
            return subjectId;
        }

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

            public String getProfessor() {
                return professor;
            }
        }
    }
}


