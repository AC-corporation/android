package com.example.allclear.schedule.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.allclear.schedule.Schedule;
import com.example.allclear.schedule.Semester;
import com.example.allclear.schedule.Timetable;

@Database(entities = {Semester.class, Timetable.class, Schedule.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SemesterDao semesterDao();
    public abstract TimetableDao timetableDao();
    public abstract ScheduleDao scheduleDao();
}
