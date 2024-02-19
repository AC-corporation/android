package com.example.allclear.data.response;

import com.google.gson.annotations.SerializedName;

public class TestResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private String data;

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
        if (code!=null) return code;
        else return "알수없는 오류 코드";
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getData() {
        return data;
    }
}
