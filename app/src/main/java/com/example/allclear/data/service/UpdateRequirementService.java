package com.example.allclear.data.service;

import com.example.allclear.data.request.UsaintUpdateRequestDto;
import com.example.allclear.data.response.TestResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateRequirementService {
    @PUT("user/updateRequirement/{userId}")
    Call<TestResponseDto> updateRequirement(@Header("Authorization") String authHeader, @Path("userId") Long userId, @Body UsaintUpdateRequestDto usaintUpdateRequestDto);
}