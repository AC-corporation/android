package com.example.allclear.timetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.databinding.ActivityListTimeTableBinding;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.schedule.Semester;
import com.example.allclear.schedule.Timetable;
import com.example.allclear.schedule.data.AppDatabase;
import com.example.allclear.schedule.data.ScheduleDao;
import com.example.allclear.schedule.data.SemesterAdapter;
import com.example.allclear.schedule.data.SemesterDao;
import com.example.allclear.schedule.data.TimetableDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListTimeTableActivity extends AppCompatActivity {
    RecyclerView semestersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.allclear.databinding.ActivityListTimeTableBinding binding = ActivityListTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.listTimetableBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        semestersRecyclerView = binding.recyclerView; // RecyclerView 초기화
        semestersRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // LayoutManager 설정


        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "DB")
                        .fallbackToDestructiveMigration()  // 이 줄을 추가
                        .build();

                SemesterDao semesterDao = db.semesterDao();
                TimetableDao timetableDao = db.timetableDao();
                ScheduleDao scheduleDao = db.scheduleDao();

                // 학기 추가
                Semester semester = new Semester();
                semester.name = "1학년 1학기";
                long semesterId = semesterDao.insert(semester);  // 삽입된 row ID를 저장

                // 시간표 추가
                Timetable timetable = new Timetable();
                timetable.name = "시간표1";
                timetable.semesterId = semesterId;  // 삽입된 학기의 ID를 시간표에 설정
                long timetableId = timetableDao.insert(timetable);  // 삽입된 row ID를 저장

                // 스케쥴 추가
                Schedule schedule = new Schedule();

                // 스케쥴 정보 설정...
                schedule.timetableId = timetableId;  // 삽입된 시간표의 ID를 스케쥴에 설정
                scheduleDao.insert(schedule);

                List<Semester> semesters = semesterDao.getAllSemesters();  // 모든 학기 가져오기

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // UI 업데이트
                        SemesterAdapter semesterAdapter = new SemesterAdapter(ListTimeTableActivity.this, semesters, db);
                        semestersRecyclerView.setAdapter(semesterAdapter);
                    }
                });
            }

        });
    }
}
