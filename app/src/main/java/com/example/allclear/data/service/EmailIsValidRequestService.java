package com.example.allclear.data.service;

import com.example.allclear.data.request.EmailIsValidRequestDto;
import com.example.allclear.data.response.TestResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailIsValidRequestService {
    @POST("user/signup/emailIsValid")
    Call<TestResponseDto> emailIsValid(@Body EmailIsValidRequestDto emailIsValidRequestDto);
}
