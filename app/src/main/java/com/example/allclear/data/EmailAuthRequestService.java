package com.example.allclear.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailAuthRequestService {
    @POST("user/signup/emailAuth")
    Call<List<TestResponseDto>> emailAuth(@Body EmailAuthRequestDto emailAuthRequestDto);
}
