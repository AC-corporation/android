package com.example.allclear.schedule.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.allclear.schedule.Schedule;

import java.util.List;

@Dao
public interface ScheduleDao {
    @Query("SELECT * FROM Schedule WHERE timetableId = :timetableId")
    List<Schedule> getAllByTimetable(int timetableId);

    @Insert
    void insert(Schedule schedule);

    // 필요한 메서드 추가...
}
