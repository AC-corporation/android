package com.example.allclear.schedule;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Room;

@Entity
public class Semester {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;  // 학기 이름 (예: "1학년 1학기")
}
