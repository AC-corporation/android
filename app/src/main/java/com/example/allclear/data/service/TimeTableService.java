package com.example.allclear.data.service;

import com.example.allclear.data.request.TimeTableOneRequestDto;
import com.example.allclear.data.request.TimeTableTwoRequestDto;
import com.example.allclear.data.response.TestResponseDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.data.response.TimeTableThreeResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TimeTableService {
    @POST("timetableGenerator/step1/{userId}")
    Call<TimeTableResponseDto> postStepOne(
            @Path("userId") long userId,
            @Body TimeTableOneRequestDto request
    );

    @POST("timetableGenerator/step2/{userId}")
    Call<TimeTableResponseDto> postStepTwo(
            @Path("userId") long userId,
            @Body List<TimeTableTwoRequestDto> request
    );

    @GET("timetableGenerator/step3/{userId}")
    Call<TimeTableThreeResponseDto> getStepThree(
            @Path("userId") long userId
    );

}
