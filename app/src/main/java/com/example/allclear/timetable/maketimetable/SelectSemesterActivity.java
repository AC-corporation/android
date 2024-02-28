package com.example.allclear.timetable.maketimetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.R;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableOneRequestDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.databinding.ActivitySelectSemesterBinding;
import com.example.allclear.timetable.TimeTableFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectSemesterActivity extends AppCompatActivity {

    private ActivitySelectSemesterBinding binding;

    long userId = 1;
    TimeTableOneRequestDto timeTableOneRequestDto;

    NumberPicker npYearSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectSemesterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBackClickListener();
        initNextBtnClickListener();
        setNumberPicker();

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
                // 체크 버튼 유무에 따라서 이동 달라집니다.
                if (binding.cbEmptyTimeTable.isChecked()) {
                    // 빈 시간표 만드는 로직 필요
                    Intent intent = new Intent(SelectSemesterActivity.this, TimeTableFragment.class);
                    startActivity(intent);
                } else {
                    postStepOneToServer(userId, timeTableOneRequestDto);
                    Intent intent = new Intent(SelectSemesterActivity.this, SelfAddOneActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void postStepOneToServer(long userId, TimeTableOneRequestDto timeTableOneRequestDto) {
        ServicePool.timeTableService.postStepOne(userId, timeTableOneRequestDto)
                .enqueue(new Callback<TimeTableResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableResponseDto> call, Response<TimeTableResponseDto> response) {
                        // 선택한 학년, 학기 서버로 보내는 로직 필요
                        Toast.makeText(SelectSemesterActivity.this, R.string.server_success, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<TimeTableResponseDto> call, Throwable t) {
                        Toast.makeText(SelectSemesterActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setNumberPicker() {
        npYearSemester = binding.npYearSemester;

        String[] years = {"2022", "2023"};
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
                // 선택된 문자열을 년도와 학기로 분리
                String[] split = selectedYearSemester.split(getString(R.string.time_table_year));
                String selectedYear = split[0];
                String selectedSemester = split[1];
                // 분리된 년도와 학기를 사용합니다.
            }
        });

    }

}