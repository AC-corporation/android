package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequestDto {
    @SerializedName("currentPassword")
    private String currentPassword;
    @SerializedName("newPassword")
    private String newPassword;

    public void init(String currentPassword,String newPassword){
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
