package com.example.allclear.timetable.maketimetable.generalelective;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.R;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.response.TimeTableGetResponseDto;
import com.example.allclear.databinding.ActivitySelectGeneralElectiveBinding;
import com.example.allclear.timetable.maketimetable.EssentialSubjectActivity;
import com.example.allclear.timetable.maketimetable.MakeTimeTableAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectGeneralElectiveActivity extends AppCompatActivity {
    private ActivitySelectGeneralElectiveBinding binding;

    long userId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelectGeneralElectiveBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        initNextClickListener();
        initBackClickListener();
        getGeneralElectiveList();

    }

    private void initNextClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectGeneralElectiveActivity.this, EssentialSubjectActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initBackClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getGeneralElectiveList() {
        ServicePool.timeTableService.getStepSix(userId)
                .enqueue(new Callback<TimeTableGetResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableGetResponseDto> call, Response<TimeTableGetResponseDto> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            TimeTableGetResponseDto responseBody = response.body();
                            TimeTableGetResponseDto.TimeTableResponseData data = responseBody.getData();

                            List<TimeTableGetResponseDto.RequirementComponentResponseDto> requirementComponents = data.getRequirementComponentResponseDtoList();
                            List<TimeTableGetResponseDto.SubjectResponseDto> subjectResponseDtoList = data.getSubjectResponseDtoList();

                            initSubjectAdapter(subjectResponseDtoList);
                            initArgumentAdapter(requirementComponents);

                        }

                    }

                    @Override
                    public void onFailure(Call<TimeTableGetResponseDto> call, Throwable t) {
                        Toast.makeText(SelectGeneralElectiveActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initSubjectAdapter(List<TimeTableGetResponseDto.SubjectResponseDto> subjectResponseDtoList) {
        MakeTimeTableAdapter adapter = new MakeTimeTableAdapter(subjectResponseDtoList);
        binding.rvSelectGeneralElective.setAdapter(adapter);
    }

    private void initArgumentAdapter(List<TimeTableGetResponseDto.RequirementComponentResponseDto> requirementComponents) {
        SelectGeneralElectiveAdapter adapter = new SelectGeneralElectiveAdapter(requirementComponents);
        binding.rvSelectGeneralElective.setAdapter(adapter);
    }

}