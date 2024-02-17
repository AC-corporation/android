package com.example.allclear.data.response;

import com.example.allclear.data.Login;
import com.google.gson.annotations.SerializedName;

public class LoginResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Login data;
//    @Override
//    public String toString(){
//        return "PostResult{" +
//                "isSuccess=" + isSuccess +
//                ", code=" + code +
//                ", message='" + message + '\'' +
//                ", data='" + data + '\'' +
//                '}';
//    }
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
    public void setData(Login data) {
        this.data = data;
    }
    public Login getData() {
        return data;
    }
}
