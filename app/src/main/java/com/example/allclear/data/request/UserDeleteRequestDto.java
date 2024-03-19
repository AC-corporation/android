package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class UserDeleteRequestDto {
    @SerializedName("password")
    private String password;
    public void init(String password){
        this.password = password;
    }
}
