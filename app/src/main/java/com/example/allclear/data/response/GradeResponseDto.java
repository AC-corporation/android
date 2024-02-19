package com.example.allclear.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GradeResponseDto {
    @SerializedName("isSuccess")
    boolean isSuccess;

    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    public GradeData data;

    public static class GradeData {
        @SerializedName("totalCredit")
        double totalCredit;

        @SerializedName("averageGrade")
        String averageGrade;

        public double getTotalCredit() {
            return totalCredit;
        }

        public String getAverageGrade() {
            return averageGrade;
        }

        @SerializedName("semesterGradeDtoList")
        public List<SemesterGradeDto> semesterGradeDtoList;

        public static class SemesterGradeDto {
            @SerializedName("semesterGradeId")
            long semesterGradeId;

            @SerializedName("semesterAverageGrade")
            public String semesterAverageGrade;

            @SerializedName("semesterSubjectDtoList")
            List<SemesterSubjectDto> semesterSubjectDtoList;

            static class SemesterSubjectDto {
                @SerializedName("semesterSubjectName")
                String semesterSubjectName;

                @SerializedName("semesterSubjectScore")
                String semesterSubjectScore;
            }
        }
    }
}

