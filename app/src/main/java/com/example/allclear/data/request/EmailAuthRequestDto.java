package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class EmailAuthRequestDto {
    @SerializedName("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
