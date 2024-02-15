package com.example.allclear.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateUserService {
    @PUT("user/updateUser/{userId}")
    Call<TestResponseDto> updateUser(@Path("userId") Long userId, @Body UsaintUpdateRequestDto usaintUpdateRequestDto);
}