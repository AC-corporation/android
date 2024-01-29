package com.example.allclear.data.request;

import com.example.allclear.data.request.EmailIsValidRequestDto;
import com.example.allclear.data.responese.TestResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailIsValidRequestService {
    @POST("user/signup/emailIsValid")
    Call<TestResponseDto> emailIsValid(@Body EmailIsValidRequestDto emailIsValidRequestDto);
}
