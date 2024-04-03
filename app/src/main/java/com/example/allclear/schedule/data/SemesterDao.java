package com.example.allclear.schedule.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.allclear.schedule.Semester;

import java.util.List;
import java.util.Locale;

@Dao
public interface SemesterDao {

    @Insert
    Long insert(Semester semester);

    @Query("SELECT * FROM Semester")
    List<Semester> getAllSemesters();

    @Query("DELETE FROM semester")
    void deleteAll();

    @Query("SELECT * FROM semester WHERE name = :name LIMIT 1")
    Semester findByName(String name);


    @Update
    void update(Semester semester);
}
