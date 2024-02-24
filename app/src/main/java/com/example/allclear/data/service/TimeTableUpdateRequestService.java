package com.example.allclear.data.service;

import com.example.allclear.data.request.TimeTableUpdateRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TimeTableUpdateRequestService {
@PUT("timetable/{timetableId}")
Call<TimeTableUpdateRequestDto> TimeTableUpdate(
        @Header("Authorization") String authHeader,
        @Path("timetableId") long timetableId,
        @Body TimeTableUpdateRequestDto request
);

}
