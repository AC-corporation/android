package com.example.allclear.data.service;

import com.example.allclear.data.request.TimeTableEssentialRequestDto;
import com.example.allclear.data.request.TimeTableGenerateRequestDto;
import com.example.allclear.data.request.TimeTableOneRequestDto;
import com.example.allclear.data.request.TimeTablePostRequestDto;
import com.example.allclear.data.request.TimeTableSaveRequestDto;
import com.example.allclear.data.request.TimeTableTwoRequestDto;
import com.example.allclear.data.response.TimeTableEssentialResponseDto;
import com.example.allclear.data.response.TimeTableGenerateResponseDto;
import com.example.allclear.data.response.TimeTableGetResponseDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.data.response.TimeTableSaveResponseDto;
import com.example.allclear.data.response.TimeTableStepEightResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TimeTableService {

    @POST("timetableGenerator/step1/{userId}")
    Call<TimeTableResponseDto> postStepOne(
            @Header("authorization") String authHeader,
            @Path("userId") long userId,
            @Body TimeTableOneRequestDto request
    );

    @POST("timetableGenerator/step2/{userId}")
    Call<TimeTableResponseDto> postStepTwo(
            @Header("authorization") String authHeader,
            @Path("userId") long userId,
            @Body TimeTableTwoRequestDto request
    );

    @GET("timetableGenerator/step3/{userId}")
    Call<TimeTableGetResponseDto> getStepThree(
            @Header("authorization") String authHeader,
            @Path("userId") long userId
    );

    @POST("timetableGenerator/step3to7/{userId}")
    Call<TimeTableResponseDto> postStepThreeToSeven(
            @Header("authorization") String authHeader,
            @Path("userId") long userId,
            @Body TimeTablePostRequestDto request
    );

    @GET("timetableGenerator/step4/{userId}")
    Call<TimeTableGetResponseDto> getStepFour(
            @Header("authorization") String authHeader,
            @Path("userId") long userId
    );

    @GET("timetableGenerator/step5/{userId}")
    Call<TimeTableGetResponseDto> getStepFive(
            @Header("authorization") String authHeader,
            @Path("userId") long userId
    );

    @GET("timetableGenerator/step6/{userId}")
    Call<TimeTableGetResponseDto> getStepSix(
            @Header("authorization") String authHeader,
            @Path("userId") long userId
    );

    @GET("timetableGenerator/step7/{userId}")
    Call<TimeTableEssentialResponseDto> getStepSeven(
            @Header("authorization") String authHeader,
            @Path("userId") long userId
    );

    @POST("timetableGenerator/step7/{userId}")
    Call<TimeTableResponseDto> postStepSeven(
            @Header("authorization") String authHeader,
            @Path("userId") long userId,
            @Body TimeTableEssentialRequestDto request
    );

    @POST("timetable/{userId}")
    Call<TimeTableGenerateResponseDto> postTimeTable(
            @Header("authorization") String authHeader,
            @Path("userId") long userId,
            @Body TimeTableGenerateRequestDto request
    );

    @GET("timetableGenerator/step8/{userId}")
    Call<TimeTableStepEightResponseDto> getStepEight(
            @Header("authorization") String authHeader,
            @Path("userId") long userId
    );

    @POST("timetableGenerator/step8/{userId}")
    Call<TimeTableSaveResponseDto> postSaveTimTable(
            @Header("authorization") String authHeader,
            @Path("userId") long userId,
            @Body TimeTableSaveRequestDto request
    );

}
