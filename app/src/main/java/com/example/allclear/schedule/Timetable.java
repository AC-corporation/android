package com.example.allclear.schedule;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Semester.class,
        parentColumns = "id",
        childColumns = "semesterId"))
public class Timetable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;  // 시간표 이름 (예: "시간표1")

    public int semesterId;  // 속한 학기의 ID
}
