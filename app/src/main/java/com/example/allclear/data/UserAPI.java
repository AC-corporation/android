package com.example.allclear.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {
    @POST("user/signup")
    Call<Void> signUp(@Body User user);
}
