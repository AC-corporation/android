package com.example.allclear.data.service;

import com.example.allclear.data.request.LoginRequestDto;
import com.example.allclear.data.response.LoginResponseDto;
import com.example.allclear.data.response.TestResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TestService {
    @GET("user/test/ok")
    Call<TestResponseDto> getListFromServer();

    @POST("user/login")
    Call<LoginResponseDto> Login(@Body LoginRequestDto loginRequestDto);
}