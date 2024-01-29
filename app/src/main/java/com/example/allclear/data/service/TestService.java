package com.example.allclear.data.service;

import com.example.allclear.data.responese.TestResponseDto;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TestService {
    @GET("user/test/ok")
    Call<List<TestResponseDto>> getListFromServer();
}