package com.example.allclear.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.allclear.R;
import com.example.allclear.schedule.data.AppDatabase;
import com.example.allclear.schedule.data.ScheduleDao;
import com.example.allclear.schedule.data.SemesterDao;
import com.example.allclear.schedule.data.TimetableDao;

public class TimeTableControlExample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_control_example);

        manageTimetable();

    }

    /*
    학기테이블: 각 레코드가 하나의 학기를 나타냅니다. PK는 학기 아이디입니다.
    시간표테이블: 각 레코드가 하나의 시간표를 나타냅니다. PK는 시간표 아이디, FK는 학기 아이디입니다.
    스케쥴테이블: 각 레코드가 하나의 스케쥴을 나타냅니다. PK는 스케쥴 아이디, FK는 시간표 아이디입니다.
    */

    void manageTimetable(){
        //시간표 관리 예제
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "DB").build();

        SemesterDao semesterDao = db.semesterDao();
        TimetableDao timetableDao = db.timetableDao();
        ScheduleDao scheduleDao = db.scheduleDao();

        // 학기 추가
        Semester semester = new Semester();
        semester.name = "1학년 1학기";
        semesterDao.insert(semester);

        // 시간표 추가
        Timetable timetable = new Timetable();
        timetable.name = "시간표1";
        timetable.semesterId = semester.id;  // 학기 ID 설정
        timetableDao.insert(timetable);

        // 스케쥴 추가
        Schedule schedule = new Schedule();

        // 스케쥴 정보 설정...
        schedule.timetableId = timetable.id;  // 시간표 ID 설정
        scheduleDao.insert(schedule);
    }
}