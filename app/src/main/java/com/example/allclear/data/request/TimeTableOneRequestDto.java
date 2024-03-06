package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class TimeTableOneRequestDto {
    @SerializedName("tableYear")
    private int tableYear;
    @SerializedName("semester")
    private int semester;

    public void setTableYear(int tableYear) {
        this.tableYear = tableYear;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

}
