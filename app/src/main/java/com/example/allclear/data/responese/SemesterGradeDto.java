package com.example.allclear.data.responese;

import com.google.gson.annotations.SerializedName;

public class SemesterGradeDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private SemesterResponseDto data;
}
