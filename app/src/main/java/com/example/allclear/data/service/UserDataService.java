package com.example.allclear.data.service;

import com.example.allclear.data.request.ChangePasswordRequestDto;
import com.example.allclear.data.request.UserDeleteRequestDto;
import com.example.allclear.data.response.UserDataResponseDto;
import com.example.allclear.data.response.UserDataResponseDtoTwo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserDataService {
    @GET("user/{userId}")
    Call<UserDataResponseDto> getUserData(@Header("Authorization") String authHeader, @Path("userId") Long userId);
    @POST("user/changePassword/{userId}")
    Call<UserDataResponseDto> changePassword(@Header("Authorization") String authHeader, @Path("userId") Long userId, @Body ChangePasswordRequestDto changePassword);
    @GET("user/logout/{userId}")
    Call<UserDataResponseDto> logout(@Header("Authorization") String authHeader, @Path("userId") Long userId);
    @POST("user/{userId}")
    Call<UserDataResponseDto> deleteUser(@Header("Authorization") String authHeader, @Path("userId") Long userId, @Body UserDeleteRequestDto userDelete);
}
