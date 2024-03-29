package com.example.allclear.schedule.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.allclear.schedule.Timetable;

import java.util.List;

@Dao
public interface TimetableDao {
    @Query("SELECT * FROM Timetable WHERE semesterId = :semesterId")
    List<Timetable> getAllBySemester(int semesterId);

    @Insert
    Long insert(Timetable timetable);

    @Query("SELECT * FROM timetable WHERE semesterId = :semesterId")
    List<Timetable> findTimetablesBySemesterId(Long semesterId);

    // 필요한 메서드 추가...
}
