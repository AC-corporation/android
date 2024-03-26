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
        public double getTotalCredit() {
            return totalCredit;
        }

        public String getAverageGrade() {
            return averageGrade;
        }

        @SerializedName("totalCredit")
        double totalCredit;

        @SerializedName("averageGrade")
        String averageGrade;

        @SerializedName("semesterGradeDtoList")
        public List<SemesterGradeDto> semesterGradeDtoList;

        public static class SemesterGradeDto {
            @SerializedName("semesterGradeId")
            public long semesterGradeId;

            @SerializedName("semesterAverageGrade")
            public String semesterAverageGrade;

            @SerializedName("semesterTitle")
            public String semesterTitle;

            @SerializedName("semesterSubjectDtoList")
            public
            List<SemesterSubjectDto> semesterSubjectDtoList;

            public static class SemesterSubjectDto {
                @SerializedName("semesterSubjectName")
                public
                String semesterSubjectName;

                @SerializedName("semesterSubjectScore")
                public
                String semesterSubjectScore;
            }
        }
    }
}

