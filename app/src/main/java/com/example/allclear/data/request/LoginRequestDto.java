package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequestDto {
    @SerializedName("email") private String email;
    @SerializedName("password") private String password;

    public void init(String email,String password){
        this.email = email;
        this.password = password;
    }
}
