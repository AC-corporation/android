package com.example.allclear.timetable.maketimetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.allclear.MainPageActivity;
import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableSaveRequestDto;
import com.example.allclear.data.response.TimeTableSaveResponseDto;
import com.example.allclear.data.response.TimeTableStepEightResponseDto;
import com.example.allclear.databinding.ActivitySaveTimeTableBinding;
import com.example.allclear.schedule.ChangeSchedule;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.schedule.Semester;
import com.example.allclear.schedule.Timetable;
import com.example.allclear.schedule.data.AppDatabase;
import com.example.allclear.schedule.data.ScheduleDao;
import com.example.allclear.schedule.data.SemesterDao;
import com.example.allclear.schedule.data.TimetableDao;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveTimeTableActivity extends AppCompatActivity {
    private ActivitySaveTimeTableBinding binding;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;

    private String[] stringDay;
    private ArrayList<Schedule> scheduleDataList = new ArrayList<Schedule>();
    private ArrayList<ScheduleEntity> scheduleEntityList = new ArrayList<>();

    String subtext;
    String professor;
    String startTime;
    String endTime;
    String place;
    long timetableId;

    int day;
    int size;

    String selectedYear;
    String selectedSemester;
    String timeTableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySaveTimeTableBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getUserData();
        getSemesterData();
        setTimeTableName();
        initBackBtnClickListener();
        getTimeTableGenerator();
        initSaveBtnClickListener();
        saveTimeTableToData();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.table.initTable(stringDay);
        binding.table.updateSchedules(scheduleEntityList);
    }

    private void getUserData() {
        preferenceUtil = MyApplication.getPreferences();
        userId = preferenceUtil.getUserId(-1L);
        accessToken = preferenceUtil.getAccessToken("FAIL");
    }

    private void getSemesterData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectedYear = intent.getStringExtra("selectedYear");
            selectedSemester = intent.getStringExtra("selectedSemester");
            timeTableName = intent.getStringExtra("timeTableName");
        }
    }

    private void setTimeTableName() {
        binding.tvSubTitle.setText(timeTableName);
    }

    private void initBackBtnClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getTimeTableGenerator() {
        ServicePool.timeTableService.getStepEight("Bearer " + accessToken, userId).enqueue(new Callback<TimeTableStepEightResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<TimeTableStepEightResponseDto> call, @NonNull Response<TimeTableStepEightResponseDto> response) {
                if (response.isSuccessful()) {
                    TimeTableStepEightResponseDto responseBody = response.body();
                    assert responseBody != null;
                    List<TimeTableStepEightResponseDto.TimeTableData.TimeTable> timeTable = responseBody.getTimeTableData().getTimetableResponseDto();
                    setTimeTable(timeTable);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TimeTableStepEightResponseDto> call, @NonNull Throwable t) {
                Toast.makeText(SaveTimeTableActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTimeTable(List<TimeTableStepEightResponseDto.TimeTableData.TimeTable> timeTable) {
        if (timeTable == null) {
            Toast.makeText(this, "생성된 추천 시간표가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
        } else {
            for (TimeTableStepEightResponseDto.TimeTableData.TimeTable entry : timeTable) {
                List<TimeTableStepEightResponseDto.TimeTableData.TimeTable.timetableSubjectResponseDtoList> subjects = entry.getTimetableSubjectResponseDtoList();

                timetableId = entry.getTimetableId();

                for (TimeTableStepEightResponseDto.TimeTableData.TimeTable.timetableSubjectResponseDtoList subject : subjects) {
                    List<TimeTableStepEightResponseDto.TimeTableData.TimeTable.timetableSubjectResponseDtoList.ClassInfo> classInfoList = subject.getClassInfoResponseDtoList();

                    for (TimeTableStepEightResponseDto.TimeTableData.TimeTable.timetableSubjectResponseDtoList.ClassInfo classInfo : classInfoList) {
                        subtext = subject.getSubjectName();
                        professor = classInfo.getProfessor();
                        day = makeDayToInt(classInfo.getClassDay());
                        startTime = classInfo.getStartTime();
                        endTime = classInfo.getEndTime();
                        place = classInfo.getClassRoom();

                        checkConflict();
                    }
                }
            }
        }
    }

    private int makeDayToInt(String day) {
        if (Objects.equals(day, getString(R.string.monday)))
            return 0;
        else if (Objects.equals(day, getString(R.string.tuesday)))
            return 1;
        else if (Objects.equals(day, getString(R.string.wednesday)))
            return 2;
        else if (Objects.equals(day, getString(R.string.thursday)))
            return 3;
        else if (Objects.equals(day, getString(R.string.friday)))
            return 4;
        else
            return 5;
    }

    private void checkConflict() {
        if (size == 0) {
            addSchedule();
        } else {
            for (int i = 0; i < size; i++) {
                if (day == scheduleDataList.get(i).getClassDay()) {
                    int addStart = timeToMinutes(startTime);
                    int addEnd = timeToMinutes(endTime);
                    int listStart = timeToMinutes(scheduleDataList.get(i).getStartTime());
                    int listEnd = timeToMinutes(scheduleDataList.get(i).getEndTime());
                    if ((addStart < listEnd) && (addEnd > listStart)) {
                        Toast.makeText(SaveTimeTableActivity.this, "시간이 겹치는 일정이 존재합니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            addSchedule();
        }
    }

    private static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    void addSchedule() {
        Schedule schedule = new Schedule();

        schedule.setSubjectId(timetableId);
        schedule.setSubjectName(subtext);
        schedule.setProfessor(professor);
        schedule.setClassDay(day);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setClassRoom(place);

        showTimeTable(schedule);
    }

    private void showTimeTable(Schedule newSchedule) {
        scheduleDataList.add(newSchedule);
        scheduleEntityList = ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);

        // 토요일, 일요일 유무에 따라 day 변경
        stringDay = new String[]{getString(R.string.Mon), getString(R.string.Tue), getString(R.string.Wen), getString(R.string.Thu), getString(R.string.Fri)};
        int size = scheduleDataList.size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (5 == scheduleDataList.get(i).getClassDay()) {
                    stringDay = new String[]{getString(R.string.Mon), getString(R.string.Tue), getString(R.string.Wen), getString(R.string.Thu), getString(R.string.Fri), getString(R.string.Sat)};
                }
                if (6 == scheduleDataList.get(i).getClassDay()) {
                    stringDay = new String[]{getString(R.string.Mon), getString(R.string.Tue), getString(R.string.Wen), getString(R.string.Thu), getString(R.string.Fri), getString(R.string.Sat), getString(R.string.Sun)};
                }
            }
        }
    }

    private void initSaveBtnClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSaveTimeTable(accessToken, userId);
            }
        });
    }

    private void postSaveTimeTable(String accessToken, Long userId) {
        TimeTableSaveRequestDto timeTableSaveRequestDto = new TimeTableSaveRequestDto();

        timeTableSaveRequestDto.setTimetableGeneratorTimetableId(timetableId);

        ServicePool.timeTableService.postSaveTimTable("Bearer " + accessToken, userId, timeTableSaveRequestDto)
                .enqueue(new Callback<TimeTableSaveResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableSaveResponseDto> call, @NonNull Response<TimeTableSaveResponseDto> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SaveTimeTableActivity.this, R.string.timetable_save, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SaveTimeTableActivity.this, MainPageActivity.class);
                            intent.putExtra("scheduleList", scheduleDataList);
                            setResult(RESULT_OK, intent);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TimeTableSaveResponseDto> call, Throwable t) {
                        Toast.makeText(SaveTimeTableActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveTimeTableToData() {

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
                semester.name = getString(R.string.selected_year_semester, selectedYear, selectedSemester);
                long semesterId = semesterDao.insert(semester);  // 삽입된 row ID를 저장

                // 시간표 추가
                Timetable timetable = new Timetable();
                timetable.name = timeTableName;
                timetable.semesterId = semesterId;  // 삽입된 학기의 ID를 시간표에 설정
                long timetableId = timetableDao.insert(timetable);  // 삽입된 row ID를 저장

                // 스케쥴 추가
                Schedule schedule = new Schedule();

                // 스케쥴 정보 설정
                schedule.timetableId = timetableId;  // 삽입된 시간표의 ID를 스케쥴에 설정
                scheduleDao.insert(schedule);

            }

        });
    }

}