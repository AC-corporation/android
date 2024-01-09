package com.example.allclear.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {
    @POST("user/signup")
    Call<Void> signUp(@Body MemberSignupRequestDto memberSignupRequestDto);
}
