package com.example.allclear.schedule;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Semester.class,
        parentColumns = "id",
        childColumns = "semesterId"))
public class Timetable {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String name;  // 시간표 이름 (예: "시간표1")

    public Long semesterId;  // 속한 학기의 ID

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }
}
