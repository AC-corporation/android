package com.example.allclear.data.responese;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SemesterGradeDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private SemesterResponseDto data;

    public static class SemesterResponseDto {
        @SerializedName("semesterGradeId")
        private Long semesterGradeId;
        @SerializedName("semesterAverageGrade")
        private String semesterAverageGrade;
        @SerializedName("semesterSubjectDtoList")
        private ArrayList<SemesterSubjectDtoList> semesterSubjectDtoList;

        public static class SemesterSubjectDtoList {
            @SerializedName("semesterSubjectName")
            private String semesterSubjectName;
            @SerializedName("semesterSubjectScore")
            private String semesterSubjectScore;
        }
    }

}
