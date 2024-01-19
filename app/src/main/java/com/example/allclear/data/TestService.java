package com.example.allclear.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TestService {
    @GET("user/test/ok")
    Call<TestResponseDto> getListFromServer();

    @POST("user/login")
    Call<LoginResponseDto> Login(@Body LoginRequestDto loginRequestDto);
}