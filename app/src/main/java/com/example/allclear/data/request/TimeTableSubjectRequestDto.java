package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTableSubjectRequestDto {

    @SerializedName("timetableGeneratorSubjectIdList")
    private List<Long> timetableGeneratorSubjectIdList;

}
