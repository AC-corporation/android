package com.example.allclear.data.response;

import com.google.gson.annotations.SerializedName;

import java.time.LocalTime;
import java.util.List;

public class SubjectAllDto{
    @SerializedName("isSuccess")
    private Boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private SubjectAllResponseDto data;

    public String getCode() {
        return code;
    }

    public SubjectAllResponseDto getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class SubjectAllResponseDto{
        @SerializedName("currentPage")
        private int currentPage;
        @SerializedName("pageSize")
        private int pageSize;
        @SerializedName("totalPage")
        private int totalPage;
        @SerializedName("totalElement")
        private int totalElement;
        @SerializedName("subjectResponseDtoList")
        private List<SubjectInfoResponseDto> subjectAllResponseDtoList;

        public List<SubjectInfoResponseDto> getSubjectAllResponseDtoList() {
            return subjectAllResponseDtoList;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public int getTotalElement() {
            return totalElement;
        }
        public static class SubjectInfoResponseDto{
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
            private String credit;
            @SerializedName("design")
            private String design;
            @SerializedName("subjectTime")
            private String subjectTime;
            @SerializedName("subjectTarget")
            private String subjectTarget;
            @SerializedName("classInfoResponseDtoList")
            private List <ClassInfoResponseDto> classInfoResponseDtoList;

            public String getSubjectName() {
                return subjectName;
            }

            public String getCredit() {
                return credit;
            }

            public String getSubjectTarget() {
                return subjectTarget;
            }

            public List<ClassInfoResponseDto> getClassInfoResponseDtoList() {
                return classInfoResponseDtoList;
            }
            public static class ClassInfoResponseDto{
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
}