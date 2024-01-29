package com.example.allclear.data.service;

import com.example.allclear.data.responese.GraduationDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GraduationService {
    @GET("requirement/get/{userId}")
    Call<GraduationDto> getListFromServer(@Path("userId") int userId);
}
