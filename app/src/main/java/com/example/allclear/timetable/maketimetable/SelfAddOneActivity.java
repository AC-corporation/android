package com.example.allclear.timetable.maketimetable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.R;
import com.example.allclear.SelfAddAdapter;

import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableTwoRequestDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.databinding.ActivitySelfAddOneBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelfAddOneActivity extends AppCompatActivity {

    private ActivitySelfAddOneBinding binding;
    private ArrayList<Schedule> scheduleDataList = new ArrayList<Schedule>();

    long userId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddOneBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        initSelfAddClickListener();
        initNextClickListener();
        initBackClickListener();

    }

    private void initSelfAddClickListener() {
        binding.btnPlusSelfAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfAddOneActivity.this, SelfAddTwoActivity.class);
                intent.putExtra("schedulelist", scheduleDataList);
                startActivityForResult(intent, 100);
            }
        });
    }

    private void initNextClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStepTwoToServer(userId, scheduleDataList);
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

    private void postStepTwoToServer(long userId, ArrayList<Schedule> scheduleDataList) {
        List<TimeTableTwoRequestDto> timeTableTwoRequestDtoList = makeTimeTableTwoRequestList(scheduleDataList);

        ServicePool.timeTableService.postStepTwo(userId, timeTableTwoRequestDtoList)
                .enqueue(new Callback<TimeTableResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableResponseDto> call, Response<TimeTableResponseDto> response) {
                        Intent intent = new Intent(SelfAddOneActivity.this, SelectMajorBaseActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<TimeTableResponseDto> call, Throwable t) {
                        Toast.makeText(SelfAddOneActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private List<TimeTableTwoRequestDto> makeTimeTableTwoRequestList(ArrayList<Schedule> scheduleDataList) {
        List<TimeTableTwoRequestDto> timeTableTwoRequestDtoList = new ArrayList<>();

        for (Schedule schedule : scheduleDataList) {
            TimeTableTwoRequestDto timeTableTwoRequestDto = new TimeTableTwoRequestDto();
            timeTableTwoRequestDto.setSubjectName(schedule.getSubjectName());
            timeTableTwoRequestDto.setSubjectId(schedule.getSubjectId());

            TimeTableTwoRequestDto.ClassInfoRequestDtoList classInfo = new TimeTableTwoRequestDto.ClassInfoRequestDtoList();
            classInfo.setProfessor(schedule.getProfessor());
            classInfo.setClassDay(getClassDay(schedule.getClassDay()));
            classInfo.setStartTime(schedule.getStartTime());
            classInfo.setEndTime(schedule.getEndTime());
            classInfo.setClassRoom(schedule.getClassRoom());

            timeTableTwoRequestDto.setClassInfoRequestDtoList(Collections.singletonList(classInfo));
            timeTableTwoRequestDtoList.add(timeTableTwoRequestDto);
        }

        return timeTableTwoRequestDtoList;
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