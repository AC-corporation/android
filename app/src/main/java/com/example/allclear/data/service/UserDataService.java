package com.example.allclear.data.service;

import com.example.allclear.data.response.TestResponseDto;
import com.example.allclear.data.response.UserDataResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UserDataService {
    @GET("user/{userId}")
    Call<UserDataResponseDto> getUserData(@Header("Authorization") String authHeader, @Path("userId") Long userId);
}
