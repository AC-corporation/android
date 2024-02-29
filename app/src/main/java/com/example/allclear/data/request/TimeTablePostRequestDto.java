package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTablePostRequestDto {
    @SerializedName("subjectIdList")
    private List<Long> subjectIdList;

    public List<Long> getSubjectIdList() {
        return subjectIdList;
    }

    public void setSubjectIdList(List<Long> subjectIdList) {
        this.subjectIdList = subjectIdList;
    }
}
