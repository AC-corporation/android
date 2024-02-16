package com.example.allclear.data;

import com.example.allclear.data.response.TestResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateUserService {
    @PUT("user/updateUser/{userId}")
    Call<TestResponseDto> updateUser(@Header("Authorization") String authHeader, @Path("userId") Long userId, @Body UsaintUpdateRequestDto usaintUpdateRequestDto);
}