package com.example.allclear.data.service;

import com.example.allclear.data.responese.GradeDto;
import com.example.allclear.data.responese.SemesterGradeDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GradeService {
    @GET("grade/getGrade/{userId}")
    Call<GradeDto> getTotalGradeList(@Path("userId") int userId);

    @GET("grade/getSemesterGrade/{semesterGradeId}")
    Call<SemesterGradeDto> getSemesterGradeList(@Path("semesterGradeId") long semesterGradeId);
}
