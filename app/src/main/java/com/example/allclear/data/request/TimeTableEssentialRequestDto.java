package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTableEssentialRequestDto {
    @SerializedName("minCredit")
    private int minCredit;
    @SerializedName("maxCredit")
    private int maxCredit;
    @SerializedName("minMajorCredit")
    private int minMajorCredit;
    @SerializedName("maxMajorCredit")
    private int maxMajorCredit;
    @SerializedName("timetableGeneratorSubjectIdList")
    public List<Long> timetableGeneratorSubjectIdList;

    public void setTimetableGeneratorSubjectIdList(List<Long> timetableGeneratorSubjectIdList) {
        this.timetableGeneratorSubjectIdList = timetableGeneratorSubjectIdList;
    }
}
