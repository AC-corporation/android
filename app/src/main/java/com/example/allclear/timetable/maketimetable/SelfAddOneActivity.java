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
import com.example.allclear.data.request.TimeTableOneRequestDto;
import com.example.allclear.data.response.TimeTableOneResponseDto;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.databinding.ActivitySelfAddOneBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelfAddOneActivity extends AppCompatActivity {

    private ActivitySelfAddOneBinding binding;
    private ArrayList<Schedule> scheduleDataList = new ArrayList<Schedule>();

    long userId = 1;
    TimeTableOneRequestDto timeTableOneRequestDto;

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
        binding.ivPlusSelfAdd.setOnClickListener(new View.OnClickListener() {
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
                postStepOneToServer(userId, timeTableOneRequestDto);
                Intent intent = new Intent(SelfAddOneActivity.this, SelectMajorBaseActivity.class);
                startActivity(intent);
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

    private void postStepOneToServer(long userId, TimeTableOneRequestDto timeTableOneRequestDto) {
        ServicePool.timeTableService.postStepOne(userId, timeTableOneRequestDto)
                .enqueue(new Callback<TimeTableOneResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableOneResponseDto> call, Response<TimeTableOneResponseDto> response) {
                        // 선택한 학년, 학기 서버로 보내기
                        Toast.makeText(SelfAddOneActivity.this, R.string.server_success, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<TimeTableOneResponseDto> call, Throwable t) {
                        Toast.makeText(SelfAddOneActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
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

    private void displayDataInRecyclerView(ArrayList<Schedule> Schedulelist) {
        RecyclerView recyclerView = binding.rvSelfAddSchedule;
        SelfAddAdapter adapter = new SelfAddAdapter(scheduleDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}