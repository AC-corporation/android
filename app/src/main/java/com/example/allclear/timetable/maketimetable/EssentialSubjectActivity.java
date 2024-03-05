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
import com.example.allclear.data.request.TimeTableEssentialRequestDto;
import com.example.allclear.data.response.TimeTableGetResponseDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.databinding.ActivityEssentialSubjectBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EssentialSubjectActivity extends AppCompatActivity {
    private ActivityEssentialSubjectBinding binding;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityEssentialSubjectBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getUserData();
        initNextClickListener();
        initBackClickListener();
        getEssentialSubject();

    }

    private void getUserData() {
        preferenceUtil = MyApplication.getPreferences();
        userId = preferenceUtil.getUserId(-1L);
        accessToken = preferenceUtil.getAccessToken("FAIL");
    }

    private void initNextClickListener() {
        binding.btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStepSevenToServer(userId);
            }
        });
    }

    private void postStepSevenToServer(long userId) {
        TimeTableEssentialRequestDto timeTableEssentialRequestDto = userIdSelected();

        ServicePool.timeTableService.postStepSeven("Bearer " + accessToken, userId, timeTableEssentialRequestDto)
                .enqueue(new Callback<TimeTableResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableResponseDto> call, @NonNull Response<TimeTableResponseDto> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(EssentialSubjectActivity.this, SaveTimeTableActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeTableResponseDto> call, Throwable t) {
                        Toast.makeText(EssentialSubjectActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private TimeTableEssentialRequestDto userIdSelected() {
        List<Long> selectedIds = MakeTimeTableAdapter.getSelectedSubjectIds();
        TimeTableEssentialRequestDto timeTableEssentialRequestDto = new TimeTableEssentialRequestDto();
        timeTableEssentialRequestDto.setTimetableGeneratorSubjectIdList(selectedIds);
        return timeTableEssentialRequestDto;
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
        ServicePool.timeTableService.getStepSeven("Bearer " + accessToken, userId)
                .enqueue(new Callback<TimeTableGetResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableGetResponseDto> call, @NonNull Response<TimeTableGetResponseDto> response) {
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