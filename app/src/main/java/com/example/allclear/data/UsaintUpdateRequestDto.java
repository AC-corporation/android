package com.example.allclear.data;

import com.google.gson.annotations.SerializedName;

public class UsaintUpdateRequestDto {
    @SerializedName("usaintId")
    String usaintId;
    @SerializedName("usaintPassword")
    String usaintPassword;

    //생성자
    public UsaintUpdateRequestDto(String usaintId, String usaintPassword) {
        this.usaintId = usaintId;
        this.usaintPassword = usaintPassword;
    }
}