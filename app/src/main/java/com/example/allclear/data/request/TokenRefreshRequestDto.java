package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class TokenRefreshRequestDto {
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("refreshToken")
    private String refreshToken;

    public void init(String accessToken,String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
