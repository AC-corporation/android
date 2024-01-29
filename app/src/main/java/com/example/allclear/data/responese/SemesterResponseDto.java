package com.example.allclear.data.responese;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SemesterResponseDto {
    @SerializedName("semesterGradeId")
    private Long semesterGradeId;
    @SerializedName("semesterAverageGrade")
    private String semesterAverageGrade;
    @SerializedName("semesterSubjectDtoList")
    private ArrayList<SemesterSubjectDtoList> semesterSubjectDtoList;

    class SemesterSubjectDtoList{
        @SerializedName("semesterSubjectName")
        private String semesterSubjectName;
        @SerializedName("semesterSubjectScore")
        private String semesterSubjectScore;
    }
}
