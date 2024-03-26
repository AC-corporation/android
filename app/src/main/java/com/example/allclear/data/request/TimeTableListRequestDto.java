package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class TimeTableListRequestDto {
    @SerializedName("userID")
    Long userId;

    public TimeTableListRequestDto(Long userId) {
        this.userId = userId;
    }
}
