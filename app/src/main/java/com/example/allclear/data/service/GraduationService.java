package com.example.allclear.data.service;

import com.example.allclear.data.response.GraduationDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface GraduationService {
    @GET("requirement/{userId}")
    Call<GraduationDto> getListFromServer(
            @Header("authorization") String authHeader,
            @Path("userId") Long userId);
}