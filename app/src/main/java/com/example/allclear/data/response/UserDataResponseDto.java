package com.example.allclear.data.response;

import com.example.allclear.data.Login;
import com.google.gson.annotations.SerializedName;

public class UserDataResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private MemberResponseDto data;

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
    public void setData(MemberResponseDto data) {
        this.data = data;
    }
    public MemberResponseDto getData() {
        return data;
    }

    public static class MemberResponseDto{
        @SerializedName("memberId")
        private Long memberId;
        @SerializedName("email")
        private String email;
        @SerializedName("password")
        private String password;
        @SerializedName("memberName")
        private String memberName;
        @SerializedName("university")
        private String usiversity;
        @SerializedName("major")
        private String major;
        @SerializedName("classType")
        private String classType;
        @SerializedName("level")
        private int level;
        @SerializedName("semester")
        private int semester;

        public Long getMemberId() {
            return memberId;
        }

        public void setMemberId(Long memberId) {
            this.memberId = memberId;
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

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getUsiversity() {
            return usiversity;
        }

        public void setUsiversity(String usiversity) {
            this.usiversity = usiversity;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getClassType() {
            return classType;
        }

        public void setClassType(String classType) {
            this.classType = classType;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getSemester() {
            return semester;
        }

        public void setSemester(int semester) {
            this.semester = semester;
        }
    }
}