package com.example.allclear.timetable.maketimetable.selfadd.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.SelfAddAdapter;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableTwoRequestDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.databinding.ActivitySelfAddPersonalOneBinding;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.timetable.maketimetable.selfadd.subject.SelfAddSubjectOneActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelfAddPersonalOneActivity extends AppCompatActivity {

    private ActivitySelfAddPersonalOneBinding binding;
    private ArrayList<Schedule> scheduleDataList = new ArrayList();

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;

    String selectedYear;
    String selectedSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddPersonalOneBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getSemesterData();
        initSelfAddClickListener();
        initNextClickListener();
        initBackClickListener();

    }


    private void getSemesterData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectedYear = intent.getStringExtra("selectedYear");
            selectedSemester = intent.getStringExtra("selectedSemester");
        }
    }


    private void initSelfAddClickListener() {
        binding.btnPlusSelfAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfAddPersonalOneActivity.this, SelfAddPersonalTwoActivity.class);
                intent.putExtra("schedulelist", scheduleDataList);
                startActivityForResult(intent, 100);
            }
        });
    }

    private void initNextClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferenceUtil = MyApplication.getPreferences();
                userId = preferenceUtil.getUserId(-1L);
                accessToken = preferenceUtil.getAccessToken("FAIL");

                postStepTwoToServer(accessToken, userId, scheduleDataList);
            }
        });
    }

    private void initBackClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void postStepTwoToServer(String accessToken, Long userId, List<Schedule> scheduleDataList) {
        // 추가한 과목 서버로 보내기
        TimeTableTwoRequestDto timeTableTwoRequestDto = makeTimeTableTwoRequestDto(scheduleDataList);

        ServicePool.timeTableService.postStepTwo("Bearer " + accessToken, userId, timeTableTwoRequestDto)
                .enqueue(new Callback<TimeTableResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableResponseDto> call, Response<TimeTableResponseDto> response) {

                        if (response.isSuccessful()) {
                            Intent intent = new Intent(SelfAddPersonalOneActivity.this, SelfAddSubjectOneActivity.class);
                            intent.putExtra("selectedYear", selectedYear);
                            intent.putExtra("selectedSemester", selectedSemester);
                            startActivity(intent);
                        } else {
                            try {
                                String errorBody = response.errorBody().string();
                                Toast.makeText(SelfAddPersonalOneActivity.this, errorBody, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeTableResponseDto> call, Throwable t) {
                        Toast.makeText(SelfAddPersonalOneActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private TimeTableTwoRequestDto makeTimeTableTwoRequestDto(List<Schedule> scheduleDataList) {
        List<TimeTableTwoRequestDto.timetableSubjectRequestDtoList> timetableSubjectRequestDtoList = new ArrayList<>();
        // 체크박스 선택한 것만 서버로 보내는 로직 필요

        for (Schedule schedule : scheduleDataList) {
            TimeTableTwoRequestDto.timetableSubjectRequestDtoList timetableSubjectRequestDto = new TimeTableTwoRequestDto.timetableSubjectRequestDtoList();
            timetableSubjectRequestDto.setSubjectName(schedule.getSubjectName());
            timetableSubjectRequestDto.setSubjectId(schedule.getSubjectId());

            TimeTableTwoRequestDto.timetableSubjectRequestDtoList.ClassInfoRequestDtoList classInfo = new TimeTableTwoRequestDto.timetableSubjectRequestDtoList.ClassInfoRequestDtoList();
            classInfo.setProfessor(schedule.getProfessor());
            classInfo.setClassDay(getClassDay(schedule.getClassDay()));

            classInfo.setStartTime(schedule.getStartTime());
            classInfo.setEndTime(schedule.getEndTime());

            classInfo.setClassRoom(schedule.getClassRoom());

            timetableSubjectRequestDto.setClassInfoRequestDtoList(Collections.singletonList(classInfo));
            timetableSubjectRequestDtoList.add(timetableSubjectRequestDto);
        }

        TimeTableTwoRequestDto requestDto = new TimeTableTwoRequestDto();
        requestDto.setTimetableSubjectRequestDtoList(timetableSubjectRequestDtoList);

        return requestDto;
    }


    private String getClassDay(int day) {
        if (day == 0)
            return getString(R.string.monday);
        else if (day == 1)
            return getString(R.string.tuesday);
        else if (day == 2)
            return getString(R.string.wednesday);
        else if (day == 3)
            return getString(R.string.thursday);
        else if (day == 4)
            return getString(R.string.friday);
        else
            return getString(R.string.saturday);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Schedule schedule = (Schedule) data.getSerializableExtra("schedule");
            scheduleDataList.add(schedule);
            displayDataInRecyclerView(scheduleDataList);
        }
    }

    private void displayDataInRecyclerView(ArrayList<Schedule> ScheduleList) {
        RecyclerView recyclerView = binding.rvSelfAddSchedule;
        SelfAddAdapter adapter = new SelfAddAdapter(ScheduleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}