package com.example.allclear.data.service;

import com.example.allclear.data.response.GradeResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface GetGradeService {
    @GET("/grade/getGrade/{userId}")
    Call<GradeResponseDto> getGradeData(@Header("Authorization") String authHeader, @Path("userId") Long userId);
}

