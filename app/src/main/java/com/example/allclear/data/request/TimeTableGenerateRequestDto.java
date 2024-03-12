package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class TimeTableGenerateRequestDto {
    @SerializedName("tableName")
    private String tableName;
    @SerializedName("tableYear")
    private int tableYear;
    @SerializedName("semester")
    private int semester;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableYear(int tableYear) {
        this.tableYear = tableYear;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
