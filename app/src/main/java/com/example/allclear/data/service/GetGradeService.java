package com.example.allclear.data.service;

import com.example.allclear.data.response.GradeResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetGradeService {
    @GET("/grade/getGrade/{userId}")
    Call<GradeResponseDto> getGradeData(@Path("userId") String userId);
}

