package com.example.allclear.data.response;

import com.google.gson.annotations.SerializedName;

public class TestResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private String data;
}
