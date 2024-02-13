package com.example.allclear.data.service;

import com.example.allclear.data.response.TestResponseDto;
import com.example.allclear.data.request.MemberSignupRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {
    @POST("user/signup")
    Call<TestResponseDto> signUp(@Body MemberSignupRequestDto memberSignupRequestDto);
}
