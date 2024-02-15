package com.example.allclear.data;

public class UsaintUpdateRequestDto {
    String usaintId;
    String usaintPassword;

    //생성자
    public UsaintUpdateRequestDto(String usaintId, String usaintPassword) {
        this.usaintId = usaintId;
        this.usaintPassword = usaintPassword;
    }
}