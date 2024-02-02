package com.example.allclear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.allclear.databinding.ActivitySelfAddTwoBinding;
import com.example.allclear.databinding.ActivitySelfAddTwoEditBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;
import com.example.allclear.timetable.maketimetable.SelfAddOneActivity;
import com.example.allclear.timetable.maketimetable.SelfAddTwoActivity;
import com.islandparadise14.mintable.model.ScheduleDay;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;

public class SelfAddTwoEditActivity extends AppCompatActivity {
    Spinner spinner;
    AdapterDaySpinner adapterDaySpinner;

    private ActivitySelfAddTwoEditBinding binding;

    private SpinnerCustomBinding spinnerCustomBinding;

    String subtext;
    String professor;
    String  dayspinner;
    String start_time;
    String end_time;
    String place;
    int day;
    int size;
    ArrayList<Schedule> ScheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddTwoEditBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        ScheduleList= (ArrayList<Schedule>) intent.getSerializableExtra("schedulelist");
        if(ScheduleList!=null){
            size=ScheduleList.size();
        } else if (ScheduleList==null) {
            size=0;
        }

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner = binding.daySpinner;
        ArrayList<String> day = new ArrayList<>();
        day.add("월요일");
        day.add("화요일");
        day.add("수요일");
        day.add("목요일");
        day.add("금요일");
        day.add("토요일");
        day.add("일요일");
        //ArrayList에 내가 스피너에 보여주고싶은 값 셋팅
        adapterDaySpinner = new AdapterDaySpinner(this, day); //그 값을 넣어줌
        spinner.setAdapter(adapterDaySpinner); //어댑터연결
        spinnerCustomBinding=SpinnerCustomBinding.inflate(getLayoutInflater());

        ImageButton downarrow=spinnerCustomBinding.ibDownArrow1;
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
                if (binding.etSubTextOne.getText().toString().isEmpty())
                    Toast.makeText(SelfAddTwoEditActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                else
                    addscheduletotimetable();
            }
        });
    }
    public void addscheduletotimetable(){
        subtext=binding.etSubTextOne.getText().toString();
        professor=binding.etProfessorName.getText().toString();
        dayspinner=binding.daySpinner.getSelectedItem().toString();
        day=getday(dayspinner);
        start_time=binding.etStarttime.getText().toString();
        end_time=binding.etEndtime.getText().toString();
        place=binding.etPlace.getText().toString();
        if(size==0){
            Schedule schedule=new Schedule();
            schedule.setOriginId(32);
            schedule.setScheduleName(subtext);
            schedule.setProfessor(professor);
            schedule.setScheduleDay(day);
            schedule.setStartTime(start_time);
            schedule.setEndTime(end_time);
            schedule.setRoomInfo(place);

            Intent intent=new Intent(SelfAddTwoEditActivity.this,EditTimeTableTwoActivity.class);
            intent.putExtra("schedule",schedule);
            setResult(RESULT_OK,intent);
            finish();
        }
        else {
            for(int i=0;i<size;i++){
                if(day==ScheduleList.get(i).getScheduleDay()){
                    int addstart=timeToMinutes(start_time);
                    int addend=timeToMinutes(end_time);
                    int liststart=timeToMinutes(ScheduleList.get(i).getStartTime());
                    int listend=timeToMinutes(ScheduleList.get(i).getEndTime());
                    if((addstart<listend)&&(addend>liststart)){
                        Toast.makeText(SelfAddTwoEditActivity.this, "시간이 겹치는 일정이 존재합니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        }

        Schedule schedule=new Schedule();
        schedule.setOriginId(32);
        schedule.setScheduleName(subtext);
        schedule.setProfessor(professor);
        schedule.setScheduleDay(day);
        schedule.setStartTime(start_time);
        schedule.setEndTime(end_time);
        schedule.setRoomInfo(place);

        Intent intent=new Intent(SelfAddTwoEditActivity.this,EditTimeTableTwoActivity.class);
        intent.putExtra("schedule",schedule);
        setResult(RESULT_OK,intent);
        finish();
    }
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
    private static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
}