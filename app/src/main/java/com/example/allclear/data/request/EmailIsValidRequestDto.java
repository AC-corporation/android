package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class EmailIsValidRequestDto {
    @SerializedName("email")
    private String email;

    @SerializedName("code")
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
