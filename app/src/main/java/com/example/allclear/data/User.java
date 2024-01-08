package com.example.allclear.data;

//회원가입을 위한 user 객체
public class User {
    private String email;
    private String password;
    private String usaintId;
    private String usaintPassword;

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

