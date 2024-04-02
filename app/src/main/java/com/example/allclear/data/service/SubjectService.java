package com.example.allclear.data.service;

import com.example.allclear.data.response.SubjectAllDto;
//import com.example.allclear.data.request.SubjectSearchsRequestDto;
//import com.example.allclear.data.response.GraduationDto;
//import com.example.allclear.data.response.SubjectDto;
//import com.example.allclear.data.response.SubjectSearchsResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SubjectService {
//    @GET("subject/{subjectId}")
//    Call<SubjectDto> getSubjectServer(
//            @Header("authorization") String authHeader,
//            @Path("subjectId") Long subjectId);
    @GET("subject/findAll")
    Call<SubjectAllDto> getSubjectAllServer(
            @Header("authorization") String authHeader,
            @Query("page") int page);
//    @POST("subject/searchs")
//    Call<SubjectSearchsResponseDto> postSubjectSearchsServer(
//            @Header("authorization") String authHeader,
//            @Path("page") int page,
//            @Body() SubjectSearchsRequestDto subjectSearchsResponseDto);
}
