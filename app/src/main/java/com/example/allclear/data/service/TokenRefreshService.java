package com.example.allclear.data.service;

import com.example.allclear.data.request.TokenRefreshRequestDto;
import com.example.allclear.data.response.TokenRefreshResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TokenRefreshService {
    @POST("/auth/reissue")
    Call<TokenRefreshResponseDto> TokenRefresh(@Body TokenRefreshRequestDto tokenRefreshRequestDto);
}
