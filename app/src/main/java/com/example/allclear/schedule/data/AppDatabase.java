package com.example.allclear.schedule.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.allclear.schedule.Schedule;
import com.example.allclear.schedule.Semester;
import com.example.allclear.schedule.TimeTable;


@Database(entities = {Semester.class, TimeTable.class, Schedule.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract SemesterDao semesterDao();
    public abstract TimetableDao timetableDao();
    public abstract ScheduleDao scheduleDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "DB")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
