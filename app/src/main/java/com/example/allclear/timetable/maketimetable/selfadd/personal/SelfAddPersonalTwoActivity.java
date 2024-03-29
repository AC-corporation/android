package com.example.allclear.timetable.maketimetable.selfadd.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.databinding.ActivitySelfAddPersonalTwoBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;
import com.example.allclear.schedule.AdapterSpinner;
import com.example.allclear.schedule.Schedule;

import java.util.ArrayList;

public class SelfAddPersonalTwoActivity extends AppCompatActivity {
    Spinner spinner;
    AdapterSpinner adapterSpinner;

    private ActivitySelfAddPersonalTwoBinding binding;

    private SpinnerCustomBinding spinnerCustomBinding;

    String subtext;
    String professor;
    String dayspinner;
    String start_time;
    String end_time;
    String place;
    int day;
    int size;
    ArrayList<Schedule> scheduleDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddPersonalTwoBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        scheduleDataList = (ArrayList<Schedule>) intent.getSerializableExtra("schedulelist");
        if (scheduleDataList != null) {
            size = scheduleDataList.size();
        } else if (scheduleDataList == null) {
            size = 0;
        }

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner = binding.daySpinner;
        ArrayList<String> days = new ArrayList<>();
        days.add("월요일");
        days.add("화요일");
        days.add("수요일");
        days.add("목요일");
        days.add("금요일");
        days.add("토요일");
        days.add("일요일");
        //ArrayList에 내가 스피너에 보여주고싶은 값 셋팅
        adapterSpinner = new AdapterSpinner(this, days); //그 값을 넣어줌
        spinner.setAdapter(adapterSpinner); //어댑터연결
        spinnerCustomBinding = SpinnerCustomBinding.inflate(getLayoutInflater());

        ImageButton downarrow = spinnerCustomBinding.ibDownArrow1;
        downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subtext = binding.etSubTextOne.getText().toString();
                professor = binding.etProfessorName.getText().toString();
                dayspinner = binding.daySpinner.getSelectedItem().toString();
                day = getday(dayspinner);
                start_time = binding.etStarttime.getText().toString();
                end_time = binding.etEndtime.getText().toString();
                place = binding.etPlace.getText().toString();
                if (subtext.isEmpty())
                    Toast.makeText(SelfAddPersonalTwoActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                else
                    //시간이 겹치는 지 확인하는 함수
                    checkconflict();
            }
        });
    }

    public void checkconflict() {

        if (size == 0) {
            //스케줄데이터를 SelfAddOneActivity로 전달하는 함수
            addschedule();
        } else {
            for (int i = 0; i < size; i++) {
                if (day == scheduleDataList.get(i).getClassDay()) {
                    int addstart = timeToMinutes(start_time);
                    int addend = timeToMinutes(end_time);
                    int liststart = timeToMinutes(scheduleDataList.get(i).getStartTime());
                    int listend = timeToMinutes(scheduleDataList.get(i).getEndTime());
                    if ((addstart < listend) && (addend > liststart)) {
                        Toast.makeText(SelfAddPersonalTwoActivity.this, "시간이 겹치는 일정이 존재합니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            addschedule();
        }
    }

    //선택요일을 정수로 변환해주는 함수
    protected int getday(String day) {
        if (day.equals("월요일"))
            return 0;
        else if (day.equals("화요일"))
            return 1;
        else if (day.equals("수요일"))
            return 2;
        else if (day.equals("목요일"))
            return 3;
        else if (day.equals("금요일"))
            return 4;
        else if (day.equals("토요일"))
            return 5;
        else return 6;
    }

    //시간문자열을 숫자로 변환해주는 함수
    private static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    void addschedule() {
        Schedule schedule = new Schedule();
        schedule.setSubjectId(32L);
        schedule.setSubjectName(subtext);
        schedule.setProfessor(professor);
        schedule.setClassDay(day);
        schedule.setStartTime(start_time);
        schedule.setEndTime(end_time);
        schedule.setClassRoom(place);
        Intent intent = new Intent(SelfAddPersonalTwoActivity.this, SelfAddPersonalOneActivity.class);
        intent.putExtra("schedule", schedule);
        setResult(RESULT_OK, intent);
        finish();
    }
}