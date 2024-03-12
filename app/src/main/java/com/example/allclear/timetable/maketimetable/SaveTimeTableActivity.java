package com.example.allclear.timetable.maketimetable;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.response.TimeTableStepEightResponseDto;
import com.example.allclear.databinding.ActivitySaveTimeTableBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveTimeTableActivity extends AppCompatActivity {
    private ActivitySaveTimeTableBinding binding;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySaveTimeTableBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getUserData();
        initBackBtnClickListener();
        getTimeTableGenerator();
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
        ServicePool.timeTableService.getStepEight("Bearer " + accessToken, userId)
                .enqueue(new Callback<TimeTableStepEightResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableStepEightResponseDto> call, Response<TimeTableStepEightResponseDto> response) {
                        Toast.makeText(SaveTimeTableActivity.this, "일단 성공~~", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<TimeTableStepEightResponseDto> call, Throwable t) {
                        Toast.makeText(SaveTimeTableActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}