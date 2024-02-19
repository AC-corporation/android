package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

//회원가입을 위한 user 객체
public class MemberSignupRequestDto {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("usaintId")
    private String usaintId;

    @SerializedName("usaintPassword")
    private String usaintPassword;

    @SerializedName("role")
    private String role = "USER" ;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsaintId() {
        return usaintId;
    }

    public void setUsaintId(String usaintId) {
        this.usaintId = usaintId;
    }

    public String getUsaintPassword() {
        return usaintPassword;
    }

    public void setUsaintPassword(String usaintPassword) {
        this.usaintPassword = usaintPassword;
    }
}
