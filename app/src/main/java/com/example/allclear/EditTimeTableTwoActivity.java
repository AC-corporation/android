package com.example.allclear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.databinding.ActivityEditTimeTableTwoBinding;
import com.example.allclear.timetable.EditTimeTableActivity;
import com.islandparadise14.mintable.model.ScheduleDay;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;

public class EditTimeTableTwoActivity extends AppCompatActivity {
private ActivityEditTimeTableTwoBinding binding;
    private String[] day = {"Mon", "Tue", "Wen", "Thu", "Fri"};
    String subtext;
    String professor;
    String place;
    int selectedDay;
    String starttime;
    String endtime;
    ScheduleEntity added_schedule;
    Intent intent=getIntent();
    private ArrayList<Schedule> ScheduleList=new ArrayList<Schedule>();

    private ArrayList<ScheduleEntity> scheduleList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditTimeTableTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(intent != null && intent.hasExtra("schedule")){
        ScheduleList= (ArrayList<Schedule>) intent.getSerializableExtra("schedulelist");}
        scheduleList=ChangeSchedule.getInstance().Change_scheduleEntity(ScheduleList);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.tvSelfadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTimeTableTwoActivity.this,SelfAddTwoEditActivity.class);
                startActivityForResult(intent,10);
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.table.initTable(day);
        binding.table.updateSchedules(scheduleList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Schedule schedule= (Schedule) data.getSerializableExtra("schedule");
            ScheduleList.add(schedule);
            subtext=schedule.getScheduleName();
            professor=schedule.getProfessor();
            place=schedule.getRoomInfo();
            selectedDay=schedule.getScheduleDay();
            starttime=schedule.getStartTime();
            endtime=schedule.getEndTime();

            added_schedule = new ScheduleEntity(
                    32,                  // originId
                    subtext,          // scheduleName
                    place,    // roomInfo
                    selectedDay,  // ScheduleDay object (MONDAY ~ SUNDAY)
                    starttime,               // startTime format: "HH:mm"
                    endtime,              // endTime format: "HH:mm"
                    "#73fcae68",          // backgroundColor (optional)
                    "#000000"             // textColor (optional)
            );
            scheduleList.add(added_schedule);
        }
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditTimeTableTwoActivity.this, EditTimeTableActivity.class);
                intent.putExtra("schedulelist",ScheduleList);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}