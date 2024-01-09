package com.example.allclear.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {
    @POST("user/signup")
    Call<List<TestResponseDto>> signUp(@Body MemberSignupRequestDto memberSignupRequestDto);
}
