package com.example.allclear.schedule.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.allclear.schedule.Schedule;
import com.example.allclear.schedule.TimeTable;

import java.util.List;

@Dao
public interface TimetableDao {
    @Query("SELECT * FROM TimeTable WHERE semesterId = :semesterId")
    List<TimeTable> getAllBySemester(int semesterId);

    @Insert
    Long insert(TimeTable timetable);

    @Query("SELECT * FROM TimeTable WHERE semesterId = :semesterId")
    List<TimeTable> findTimetablesBySemesterId(Long semesterId);

    @Query("SELECT * FROM Schedule WHERE timetableId = :timetableId")
    List<Schedule> getAllSchedulesByTimeTableId(Long timetableId);

    @Query("DELETE FROM timetable")
    void deleteAll();
    // 필요한 메서드 추가...
}
