package com.example.allclear.data.service;

import com.example.allclear.data.request.TimeTableOneRequestDto;
import com.example.allclear.data.response.TimeTableOneResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TimeTableService {
    @POST("timetableGenerator/step1/{userId}")
    Call<TimeTableOneResponseDto> postStepOne(
            @Path("userId") long userId,
            @Body TimeTableOneRequestDto request
    );


}
