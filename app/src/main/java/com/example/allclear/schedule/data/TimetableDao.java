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

    // 서버에서의 TimeTable ID를 timetableId를 기준으로 조회하는 메서드 추가
    @Query("SELECT serverId FROM TimeTable WHERE id = :timetableId")
    Long getServerIdByTimetableId(Long timetableId);

}
