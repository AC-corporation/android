package com.example.allclear.data.service;

import com.example.allclear.data.request.EmailAuthRequestDto;
import com.example.allclear.data.response.TestResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailAuthRequestService {
    @POST("user/signup/emailAuth")
    Call<TestResponseDto> emailAuth(@Body EmailAuthRequestDto emailAuthRequestDto);
}
