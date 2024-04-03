package com.example.allclear.data.service;

import com.example.allclear.data.response.TimetableListResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TimeTableListRequestService {
    @GET("timetable/list/{userId}")
    Call<TimetableListResponseDto> GetTimeTable(@Header("Authorization") String authHeader, @Path("userId") Long userId);
}
