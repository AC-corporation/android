package com.example.allclear.timetable.maketimetable.essential;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableEssentialRequestDto;
import com.example.allclear.data.response.TimeTableEssentialResponseDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.databinding.ActivityEssentialSubjectBinding;
import com.example.allclear.timetable.maketimetable.MakeTimeTableAdapter;
import com.example.allclear.timetable.maketimetable.SaveTimeTableActivity;

import java.util.List;
import java.util.Objects;

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
                    public void onFailure(@NonNull Call<TimeTableResponseDto> call, @NonNull Throwable t) {
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
                .enqueue(new Callback<TimeTableEssentialResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableEssentialResponseDto> call, @NonNull Response<TimeTableEssentialResponseDto> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            TimeTableEssentialResponseDto responseBody = response.body();
                            TimeTableEssentialResponseDto.TimeTableResponseData data = responseBody.getData();

                            List<TimeTableEssentialResponseDto.timetableGeneratorSubjectResponseDtoList> subjectResponseDtoList = data.getSubjectResponseDtoList();

                   //         Log.d("LYB", "자자 " + data.getSubjectResponseDtoList().toString());

                                initAdapter(subjectResponseDtoList);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TimeTableEssentialResponseDto> call, @NonNull Throwable t) {
                        Toast.makeText(EssentialSubjectActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                        Log.d("LYB", Objects.requireNonNull(t.getMessage()));
                    }
                });
    }

    private void initAdapter(List<TimeTableEssentialResponseDto.timetableGeneratorSubjectResponseDtoList> subjectResponseDtoList) {
        EssentialSubjectAdapter adapter = new EssentialSubjectAdapter(subjectResponseDtoList);
        binding.rvEssentialSubject.setAdapter(adapter);
    }

}