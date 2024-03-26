package com.example.allclear.timetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.MyApplication;
import com.example.allclear.auth.LoginActivity;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableListRequestDto;
import com.example.allclear.data.response.TimeTableListResponseDto;
import com.example.allclear.data.service.TimeTableListRequestService;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTimeTableActivity extends AppCompatActivity {
    private RecyclerView semestersRecyclerView;
    private Long userId;
    private String accessToken;
    private String refreshToken;
    TimeTableListRequestDto timeTableListRequestDto;
    TimeTableListRequestService timeTableListRequestService;
    PreferenceUtil preferenceUtil;


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
        semestersRecyclerView.setLayoutManager(new LinearLayoutManager(this));// LayoutManager 설정
        timeTableListRequestService = ServicePool.timeTableListRequestService; //서비스 초기화
        preferenceUtil = MyApplication.getPreferences();

        //로그인 정보 가져오기
        accessToken = preferenceUtil.getAccessToken("Fail");
        refreshToken = preferenceUtil.getRefreshToken("Fail");
        userId = preferenceUtil.getUserId(-1L);

        //로그인 정보 체크
        if(accessToken.equals("Fail") || refreshToken.equals("Fail") || userId == -1L){
            Toast.makeText(getApplicationContext(),"로그인 정보 만료 다시 로그인해 주세요", Toast.LENGTH_SHORT);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                updateList();

                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "DB")
                        .fallbackToDestructiveMigration()
                        .build();

                SemesterDao semesterDao = db.semesterDao();
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

    void updateList() {
        ServicePool.timeTableListRequestService.GetTimeTable("Bearer " + accessToken, userId)
                .enqueue(new Callback<TimeTableListResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableListResponseDto> call, Response<TimeTableListResponseDto> response) {
                        if(response.body() != null) {
                            updateLocalDB(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeTableListResponseDto> call, Throwable t) {

                    }
                });
    }

    void updateLocalDB(TimeTableListResponseDto timeTableListResponseDto) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "DB")
                .fallbackToDestructiveMigration()
                .build();

        SemesterDao semesterDao = db.semesterDao();
        TimetableDao timetableDao = db.timetableDao();
        ScheduleDao scheduleDao = db.scheduleDao();

        List<TimeTableListResponseDto.TimeTableResponseDto> timeTables = timeTableListResponseDto.getData();
        for (TimeTableListResponseDto.TimeTableResponseDto timetableResponseDto : timeTables) {
            // 학기 추가
            Semester semester = new Semester();
            semester.setName(timetableResponseDto.getTableName());
            long semesterId = semesterDao.insert(semester);  // 삽입된 row ID를 저장

            // 시간표 추가
            Timetable timetable = new Timetable();
            timetable.setName(timetableResponseDto.getTableName());
            timetable.setSemesterId(semesterId);  // 삽입된 학기의 ID를 시간표에 설정
            long timetableId = timetableDao.insert(timetable);  // 삽입된 row ID를 저장

            List<TimeTableListResponseDto.TimeTableResponseDto.TimeTableSubjectResponseDto> subjects = timetableResponseDto.getTimetableSubjectResponseDtoList();
            for (TimeTableListResponseDto.TimeTableResponseDto.TimeTableSubjectResponseDto subjectResponseDto : subjects) {
                // 스케쥴 추가
                Schedule schedule = new Schedule();

                // 스케쥴 정보 설정...
                schedule.setTimetableId(timetableId);  // 삽입된 시간표의 ID를 스케쥴에 설정
                scheduleDao.insert(schedule);
            }
        }
    }

}
