package com.example.allclear.data.responese;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GradeDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    public GradeResponseDto data;

    public static class GradeResponseDto {
        @SerializedName("totalCredit")
        private double totalCredit;
        @SerializedName("averageGrade")
        private String averageGrade;
        @SerializedName("semesterGradeDtoList")
        private ArrayList<SemesterGradeDtoList> semesterGradeDtoList;

        public ArrayList<SemesterGradeDtoList> getSemesterGradeDtoList() {
            return this.semesterGradeDtoList;
        }

        public static class SemesterGradeDtoList {
            @SerializedName("semesterGradeId")
            private Long semesterGradeId;
            @SerializedName("semesterAverageGrade")
            private String semesterAverageGrade;
            @SerializedName("semesterSubjectDtoList")
            private ArrayList<SemesterSubjectDtoList> semesterSubjectDtoList;

            public ArrayList<SemesterSubjectDtoList> getSemesterSubjectDtoList() {
                return this.semesterSubjectDtoList;
            }

            public static class SemesterSubjectDtoList {
                @SerializedName("semesterSubjectName")
                private String semesterSubjectName;
                @SerializedName("semesterSubjectScore")
                private String semesterSubjectScore;

                public String getSemesterSubjectName() {
                    return this.semesterSubjectName;
                }

                public String getSemesterSubjectScore() {
                    return this.semesterSubjectScore;
                }
            }
        }
    }
}
