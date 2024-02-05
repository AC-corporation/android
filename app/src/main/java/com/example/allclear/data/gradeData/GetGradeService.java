package com.example.allclear.data.gradeData;

import com.example.allclear.data.EmailAuthRequestDto;
import com.example.allclear.data.TestResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetGradeService {
    @POST("/grade/getGrade/{userId}")
    Call<GradeResponseDto> getGradeData(@Query("userId") String userId);
}
