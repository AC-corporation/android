package com.example.allclear.data;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TestService {
    @GET("user/test/ok")
    Call<List<TestResponseDto>> getListFromServer();
}