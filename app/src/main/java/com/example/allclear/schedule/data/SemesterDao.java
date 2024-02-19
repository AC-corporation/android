package com.example.allclear.schedule.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.allclear.schedule.Semester;

import java.util.List;

@Dao
public interface SemesterDao {
    @Query("SELECT * FROM Semester")
    List<Semester> getAll();

    @Insert
    void insert(Semester semester);

    // 필요한 메서드 추가...
}

