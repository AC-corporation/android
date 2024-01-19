package com.example.allclear.data;

import com.google.gson.annotations.SerializedName;

public class LoginResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Long data;
    public void setIsSuccess(boolean success) {
        isSuccess = success;
    }
    public boolean getIsSuccess(){
        return isSuccess;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setData(Long data) {
        this.data = data;
    }
    public Long getData() {
        return data;
    }
}
