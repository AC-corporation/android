package com.example.allclear.data.responese;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

class GradeResponseDto {
    @SerializedName("totalCredit")
    private double totalCredit;
    @SerializedName("averageGrade")
    private String averageGrade;
    @SerializedName("semesterGradeDtoList")
    private ArrayList<SemesterGradeDtoList> semesterGradeDtoList;

    class SemesterGradeDtoList {
        @SerializedName("semesterGradeId")
        private Long semesterGradeId;
        @SerializedName("semesterAverageGrade")
        private String semesterAverageGrade;
        @SerializedName("semesterSubjectDtoList")
        private ArrayList<SemesterSubjectDtoList> semesterSubjectDtoList;

        class SemesterSubjectDtoList {
            @SerializedName("semesterSubjectName")
            private String semesterSubjectName;
            @SerializedName("semesterSubjectScore")
            private String semesterSubjectScore;
        }
    }
}
