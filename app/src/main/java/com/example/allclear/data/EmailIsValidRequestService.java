package com.example.allclear.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailIsValidRequestService {
    @POST("user/signup/emailIsValid")
    Call<TestResponseDto> emailIsValid(@Body EmailIsValidRequestDto emailIsValidRequestDto);
}
