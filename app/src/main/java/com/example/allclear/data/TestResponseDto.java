package com.example.allclear.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TestResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private String data;

    public boolean getIsSuccess() {
        return isSuccess;
    }
    public String getCode() {
        return code;
    }
}
