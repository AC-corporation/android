package com.example.allclear.data.service;

import com.example.allclear.data.request.TimeTableListRequestDto;
import com.example.allclear.data.response.TimeTableListResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TimeTableListRequestService {
    @GET("timetable/list/{userId}")
    Call<TimeTableListResponseDto> GetTimeTable(@Header("Authorization") String authHeader, @Path("userId") Long userId);
}
