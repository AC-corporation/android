package com.example.allclear.timetable.maketimetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.allclear.timetable.TimeTableFragment;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

//    Schedule schedule = new Schedule();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySaveTimeTableBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getUserData();
        initBackBtnClickListener();
        getTimeTableGenerator();
        initSaveBtnClickListener();
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

                        // 시간표 충돌 확인
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
        // 서버 통신한 데이터를 ScheduleList에 추가
        scheduleDataList.add(newSchedule);
        scheduleEntityList = ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
        // 토요일, 일요일 유무에 따라 day 변경
        boolean hasSaturday = false;
        boolean hasSunday = false;
        for (Schedule schedule : scheduleDataList) {
            if (schedule.getClassDay() == 5) {
                hasSaturday = true;
            } else if (schedule.getClassDay() == 6) {
                hasSunday = true;
            }
        }

        ArrayList<String> updatedStringDayList = new ArrayList<>();
        updatedStringDayList.add(getString(R.string.Mon));
        updatedStringDayList.add(getString(R.string.Tue));
        updatedStringDayList.add(getString(R.string.Wen));
        updatedStringDayList.add(getString(R.string.Thu));
        updatedStringDayList.add(getString(R.string.Fri));

        if (hasSaturday) {
            updatedStringDayList.add(getString(R.string.Sat));
        }
        if (hasSunday) {
            updatedStringDayList.add(getString(R.string.Sun));
        }

        // String 배열로 변환
        stringDay = updatedStringDayList.toArray(new String[0]);
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
        // 이거 set하는 로직 필요
        TimeTableSaveRequestDto timeTableSaveRequestDto = new TimeTableSaveRequestDto();

        ServicePool.timeTableService.postSaveTimTable("Bearer " + accessToken, userId, timeTableSaveRequestDto)
                .enqueue(new Callback<TimeTableSaveResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableSaveResponseDto> call, @NonNull Response<TimeTableSaveResponseDto> response) {
                        if (response.isSuccessful()) {
                            //갱신된 ScheduleList를 TimeTableFragment로 전달
                            Intent intent = new Intent(SaveTimeTableActivity.this, TimeTableFragment.class);
                            intent.putExtra("scheduleList", scheduleDataList);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TimeTableSaveResponseDto> call, Throwable t) {
                        Toast.makeText(SaveTimeTableActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}