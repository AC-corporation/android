package com.example.allclear.timetable.maketimetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.MainPageActivity;
import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableGenerateRequestDto;
import com.example.allclear.data.request.TimeTableOneRequestDto;
import com.example.allclear.data.response.TimeTableGenerateResponseDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.databinding.ActivitySelectSemesterBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectSemesterActivity extends AppCompatActivity {

    static final String ACCESS_TOKEN = "Access_Token";
    static final String DB = "allClear";
    static final String USER_ID = "User_Id";

    private ActivitySelectSemesterBinding binding;

    TimeTableOneRequestDto timeTableOneRequestDto;
    TimeTableGenerateRequestDto timeTableGenerateRequestDto;

    NumberPicker npYearSemester;
    String selectedYear;
    String selectedSemester;
    String timeTableName;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;
    private String refreshToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectSemesterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserData();
        initBackClickListener();
        initNextBtnClickListener();
        setNumberPicker();
    }

    private void getUserData() {
        preferenceUtil = MyApplication.getPreferences();
        userId = preferenceUtil.getUserId(-1L);
        accessToken = preferenceUtil.getAccessToken("FAIL");
    }

    private void initBackClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initNextBtnClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.cbEmptyTimeTable.isChecked()) {
                    postTimeTableGenerateToServer(accessToken, userId);
                } else {
                    if (timeTableName == null)
                        timeTableName = getString(R.string.timetableName_base);
                    else
                        timeTableName = binding.etTimetableName.getText().toString();
                    postStepOneToServer(accessToken, userId);
                }
            }
        });
    }

    private void setNumberPicker() {
        npYearSemester = binding.npYearSemester;

        // 학년 어떻게 할지 논의 필요
        String[] years = {"2023", "2024"};
        String[] semesters = {getString(R.string.time_table_first_semester), getString(R.string.time_table_second_semester)};

        // 년도와 학기를 합친 배열 생성
        ArrayList<String> yearSemesters = new ArrayList<>();
        for (String year : years) {
            for (String semester : semesters) {
                yearSemesters.add(year + getString(R.string.time_table_year) + semester);
            }
        }

        // NumberPicker 설정
        npYearSemester.setMinValue(0);
        npYearSemester.setMaxValue(yearSemesters.size() - 1);
        npYearSemester.setDisplayedValues(yearSemesters.toArray(new String[0]));

        //무한 스크롤 제한
        npYearSemester.setWrapSelectorWheel(false);

        npYearSemester.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String selectedYearSemester = yearSemesters.get(newVal);
                String[] split = selectedYearSemester.split(getString(R.string.time_table_year));

                selectedYear = split[0];
                selectedSemester = split[1].replaceAll("[^0-9]", "");
            }
        });
    }

    private void postStepOneToServer(String accessToken, Long userId) {

        timeTableOneRequestDto = new TimeTableOneRequestDto();

        timeTableOneRequestDto.setTableYear(Integer.parseInt(selectedYear));
        timeTableOneRequestDto.setSemester(Integer.parseInt(selectedSemester));

        ServicePool.timeTableService.postStepOne("Bearer " + accessToken, userId, timeTableOneRequestDto)
                .enqueue(new Callback<TimeTableResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableResponseDto> call, Response<TimeTableResponseDto> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(SelectSemesterActivity.this, SelectMajorBaseActivity.class);
                            intent.putExtra("selectedYear", selectedYear);
                            intent.putExtra("selectedSemester", selectedSemester);
                            intent.putExtra("timeTableName", timeTableName);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeTableResponseDto> call, Throwable t) {
                        Toast.makeText(SelectSemesterActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void postTimeTableGenerateToServer(String accessToken, Long userId) {

        timeTableGenerateRequestDto = new TimeTableGenerateRequestDto();

        timeTableGenerateRequestDto.setTableName(timeTableName);
        timeTableGenerateRequestDto.setTableYear(Integer.parseInt(selectedYear));
        timeTableGenerateRequestDto.setSemester(Integer.parseInt(selectedSemester));

        ServicePool.timeTableService.postTimeTable("Bearer " + accessToken, userId, timeTableGenerateRequestDto)
                .enqueue(new Callback<TimeTableGenerateResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableGenerateResponseDto> call, @NonNull Response<TimeTableGenerateResponseDto> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            long timetableId = response.body().getData();
                            Intent intent = new Intent(SelectSemesterActivity.this, MainPageActivity.class);
                            intent.putExtra("timetableId", timetableId);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TimeTableGenerateResponseDto> call, @NonNull Throwable t) {
                        Toast.makeText(SelectSemesterActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}