package com.example.allclear.timetable.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.R;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.response.TimeTableGetResponseDto;
import com.example.allclear.databinding.ActivityEssentialSubjectBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EssentialSubjectActivity extends AppCompatActivity {
    private ActivityEssentialSubjectBinding binding;

    private long userId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityEssentialSubjectBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        initNextClickListener();
        initBackClickListener();
        getEssentialSubject();

    }

    private void initNextClickListener() {
        binding.btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EssentialSubjectActivity.this, SaveTimeTableActivity.class);
                startActivity(intent);
            }
        });
    }

    private void postStepSevenToServer(){

    }

    private void initBackClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getEssentialSubject() {
        ServicePool.timeTableService.getStepSeven(userId)
                .enqueue(new Callback<TimeTableGetResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableGetResponseDto> call, Response<TimeTableGetResponseDto> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            TimeTableGetResponseDto responseBody = response.body();
                            TimeTableGetResponseDto.TimeTableResponseData data = responseBody.getData();

                            List<TimeTableGetResponseDto.SubjectResponseDto> subjectResponseDtoList = data.getSubjectResponseDtoList();

                            initAdapter(subjectResponseDtoList);
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeTableGetResponseDto> call, Throwable t) {
                        Toast.makeText(EssentialSubjectActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initAdapter(List<TimeTableGetResponseDto.SubjectResponseDto> subjectResponseDtoList) {
        MakeTimeTableAdapter adapter = new MakeTimeTableAdapter(subjectResponseDtoList);
        binding.rvEssentialSubject.setAdapter(adapter);
    }

}