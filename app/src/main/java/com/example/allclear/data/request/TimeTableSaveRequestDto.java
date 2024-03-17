package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class TimeTableSaveRequestDto {
    @SerializedName("timetableGeneratorTimetableId")
    private Long timetableGeneratorTimetableId;

    public void setTimetableGeneratorTimetableId(Long timetableGeneratorTimetableId) {
        this.timetableGeneratorTimetableId = timetableGeneratorTimetableId;
    }
}
