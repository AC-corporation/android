package com.example.allclear.timetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.AppExecutors;
import com.example.allclear.MyApplication;
import com.example.allclear.auth.LoginActivity;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableListRequestDto;
import com.example.allclear.data.response.TimetableListResponseDto;
import com.example.allclear.data.service.TimeTableListRequestService;
import com.example.allclear.databinding.ActivityListTimeTableBinding;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.schedule.Semester;
import com.example.allclear.schedule.TimeTable;
import com.example.allclear.schedule.data.AppDatabase;
import com.example.allclear.schedule.data.ScheduleDao;
import com.example.allclear.schedule.data.SemesterAdapter;
import com.example.allclear.schedule.data.SemesterDao;
import com.example.allclear.schedule.data.TimetableDao;

import java.util.ArrayList;
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
        if(accessToken.equals("Fail") || refreshToken.equals("Fail") || userId == -1L) {
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
            }
        });
    }

    void updateList() {
        ServicePool.timeTableListRequestService.GetTimeTable("Bearer " + accessToken, userId)
                .enqueue(new Callback<TimetableListResponseDto>() {
                    @Override
                    public void onResponse(Call<TimetableListResponseDto> call, Response<TimetableListResponseDto> response) {
                        if(response.isSuccessful()) {
                            updateLocalDB(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<TimetableListResponseDto> call, Throwable t) {

                    }
                });
    }

    void updateLocalDB(TimetableListResponseDto timeTableListResponseDto) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

            SemesterDao semesterDao = db.semesterDao();
            TimetableDao timetableDao = db.timetableDao();
            ScheduleDao scheduleDao = db.scheduleDao();

            scheduleDao.deleteAll();
            timetableDao.deleteAll();
            semesterDao.deleteAll();

            TimetableListResponseDto.Data data = timeTableListResponseDto.getData();
            List<TimetableListResponseDto.Data.TimetableResponseDto> timeTables =  data.getTimetableResponseDtoList();
            for (TimetableListResponseDto.Data.TimetableResponseDto timetableResponseDto : timeTables) {

                // 학기 이름 생성
                String semesterName = timetableResponseDto.getYear() + "년 " + timetableResponseDto.getSemester() + "학기";

                // 데이터베이스에서 동일한 이름을 가진 학기 검색
                Semester existingSemester = semesterDao.findByName(semesterName);
                long semesterId;

                if (existingSemester == null) {
                    // 동일한 이름의 학기가 존재하지 않는 경우, 새 학기 추가
                    Semester newSemester = new Semester();
                    newSemester.setName(semesterName);
                    // 새 학기 삽입 후, 생성된 학기 ID 저장
                    semesterId = semesterDao.insert(newSemester);
                } else {
                    // 이미 존재하는 학기의 경우, 해당 학기 ID 재사용
                    semesterId = existingSemester.getId();
                }

                // 시간표 추가
                TimeTable timetable = new TimeTable();
                timetable.setName(timetableResponseDto.getTableName());
                timetable.setSemesterId(semesterId);  // 삽입된 학기의 ID를 시간표에 설정
                timetable.setServerId(timetableResponseDto.getTimetableId());
                long timetableId = timetableDao.insert(timetable);  // 삽입된 row ID를 저장

                List<TimetableListResponseDto.Data.TimetableResponseDto.timetableSubjectResponseDto> subjects = timetableResponseDto.getTimetableSubjectResponseDtoList();
                for (TimetableListResponseDto.Data.TimetableResponseDto.timetableSubjectResponseDto subjectResponseDto : subjects) {
                    // parseSchedules 함수를 이용해 스케쥴 리스트를 파싱
                    List<Schedule> schedules = parseSchedules(subjectResponseDto);

                    // 파싱된 스케쥴 객체들을 데이터베이스에 저장
                    for (Schedule schedule : schedules) {
                        schedule.setTimetableId(timetableId); // 삽입된 시간표의 ID를 스케쥴에 설정
                        scheduleDao.insert(schedule);
                    }
                }
            }

            AppExecutors.getInstance().diskIO().execute(() -> {
                        List<Semester> semesters = semesterDao.getAllSemesters();  // 모든 학기 가져오기

                        for(Semester semester : semesters) {
                            Log.d("TAG", "info " + semester.name);

                        }
                        // UI 스레드로 전환하여 UI 업데이트
                        runOnUiThread(() -> {
                            // UI 업데이트 코드
                            SemesterAdapter semesterAdapter = new SemesterAdapter(ListTimeTableActivity.this, semesters, db);
                            semestersRecyclerView.setAdapter(semesterAdapter);
                        });
            });
        });
    }


    public List<Schedule> parseSchedules(TimetableListResponseDto.Data.TimetableResponseDto.timetableSubjectResponseDto dto) {
        List<Schedule> schedules = new ArrayList<>();

        // dto에서 classInfoResponseDtoList를 가져옵니다.
        List<TimetableListResponseDto.Data.TimetableResponseDto.timetableSubjectResponseDto.classInfoResponseDto> classInfoResponseDtoList = dto.getClassInfoResponseDtoList();

        // classInfoResponseDtoList의 각 요소에 대해 반복 처리합니다.
        for (TimetableListResponseDto.Data.TimetableResponseDto.timetableSubjectResponseDto.classInfoResponseDto classInfo : classInfoResponseDtoList) {
            Schedule schedule = new Schedule();
            schedule.setSubjectId(dto.getSubjectId());
            schedule.setSubjectName(dto.getSubjectName());
            schedule.setProfessor(classInfo.getProfessor());
            schedule.setClassDay(dayOfWeekToInt(classInfo.getClassDay()));
            schedule.setStartTime(classInfo.getStartTime());
            schedule.setEndTime(classInfo.getEndTime());
            schedule.setClassRoom(classInfo.getClassRoom());
            schedule.setTimetableId(dto.getTimetableSubjectId());

            // 설정된 schedule 객체를 리스트에 추가합니다.
            schedules.add(schedule);
        }

        return schedules;
    }

    // 요일을 숫자로 변환하는 메소드
    private int dayOfWeekToInt(String day) {
        switch (day) {
            case "월":
                return 1;
            case "화":
                return 2;
            case "수":
                return 3;
            case "목":
                return 4;
            case "금":
                return 5;
            case "토":
                return 6;
            case "일":
                return 7;
            default:
                throw new IllegalArgumentException("Invalid day of week: " + day);

        }
    }
}
